# Routing and test oracles
Positive: registration, ready, heartbeat acceptance, unique assignment, slot release, graceful drain.
Negative: duplicate assignment, stale incarnation, stale fencing epoch, over-capacity, unknown capability, unauthorized identity.
Boundary: zero slots, capacity shrink, restart with outstanding work, heartbeat exactly at threshold, drain during active attempt.
Fault: partition, duplicate/reordered heartbeat, process crash, server failover, lost response after accepted reservation.
A test is successful only with observable assertions, not a print statement. Mutation checks must first pass on baseline and then fail on deliberately faulty model. A synthetic state machine is not proof of database atomicity, actual agent isolation or production recovery.
