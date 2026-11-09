function rejected(input: string, node: HTMLElement) {
    // ruleid: ec.typescript.dynamic-eval
    eval(input);
    // ruleid: ec.typescript.dynamic-eval
    new Function(input);
    // ruleid: ec.typescript.raw-html
    node.innerHTML = input;
}
function accepted(input: string, node: HTMLElement) {
    // ok: ec.typescript.raw-html
    node.textContent = input;
    // ok: ec.typescript.dynamic-eval
    JSON.parse(input);
}
function persistence(token: string) {
    // ruleid: ec.typescript.persistent-token
    localStorage.setItem('token', token);
    // ruleid: ec.typescript.persistent-token
    sessionStorage.setItem('accessToken', token);
    // ok: ec.typescript.persistent-token
    const memoryOnly = token;
}
