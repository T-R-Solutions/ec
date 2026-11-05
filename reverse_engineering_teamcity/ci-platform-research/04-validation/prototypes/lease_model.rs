//! Independent contract model. No network, SQL, executor or TeamCity internals.
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
pub struct Fence(u64);
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
pub struct Lease {
    fence: Fence,
    expires: u64,
}
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
pub enum Outcome {
    Success,
    Failed,
    Cancelled,
    Lost,
}
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
pub enum State {
    Queued,
    Leased(Lease),
    CancelRequested(Lease),
    Terminal(Outcome),
}
#[derive(Clone, Copy, Debug, PartialEq, Eq)]
pub enum Error {
    InvalidState,
    Expired,
    Stale,
    Conflict,
    InvalidDuration,
}
pub struct Job {
    state: State,
    generation: u64,
    receipt: Option<(Fence, Outcome)>,
}
impl Default for Job {
    fn default() -> Self {
        Self { state: State::Queued, generation: 0, receipt: None }
    }
}
impl Job {
    pub fn reserve(&mut self, now: u64, ttl: u64) -> Result<Fence, Error> {
        if self.state != State::Queued { return Err(Error::InvalidState); }
        if ttl == 0 { return Err(Error::InvalidDuration); }
        let expires = now.checked_add(ttl).ok_or(Error::InvalidDuration)?;
        let generation = self.generation.checked_add(1).ok_or(Error::InvalidState)?;
        self.generation = generation;
        let fence = Fence(generation);
        self.state = State::Leased(Lease { fence, expires });
        Ok(fence)
    }
    fn live(&self, fence: Fence, now: u64) -> Result<Lease, Error> {
        let lease = match self.state {
            State::Leased(l) | State::CancelRequested(l) => l,
            _ => return Err(Error::InvalidState),
        };
        if lease.fence != fence { return Err(Error::Stale); }
        if now >= lease.expires { return Err(Error::Expired); }
        Ok(lease)
    }
    pub fn renew(&mut self, fence: Fence, now: u64, ttl: u64) -> Result<(), Error> {
        let old = self.live(fence, now)?;
        if ttl == 0 { return Err(Error::InvalidDuration); }
        let expires = now.checked_add(ttl).ok_or(Error::InvalidDuration)?;
        let lease = Lease { expires: old.expires.max(expires), ..old };
        self.state = match self.state {
            State::Leased(_) => State::Leased(lease),
            State::CancelRequested(_) => State::CancelRequested(lease),
            _ => unreachable!(),
        };
        Ok(())
    }
    pub fn cancel(&mut self) -> Result<(), Error> {
        self.state = match self.state {
            State::Queued => State::Terminal(Outcome::Cancelled),
            State::Leased(l) | State::CancelRequested(l) => State::CancelRequested(l),
            State::Terminal(_) => return Err(Error::InvalidState),
        };
        Ok(())
    }
    pub fn complete(&mut self, fence: Fence, now: u64, outcome: Outcome) -> Result<(), Error> {
        if let Some((old_fence, old_outcome)) = self.receipt {
            return if fence == old_fence && outcome == old_outcome { Ok(()) } else { Err(Error::Conflict) };
        }
        self.live(fence, now)?;
        match (self.state, outcome) {
            (State::CancelRequested(_), Outcome::Cancelled) => (),
            (State::Leased(_), Outcome::Success | Outcome::Failed) => (),
            _ => return Err(Error::InvalidState),
        }
        self.receipt = Some((fence, outcome));
        self.state = State::Terminal(outcome);
        Ok(())
    }
    pub fn expire(&mut self, now: u64, retry_safe: bool) -> Result<(), Error> {
        let (lease, cancelled) = match self.state {
            State::Leased(l) => (l, false),
            State::CancelRequested(l) => (l, true),
            _ => return Err(Error::InvalidState),
        };
        if now < lease.expires { return Err(Error::InvalidState); }
        self.state = if cancelled { State::Terminal(Outcome::Cancelled) }
            else if retry_safe { State::Queued }
            else { State::Terminal(Outcome::Lost) };
        Ok(())
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    #[test]
    fn exclusive_current_reservation() {
        let mut j = Job::default();
        j.reserve(0, 30).unwrap();
        assert_eq!(j.reserve(0, 30), Err(Error::InvalidState));
    }
    #[test]
    fn expiry_boundary_rejects_result_and_renewal() {
        let mut j = Job::default();
        let f = j.reserve(0, 30).unwrap();
        assert_eq!(j.complete(f, 30, Outcome::Success), Err(Error::Expired));
        assert_eq!(j.renew(f, 30, 30), Err(Error::Expired));
    }
    #[test]
    fn old_owner_cannot_finish_after_retry() {
        let mut j = Job::default();
        let old = j.reserve(0, 30).unwrap();
        j.expire(30, true).unwrap();
        let current = j.reserve(31, 30).unwrap();
        assert!(current.0 > old.0);
        assert_eq!(j.complete(old, 32, Outcome::Success), Err(Error::Stale));
        j.complete(current, 32, Outcome::Success).unwrap();
    }
    #[test]
    fn result_retry_after_lease_expiry_returns_receipt() {
        let mut j = Job::default();
        let f = j.reserve(0, 30).unwrap();
        j.complete(f, 2, Outcome::Success).unwrap();
        assert_eq!(j.complete(f, 300, Outcome::Success), Ok(()));
        assert_eq!(j.complete(f, 300, Outcome::Failed), Err(Error::Conflict));
    }
    #[test]
    fn cancel_wins_when_committed_first() {
        let mut j = Job::default();
        let f = j.reserve(0, 30).unwrap();
        j.cancel().unwrap();
        assert_eq!(j.complete(f, 1, Outcome::Success), Err(Error::InvalidState));
        j.complete(f, 2, Outcome::Cancelled).unwrap();
    }
    #[test]
    fn complete_wins_when_committed_first() {
        let mut j = Job::default();
        let f = j.reserve(0, 30).unwrap();
        j.complete(f, 1, Outcome::Success).unwrap();
        assert_eq!(j.cancel(), Err(Error::InvalidState));
        assert_eq!(j.state, State::Terminal(Outcome::Success));
    }
    #[test]
    fn cancel_expiry_never_requeues() {
        let mut j = Job::default();
        j.reserve(0, 30).unwrap();
        j.cancel().unwrap();
        j.expire(30, true).unwrap();
        assert_eq!(j.state, State::Terminal(Outcome::Cancelled));
    }
    #[test]
    fn retry_requires_explicit_safe_policy() {
        let mut j = Job::default();
        j.reserve(0, 30).unwrap();
        j.expire(30, false).unwrap();
        assert_eq!(j.state, State::Terminal(Outcome::Lost));
        assert_eq!(j.reserve(31, 30), Err(Error::InvalidState));
    }
    #[test]
    fn renewal_moves_deadline_without_changing_owner() {
        let mut j = Job::default();
        let f = j.reserve(0, 30).unwrap();
        j.renew(f, 10, 30).unwrap();
        assert_eq!(j.expire(30, true), Err(Error::InvalidState));
        j.complete(f, 35, Outcome::Success).unwrap();
    }
    #[test]
    fn invalid_duration_does_not_mutate_job() {
        let mut j = Job::default();
        assert_eq!(j.reserve(0, 0), Err(Error::InvalidDuration));
        assert_eq!(j.reserve(u64::MAX, 1), Err(Error::InvalidDuration));
        assert_eq!(j.state, State::Queued);
        assert_eq!(j.generation, 0);
    }
    #[test]
    fn cancelled_queued_job_never_starts() {
        let mut j = Job::default();
        j.cancel().unwrap();
        assert_eq!(j.reserve(0, 30), Err(Error::InvalidState));
    }
    #[test]
    fn all_small_deadline_points_obey_fencing() {
        for now in 0..=60 {
            let mut j = Job::default();
            let f = j.reserve(0, 30).unwrap();
            assert_eq!(j.complete(f, now, Outcome::Success).is_ok(), now < 30);
        }
    }
}
