fn rejected() {
    // ruleid: ec.rust.uninitialized-value
    let value: bool = unsafe { std::mem::uninitialized() };
}
fn accepted() {
    // ok: ec.rust.uninitialized-value
    let value: bool = false;
}
fn sql(client: &mut postgres::Client, input: &str) {
    // ruleid: ec.rust.dynamic-postgres-query
    client.query(&format!("SELECT id FROM projects WHERE name = '{}'", input), &[]);
    // ok: ec.rust.dynamic-postgres-query
    client.query("SELECT id FROM projects WHERE name = $1", &[&input]);
}
fn hash(input: &[u8]) {
    // ruleid: ec.rust.weak-hash
    md5::compute(input);
    // ok: ec.rust.weak-hash
    sha2::Sha256::digest(input);
}
