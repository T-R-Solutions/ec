//! Loopback HTTP v1 transport; no user credentials or script execution.
use serde_json::Value;
use std::{
    io::{Read, Write},
    net::{SocketAddr, TcpStream},
    time::Duration,
};
#[derive(Clone)]
pub struct Client {
    pub address: SocketAddr,
    pub token: String,
}
#[derive(Debug)]
pub enum Error {
    Transport,
    Rejected(u16),
    Envelope,
}
impl Client {
    /// Envia uma chamada HTTP ao Server configurado.
    ///
    /// # Errors
    /// Retorna falha de transporte, rejeição HTTP ou envelope inválido.
    pub fn call(&self, method: &str, path: &str, body: Option<&Value>) -> Result<Value, Error> {
        let bytes = body.map(ToString::to_string).unwrap_or_default();
        let mut s = TcpStream::connect_timeout(&self.address, Duration::from_secs(2))
            .map_err(|_| Error::Transport)?;
        s.set_read_timeout(Some(Duration::from_secs(3)))
            .map_err(|_| Error::Transport)?;
        s.set_write_timeout(Some(Duration::from_secs(3)))
            .map_err(|_| Error::Transport)?;
        #[cfg(feature = "test-failpoints")]
        let injected = {
            static USED: std::sync::Mutex<Vec<String>> = std::sync::Mutex::new(Vec::new());
            let action = path.rsplit('/').next().unwrap_or_default();
            let mut used = USED.lock().map_err(|_| Error::Transport)?;
            if std::env::var("EC_AGENT_TEST_DROP")
                .unwrap_or_default()
                .split(',')
                .any(|v| v == action)
                && !used.iter().any(|v| v == action)
            {
                used.push(action.to_owned());
                "X-Ec-Test-Failure: after-commit\r\n"
            } else {
                ""
            }
        };
        #[cfg(not(feature = "test-failpoints"))]
        let injected = "";
        write!(s,"{method} /api/v1{path} HTTP/1.1\r\nHost: {}\r\n{injected}Authorization: Bearer {}\r\nContent-Type: application/json\r\nContent-Length: {}\r\nConnection: close\r\n\r\n{bytes}",self.address,self.token,bytes.len()).map_err(|_|Error::Transport)?;
        let mut response = Vec::new();
        s.take(100 * 1024 * 1024)
            .read_to_end(&mut response)
            .map_err(|_| Error::Transport)?;
        let split = response
            .windows(4)
            .position(|b| b == b"\r\n\r\n")
            .ok_or(Error::Transport)?;
        let headers = std::str::from_utf8(&response[..split]).map_err(|_| Error::Envelope)?;
        let status = headers
            .split_whitespace()
            .nth(1)
            .and_then(|s| s.parse::<u16>().ok())
            .ok_or(Error::Envelope)?;
        if status != 200 {
            return Err(Error::Rejected(status));
        }
        serde_json::from_slice(&response[split + 4..]).map_err(|_| Error::Envelope)
    }
}

/// Independent lease renewal during preparation, process execution and delivery.
pub fn heartbeat(
    hc: Client,
    hbase: String,
    fence: i64,
    l: std::sync::Arc<std::sync::atomic::AtomicBool>,
    k: std::sync::Arc<std::sync::atomic::AtomicBool>,
    d: std::sync::Arc<std::sync::atomic::AtomicBool>,
) -> std::thread::JoinHandle<()> {
    use serde_json::json;
    use std::{sync::atomic::Ordering, thread, time::Instant};
    thread::spawn(move || {
        let mut confirmed = Instant::now();
        while !d.load(Ordering::SeqCst) {
            let sent = Instant::now();
            match hc.call(
                "POST",
                &(hbase.clone() + "/heartbeat"),
                Some(&json!({"fence":fence})),
            ) {
                Ok(v) => {
                    confirmed = sent;
                    if v["cancelRequested"] == true {
                        k.store(true, Ordering::SeqCst);
                    }
                }
                Err(Error::Transport) => {}
                _ => {
                    l.store(true, Ordering::SeqCst);
                    break;
                }
            }
            if confirmed.elapsed() > Duration::from_secs(10) {
                l.store(true, Ordering::SeqCst);
                break;
            }
            for _ in 0..20 {
                if d.load(Ordering::SeqCst) {
                    break;
                }
                thread::sleep(Duration::from_millis(100));
            }
        }
    })
}
