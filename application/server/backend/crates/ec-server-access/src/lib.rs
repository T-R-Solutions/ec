use ec_contracts::hash;
use postgres::GenericClient;
/// Procura uma sessão válida a partir do hash do token.
///
/// # Errors
/// Retorna erro PostgreSQL quando a consulta falhar.
pub fn authenticate(
    c: &mut impl GenericClient,
    token: &str,
) -> Result<Option<String>, postgres::Error> {
    if token.len() != ec_contracts::TOKEN_BYTES * 2 || !token.bytes().all(|b| b.is_ascii_hexdigit())
    {
        return Ok(None);
    }
    Ok(c.query_opt("SELECT identity FROM sessions WHERE hash=$1 AND NOT revoked AND expires>clock_timestamp() FOR SHARE", &[&hash(token.as_bytes())])?.map(|r| r.get(0)))
}
/// Consulta a concessão da identidade no projeto.
///
/// # Errors
/// Retorna erro PostgreSQL quando a consulta falhar.
pub fn role(
    c: &mut impl GenericClient,
    project: &str,
    identity: &str,
) -> Result<Option<String>, postgres::Error> {
    Ok(c.query_opt(
        "SELECT role FROM grants WHERE project=$1 AND identity=$2 FOR SHARE",
        &[&project, &identity],
    )?
    .map(|r| r.get(0)))
}
