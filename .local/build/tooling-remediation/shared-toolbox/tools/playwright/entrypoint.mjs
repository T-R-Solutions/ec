import fs from 'node:fs';
import { spawnSync } from 'node:child_process';

// The consumer is copied by the host runner; the original /src remains read-only.
// Native node resolution (including ESM) follows this explicit node_modules link.
process.chdir('/out/work');
if (fs.existsSync('node_modules')) {
  throw new Error('consumer node_modules is prohibited in the staged work tree');
}
fs.symlinkSync('/opt/pw/node_modules', 'node_modules', 'dir');
const packagePath = '/opt/pw/node_modules/@playwright/test/package.json';
const pkg = JSON.parse(fs.readFileSync(packagePath, 'utf8'));
const result = spawnSync(process.execPath,
  ['/opt/pw/node_modules/@playwright/test/cli.js', ...process.argv.slice(2)],
  { stdio: 'inherit', env: process.env });
if (result.error) throw result.error;
console.error(JSON.stringify({ centralPlaywright: pkg.version, signal: result.signal }));
process.exit(result.status ?? 2);
