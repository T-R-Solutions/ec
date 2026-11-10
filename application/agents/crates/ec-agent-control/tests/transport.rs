use ec_agent_control::{Client, Error, heartbeat};
use serde_json::json;
use std::{
    io::{Read, Write},
    net::TcpListener,
    sync::{
        Arc,
        atomic::{AtomicBool, Ordering},
    },
    thread,
    time::{Duration, Instant},
};

fn server(response: &'static [u8]) -> (Client, thread::JoinHandle<Vec<u8>>) {
    let listener = TcpListener::bind("127.0.0.1:0").unwrap();
    let client = Client {
        address: listener.local_addr().unwrap(),
        token: "fixture".into(),
    };
    let worker = thread::spawn(move || {
        let (mut stream, _) = listener.accept().unwrap();
        stream
            .set_read_timeout(Some(Duration::from_secs(3)))
            .unwrap();
        let mut request = Vec::new();
        loop {
            let mut buffer = [0; 1024];
            let count = stream.read(&mut buffer).unwrap();
            assert!(count > 0, "request incomplete");
            request.extend_from_slice(&buffer[..count]);
            if let Some(split) = request.windows(4).position(|v| v == b"\r\n\r\n") {
                let headers = String::from_utf8_lossy(&request[..split]);
                let length: usize = headers
                    .lines()
                    .find_map(|line| line.strip_prefix("Content-Length: "))
                    .unwrap()
                    .parse()
                    .unwrap();
                if request.len() >= split + 4 + length {
                    break;
                }
            }
        }
        stream.write_all(response).unwrap();
        request
    });
    (client, worker)
}

#[test]
fn wire_preserves_path_credential_and_json_bytes() {
    let (client, worker) = server(b"HTTP/1.1 200 OK\r\nConnection: close\r\n\r\n{\"receipt\":7}");
    assert_eq!(
        client
            .call("POST", "/agent/reserve", Some(&json!({"text":"é"})))
            .unwrap(),
        json!({"receipt":7})
    );
    let request = String::from_utf8(worker.join().unwrap()).unwrap();
    assert!(request.starts_with("POST /api/v1/agent/reserve HTTP/1.1\r\n"));
    assert!(request.contains("Authorization: Bearer fixture\r\n"));
    assert!(request.ends_with("{\"text\":\"é\"}"));
    assert!(request.contains("Content-Length: 13\r\n"));
}

#[test]
fn malformed_and_rejected_responses_never_become_receipts() {
    for response in [
        &b"HTTP/1.1 403 Forbidden\r\n\r\n{}"[..],
        &b"HTTP/1.1 invalid\r\n\r\n{}"[..],
        &b"HTTP/1.1 200 OK\r\n\r\ninvalid-json"[..],
        &b"missing-headers"[..],
        &b"HTTP/1.1 \xff\r\n\r\n{}"[..],
    ] {
        let (client, worker) = server(response);
        let error = client.call("GET", "/agent/attempts", None).unwrap_err();
        assert!(matches!(
            error,
            Error::Rejected(403) | Error::Envelope | Error::Transport
        ));
        worker.join().unwrap();
    }
    let listener = TcpListener::bind("127.0.0.1:0").unwrap();
    let client = Client {
        address: listener.local_addr().unwrap(),
        token: String::new(),
    };
    drop(listener);
    assert!(matches!(
        client.call("GET", "/agent/attempts", None),
        Err(Error::Transport)
    ));
}

#[test]
fn heartbeat_propagates_cancellation_and_authority_loss_and_stops() {
    for (response, rejected) in [
        (
            &b"HTTP/1.1 200 OK\r\n\r\n{\"cancelRequested\":true}"[..],
            false,
        ),
        (&b"HTTP/1.1 401 Unauthorized\r\n\r\n{}"[..], true),
    ] {
        let (client, server) = server(response);
        let lost = Arc::new(AtomicBool::new(false));
        let cancel = Arc::new(AtomicBool::new(false));
        let done = Arc::new(AtomicBool::new(false));
        let worker = heartbeat(
            client,
            "/agent/attempts/1".into(),
            3,
            lost.clone(),
            cancel.clone(),
            done.clone(),
        );
        server.join().unwrap();
        let deadline = Instant::now() + Duration::from_secs(3);
        while !(lost.load(Ordering::SeqCst) || cancel.load(Ordering::SeqCst))
            && Instant::now() < deadline
        {
            thread::yield_now();
        }
        done.store(true, Ordering::SeqCst);
        worker.join().unwrap();
        assert_eq!(lost.load(Ordering::SeqCst), rejected);
        assert_eq!(cancel.load(Ordering::SeqCst), !rejected);
    }
}
