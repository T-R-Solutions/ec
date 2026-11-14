use postgres::{Client, NoTls};
use std::io::Read;
type AnyError = Box<dyn std::error::Error + Send + Sync>;
mod admin;
mod agents;
mod http;
mod projects;
mod protocol;
pub use admin::run;
fn connect() -> Result<Client, postgres::Error> {
    Client::connect(&std::env::var("EC_DATABASE_URL").unwrap_or_default(), NoTls)
}
use ec_contracts::valid_name as name;
fn random() -> Result<String, AnyError> {
    let mut bytes = [0; ec_contracts::TOKEN_BYTES];
    std::fs::File::open("/dev/urandom")?.read_exact(&mut bytes)?;
    Ok(ec_contracts::hex(&bytes))
}
