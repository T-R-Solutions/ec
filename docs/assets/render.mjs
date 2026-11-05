/** Gera figuras e uma prévia local; não inicia nem testa o produto Ec. */
import fs from 'node:fs/promises';
import path from 'node:path';
import {pathToFileURL, fileURLToPath} from 'node:url';
import assert from 'node:assert/strict';

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '../..');
const assets = path.join(root, 'docs/assets');
const output = path.join(root, '.local/build/documentation-enterprise');
const evidenceArgument = process.argv.indexOf('--evidence');
const evidence = path.resolve(root, evidenceArgument >= 0 ? process.argv[evidenceArgument + 1] : path.join(output, 'observations'));
const tools = process.env.EC_DOC_TOOLS || '/tmp/ox-doc-visual-tools';
const executablePath = process.env.EC_DOC_CHROMIUM || '/tmp/ox-doc-chromium-extracted/chrome-linux/chrome';
const {chromium} = await import(pathToFileURL(path.join(tools, 'node_modules/playwright/index.mjs')));
const {default: MarkdownIt} = await import(pathToFileURL(path.join(tools, 'node_modules/markdown-it/index.mjs')));
const readJSON = async file => JSON.parse(await fs.readFile(file, 'utf8'));
const catalog = await readJSON(path.join(assets, 'catalog.json'));
const icons = await readJSON(path.join(assets, 'vendor/lucide/icons.json'));
const versions = Object.fromEntries(await Promise.all(['playwright', 'mermaid', 'markdown-it'].map(async name =>
  [name, (await readJSON(path.join(tools, 'node_modules', name, 'package.json'))).version])));
await fs.mkdir(output, {recursive: true});
await fs.mkdir(path.join(evidence, 'screenshots'), {recursive: true});
const browser = await chromium.launch({headless: true, executablePath,
  args: ['--no-sandbox', '--disable-dev-shm-usage']});
const browserVersion = browser.version();
const page = await browser.newPage({viewport: {width: 1600, height: 1200}, deviceScaleFactor: 1});
await page.route(/^https?:/, route => route.abort());
await page.setContent('<!doctype html><html lang="pt-BR"><meta charset="utf-8"><body></body></html>');
await page.addScriptTag({path: path.join(tools, 'node_modules/mermaid/dist/mermaid.min.js')});
await page.evaluate(() => mermaid.initialize({startOnLoad: false, securityLevel: 'strict', theme: 'base', htmlLabels: false,
  flowchart: {htmlLabels: false, curve: 'linear', padding: 28, nodeSpacing: 36, rankSpacing: 60},
  themeVariables: {fontFamily: 'Arial, sans-serif', fontSize: '18px', lineColor: '#52657b',
    primaryTextColor: '#172c43', primaryColor: '#f1f6fc', primaryBorderColor: '#59738e',
    edgeLabelBackground: '#ffffff', clusterBkg: '#f4f7fb', clusterBorder: '#b6c4d5'}}));

const generated = [];
try {
  for (const name of Object.keys(catalog.figures)) {
    const config = await readJSON(path.join(assets, `diagrams/${name}.layout.json`));
    const source = await fs.readFile(path.join(assets, `diagrams/${name}.mmd`), 'utf8');
    const result = await page.evaluate(async ({name, source, config, icons}) => {
      const palette = {blue: ['#f1f6fc', '#425f7f', '#193b63'], teal: ['#eff9f6', '#397569', '#185446'],
        purple: ['#f6f2fc', '#776099', '#533679'], amber: ['#fff8e9', '#947237', '#704d13'],
        rose: ['#fff1f3', '#a65368', '#842b43']};
      const definitions = Object.entries(palette).map(([tone, [fill, stroke, color]]) =>
        `classDef ${tone} fill:${fill},stroke:${stroke},color:${color},stroke-width:1.5px,rx:12,ry:12`).join('\n');
      const classes = Object.entries(config.nodes).map(([id, node]) => `class ${id} ${node.tone}`).join('\n');
      const {svg} = await mermaid.render('graph_' + name.replaceAll('-', '_'), config.type === 'sequence' ? source : source + '\n' + definitions + '\n' + classes);
      document.body.innerHTML = svg;
      const el = document.querySelector('svg');
      const NS = 'http://www.w3.org/2000/svg';
      const box = el.viewBox.baseVal;
      let native = {x: box.x, y: box.y, width: box.width, height: box.height};
      const content = document.createElementNS(NS, 'g');
      const oldChildren = [...el.childNodes];
      for (const child of oldChildren) content.append(child);
      el.append(content);
      // Painéis independentes e sequências longas recebem composição compacta.
      // Os nós/rótulos/relações continuam vindo da fonte Mermaid renderizada.
      const grid = ['test-boundaries','project-status','server-map','delivery-journey','contribution'].includes(name);
      if (grid) {
        const ids = Object.keys(config.nodes), cols = 3, cw = 310, ch = 150, gapX = 56, gapY = 78;
        const isTree = name === 'server-map', isChain = ['delivery-journey','contribution'].includes(name);
        const positions = {};
        const rows = Math.ceil((ids.length - (isTree ? 1 : 0)) / cols);
        const top = isTree ? 220 : 0;
        for (let index = 0; index < ids.length; index++) {
          let i = index - (isTree ? 1 : 0), row = Math.floor(i / cols), col = i % cols;
          if (isChain && row % 2) col = cols - 1 - col;
          const x = isTree && index === 0 ? (cols * cw + (cols - 1) * gapX) / 2 : col * (cw + gapX) + cw / 2;
          const y = isTree && index === 0 ? ch / 2 : top + row * (ch + gapY) + ch / 2;
          const group = content.querySelector(`g.node[id^="flowchart-${ids[index]}-"]`);
          const rect = group.querySelector('rect.basic');
          group.setAttribute('transform', `translate(${x},${y})`);
          for (const [key,val] of Object.entries({x:-cw/2,y:-ch/2,width:cw,height:ch})) rect.setAttribute(key,val);
          positions[ids[index]] = {x,y};
        }
        for (const node of content.querySelectorAll('.edgePaths,.edgeLabels')) node.replaceChildren();
        const edges = content.querySelector('.edgePaths');
        const pattern = /^\s*(\w+) -->\|"([^"]+)"\| (\w+)\s*$/gm;
        for (const match of source.matchAll(pattern)) {
          const a = positions[match[1]], b = positions[match[3]];
          let d;
          if (isTree) {
            const lane = b.y - ch/2 - 28;
            d = `M ${a.x},${a.y+ch/2} V ${a.y+ch/2+28} H -24 V ${lane} H ${b.x} V ${b.y-ch/2-4}`;
          } else if (a.y === b.y) {
            const sign = b.x > a.x ? 1 : -1;
            d = `M ${a.x+sign*cw/2},${a.y} H ${b.x-sign*(cw/2+4)}`;
          } else d = `M ${a.x},${a.y+ch/2} V ${b.y-ch/2-4}`;
          const line = document.createElementNS(NS,'path');
          line.setAttribute('d',d); line.setAttribute('class','flowchart-link');
          line.setAttribute('fill','none'); line.setAttribute('stroke','#52657b'); line.setAttribute('stroke-width','1.5');
          const marker = content.querySelector('marker[id$="pointEnd"]');
          if (marker) line.setAttribute('marker-end',`url(#${marker.id})`);
          const title=document.createElementNS(NS,'title');title.textContent=match[2];line.append(title);
          edges.append(line);
        }
        native = {x: isTree ? -32 : 0, y: 0, width: cols*cw+(cols-1)*gapX+(isTree?32:0), height: top+rows*ch+(rows-1)*gapY};
      }
      const width = Math.max(800, native.width + 72);
      const height = native.height + 230;
      content.setAttribute('transform', `translate(${(width - native.width) / 2 - native.x},${158 - native.y})`);
      for (const [id, node] of Object.entries(config.nodes)) {
        const group = content.querySelector(`g.node[id^="flowchart-${id}-"]`);
        if (!group) throw new Error('Nó ausente: ' + id);
        const rect = group.querySelector('rect.basic');
        if (!rect) throw new Error('Forma inesperada: ' + id);
        const x = +rect.getAttribute('x'), y = +rect.getAttribute('y');
        const icon = document.createElementNS(NS, 'g');
        icon.setAttribute('transform', `translate(${x + 10},${y + 10}) scale(.83)`);
        icon.setAttribute('color', palette[node.tone][2]);
        icon.innerHTML = icons.icons[node.icon].body;
        icon.setAttribute('aria-hidden', 'true');
        group.append(icon);
      }
      const make = (tag, attrs, value) => {
        const node = document.createElementNS(NS, tag);
        for (const [key, val] of Object.entries(attrs)) node.setAttribute(key, val);
        if (value !== undefined) node.textContent = value;
        return node;
      };
      const bg = make('rect', {x: 0, y: 0, width, height, rx: 18, fill: '#ffffff', stroke: '#c9d4df', 'stroke-width': 2});
      el.insertBefore(bg, content);
      el.append(make('rect', {x: 32, y: 30, width: 38, height: 34, rx: 7, fill: '#12394b'}));
      el.append(make('text', {x: 51, y: 53, 'text-anchor': 'middle', fill: '#ffffff', 'font-size': 18, 'font-weight': 700, 'font-family': 'Arial, sans-serif'}, 'Ec'));
      el.append(make('text', {x: 84, y: 52, fill: '#456078', 'font-size': 14, 'letter-spacing': 1.4, 'font-family': 'Arial, sans-serif'}, 'DOCUMENTAÇÃO TÉCNICA'));
      el.append(make('text', {x: 32, y: 102, fill: '#15364b', 'font-size': 27, 'font-weight': 700, 'font-family': 'Arial, sans-serif'}, config.title));
      el.append(make('text', {x: 32, y: 130, fill: '#435a70', 'font-size': 16, 'font-family': 'Arial, sans-serif'}, config.question));
      el.append(make('line', {x1: 32, y1: height - 48, x2: width - 32, y2: height - 48, stroke: '#c9d4df'}));
      el.append(make('text', {x: 32, y: height - 22, fill: '#435a70', 'font-size': 14, 'font-family': 'Arial, sans-serif'}, config.status));
      el.setAttribute('viewBox', `0 0 ${width} ${height}`);
      el.setAttribute('width', width); el.setAttribute('height', height);
      el.style.maxWidth = ''; el.style.background = '#fff';
      el.setAttribute('role', 'img');
      // Inspeciona geometrias na exportação final, não apenas a sintaxe Mermaid.
      const outside = [...el.querySelectorAll('text')].filter(t => {
        const b = t.getBoundingClientRect(), s = el.getBoundingClientRect();
        return b.left < s.left - 1 || b.right > s.right + 1 || b.top < s.top - 1 || b.bottom > s.bottom + 1;
      }).map(t => t.textContent);
      const nodeBoxes = [...content.querySelectorAll('g.node')].map(n => {
        const b = n.getBoundingClientRect(); return {id: n.id, x: b.x, y: b.y, w: b.width, h: b.height};
      });
      const overlaps = [];
      for (let i = 0; i < nodeBoxes.length; i++) for (let j = i + 1; j < nodeBoxes.length; j++) {
        const a = nodeBoxes[i], b = nodeBoxes[j];
        if (a.x < b.x + b.w - 1 && b.x < a.x + a.w - 1 && a.y < b.y + b.h - 1 && b.y < a.y + a.h - 1) overlaps.push([a.id,b.id]);
      }
      const minimumLabelFont = Math.min(...[...content.querySelectorAll('text')].map(t => parseFloat(getComputedStyle(t).fontSize)));
      const labelOverflow = [...content.querySelectorAll('g.node')].filter(n => {
        const label = n.querySelector('.label'), shape = n.querySelector('rect.basic');
        if (!label || !shape) return false;
        const a = label.getBoundingClientRect(), b = shape.getBoundingClientRect();
        return a.left < b.left || a.right > b.right || a.top < b.top || a.bottom > b.bottom;
      }).map(n => n.id);
      return {svg: new XMLSerializer().serializeToString(el), width, height, outside, overlaps, minimumLabelFont, labelOverflow,
        nodes: nodeBoxes.length, edges: content.querySelectorAll('.flowchart-link').length};
    }, {name, source, config, icons});
    assert.equal(result.outside.length, 0, `${name}: texto fora da figura: ${result.outside}`);
    assert.equal(result.overlaps.length, 0, `${name}: nós sobrepostos`);
    assert.equal(result.labelOverflow.length, 0, `${name}: texto fora do nó: ${result.labelOverflow}`);
    assert.equal(result.nodes, Object.keys(config.nodes).length, `${name}: contagem de nós`);
    await fs.writeFile(path.join(assets, `diagrams/${name}.svg`), result.svg + '\n');
    generated.push({name, width: result.width, height: result.height, nodes: result.nodes, edges: result.edges,
      text_outside: result.outside, node_overlaps: result.overlaps, label_overflow: result.labelOverflow,
      minimum_label_font_px: result.minimumLabelFont});
  }
} finally { await page.close(); }

const css = `:root{color-scheme:light;--ink:#193047;--muted:#435b71;--line:#c6d2df;--link:#075b80}
*{box-sizing:border-box}html{scroll-behavior:smooth}body{margin:0;background:#f1f4f8;color:var(--ink);font:17px/1.7 Arial,sans-serif}
a{color:var(--link);text-underline-offset:3px;overflow-wrap:anywhere}a:focus-visible{outline:3px solid #926919;outline-offset:4px}
.skip{position:absolute;top:-80px;padding:12px;background:white}.skip:focus{top:8px}
header{background:#12394b;color:white;padding:20px max(20px,calc((100vw - 1100px)/2));display:flex;gap:24px;align-items:center}
header a{color:#fff}header strong{font-size:26px}header span{font-size:14px}main{max-width:1160px;margin:36px auto;padding:44px 48px;background:#fff;border:1px solid var(--line);border-radius:12px}
h1{font-size:44px;line-height:1.15;letter-spacing:-1.2px;margin:0 0 24px}h2{font-size:27px;line-height:1.3;margin:48px 0 20px;padding-top:20px;border-top:1px solid var(--line)}h3{font-size:21px;margin-top:30px}p{margin:18px 0}
blockquote{margin:24px 0;padding:4px 22px;background:#eef7f4;border-left:4px solid #397569;color:#234f48}
pre{padding:20px;background:#f1f5f9;overflow:auto;border:1px solid var(--line);border-radius:8px;font-size:14px;line-height:1.6}code{font-family:monospace;font-size:.87em;overflow-wrap:anywhere}p code,li code,td code{background:#eef2f6;padding:2px 4px;border-radius:3px}
table{border-collapse:collapse;width:100%;font-size:15px}th{text-align:left;background:#edf2f7;color:#25465e}th,td{padding:14px 16px;border:1px solid var(--line);vertical-align:top}tr:nth-child(even){background:#f8fafc}.table-scroll{overflow:auto}
.figure-scroll{overflow:auto;border-radius:12px;margin:28px 0 18px}.figure-scroll img{display:block;width:100%;min-width:740px;height:auto}li{margin:8px 0}footer{color:var(--muted);font-size:14px;margin-top:48px;border-top:1px solid var(--line);padding-top:20px}
@media(max-width:768px){body{font-size:16px}header{padding:16px 20px;gap:18px}main{margin:0;padding:28px 20px;border:0;border-radius:0}h1{font-size:36px}h2{font-size:25px;margin-top:36px}th,td{padding:12px;min-width:130px}pre{padding:16px}.figure-scroll{margin-left:0;margin-right:0}header span{font-size:12px}}
@media(prefers-color-scheme:dark){:root{color-scheme:dark;--ink:#e1e9f3;--muted:#b7c7d8;--line:#4f657c;--link:#91cde8}body{background:#111c28}main{background:#172534}header{background:#0d2633}blockquote{background:#1c3733;color:#d0e8dc}pre,p code,li code,td code{background:#223448}th{background:#263b50;color:#e1e9f3}tr:nth-child(even){background:#1e2d3e}.figure-scroll{background:white}.skip{background:#172534}}
`;
const md = new MarkdownIt({html: true, linkify: false});
const slug = text => text.toLowerCase().replace(/<[^>]*>/g, '').replace(/[^\p{L}\p{N}\s_-]/gu, '').replace(/\s/g, '-');
md.renderer.rules.heading_open = (tokens, idx, options, env, self) => {
  const title = tokens[idx + 1].content;
  const base = slug(title), count = env.slugs.get(base) || 0;
  env.slugs.set(base, count + 1);
  tokens[idx].attrSet('id', base + (count ? '-' + count : ''));
  return self.renderToken(tokens, idx, options);
};
const originalLink = md.renderer.rules.link_open || ((tokens, idx, options, env, self) => self.renderToken(tokens, idx, options));
md.renderer.rules.link_open = (tokens, idx, options, env, self) => {
  const href = tokens[idx].attrGet('href');
  if (href && !/^(https?:|#|mailto:)/.test(href)) {
    const [location, hash] = href.split('#');
    const file = path.resolve(root, path.dirname(env.file), location);
    const preview = file.endsWith('.md') && documents.includes(path.relative(root,file));
    tokens[idx].attrSet('href', pathToFileURL(preview ? path.join(output, 'site', path.relative(root, file)) + '.html' : file).href + (hash ? '#' + hash : ''));
  }
  return originalLink(tokens, idx, options, env, self);
};
md.renderer.rules.image = (tokens, idx, options, env) => {
  const token = tokens[idx], alt = md.utils.escapeHtml(token.content);
  const src = pathToFileURL(path.resolve(root, path.dirname(env.file), token.attrGet('src'))).href;
  const graph = generated.find(g => path.basename(src) === g.name + '.svg');
  const minWidth = Math.max(740, graph ? Math.ceil(graph.width * 14 / graph.minimum_label_font_px) : 740);
  return `<img src="${src}" alt="${alt}" loading="eager" style="min-width:${minWidth}px"${graph ? ` data-native-width="${graph.width}" data-label-font="${graph.minimum_label_font_px}"` : ''}>`;
};
const documents = [...new Set(['README.md', 'docs/architecture/application-overview.md', 'docs/assets/README.md',
  'docs/validation/documentation-enterprise-20261104/report.md', ...Object.keys(catalog.documents)])].sort();
for (const file of documents) {
  const text = await fs.readFile(path.join(root, file), 'utf8');
  let html = md.render(text, {file, slugs: new Map()});
  html = html.replace(/<p>(<a [^>]+><img[^>]+><\/a>)<\/p>/g, '<div class="figure-scroll" tabindex="0" role="region" aria-label="Figura técnica ampliável; use a rolagem horizontal quando necessário">$1</div>');
  html = html.replace(/<table>/g, '<div class="table-scroll" tabindex="0" role="region" aria-label="Tabela com rolagem horizontal"><table>').replace(/<\/table>/g, '</table></div>');
  const destination = path.join(output, 'site', file + '.html');
  await fs.mkdir(path.dirname(destination), {recursive: true});
  await fs.writeFile(destination, `<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>${md.utils.escapeHtml(text.split('\n')[0].replace(/^# /,''))} · Ec</title><style>${css}</style><body><a class="skip" href="#conteudo">Pular para o conteúdo</a><header><strong>Ec</strong><span>DOCUMENTAÇÃO · 2026-11-04</span><a href="${pathToFileURL(path.join(output, 'site/README.md.html')).href}">Início</a></header><main id="conteudo">${html}<footer>Prévia local de documentação · conteúdo de produto planejado · imagens técnicas ampliáveis</footer></main></body></html>`);
}

const cases = [];
const errors = [];
for (const colorScheme of ['light', 'dark']) {
  for (const width of [360, 768, 1280]) {
    const context = await browser.newContext({viewport: {width, height: 1000}, colorScheme});
    const p = await context.newPage();
    p.on('pageerror', error => errors.push({error: String(error), file: p.url()}));
    await p.route(/^https?:/, route => route.abort());
    for (const file of documents) {
      await p.goto(pathToFileURL(path.join(output, 'site', file + '.html')).href);
      await p.locator('main img').evaluateAll(images => Promise.all(images.map(img => img.decode())));
      const observed = await p.evaluate(() => ({
        horizontal_overflow: document.documentElement.scrollWidth > innerWidth + 1,
        images: [...document.images].map(i => ({loaded: i.complete && i.naturalWidth > 0, alt: !!i.alt, width: i.naturalWidth, height: i.naturalHeight})),
        empty_links: [...document.querySelectorAll('a')].filter(a => !a.textContent.trim() && !a.querySelector('img[alt]')).length,
        title: !!document.querySelector('h1'), language: document.documentElement.lang,
        figures: document.querySelectorAll('.figure-scroll').length,
        minimum_node_label_px: document.images.length ? Math.min(...[...document.images].filter(i => i.dataset.nativeWidth).map(i => Number(i.dataset.labelFont) * i.getBoundingClientRect().width / Number(i.dataset.nativeWidth))) : null,
        contrast_failures: (() => {
          const parse = value => (value.match(/[\d.]+/g) || []).slice(0,3).map(Number);
          const luminance = rgb => rgb.map(v => {const c = v / 255; return c <= .04045 ? c/12.92 : ((c+.055)/1.055)**2.4;}).reduce((s,v,i) => s + v * [.2126,.7152,.0722][i], 0);
          const failures = [];
          for (const el of document.querySelectorAll('main p,main h1,main h2,main h3,main a,main td,main th,main code,header a,header span')) {
            if (!el.textContent.trim() || !el.getClientRects().length) continue;
            let parent = el, background;
            while (parent) {
              const value = getComputedStyle(parent).backgroundColor;
              if (value !== 'rgba(0, 0, 0, 0)' && value !== 'transparent') {background=value;break;}
              parent = parent.parentElement;
            }
            if (!background) continue;
            const color = getComputedStyle(el).color;
            const a=luminance(parse(color)), b=luminance(parse(background)), ratio=(Math.max(a,b)+.05)/(Math.min(a,b)+.05);
            if (ratio < 4.5) failures.push({text:el.textContent.slice(0,80),color,background,ratio});
          }
          return failures;
        })(),
      }));
      assert(!observed.horizontal_overflow, `${file}: overflow global em ${width}/${colorScheme}`);
      assert(observed.title && observed.language === 'pt-BR', `${file}: estrutura da página`);
      assert(observed.images.every(i => i.loaded && i.alt), `${file}: imagem ausente ou sem descrição`);
      assert.equal(observed.empty_links, 0, `${file}: link vazio`);
      assert(observed.minimum_node_label_px === null || observed.minimum_node_label_px >= 14, `${file}: rótulo essencial menor que 14px`);
      assert.equal(observed.contrast_failures.length, 0, `${file}: contraste de texto inferior a 4,5:1: ${JSON.stringify(observed.contrast_failures)}`);
      cases.push({file, width, colorScheme, ...observed});
      const capture = file === 'README.md' || (width === 1280 && colorScheme === 'light' && ['docs/architecture/application-overview.md','docs/architecture/contracts.md','docs/development/contributing.md','application/server/frontend/README.md','application/agents/crates/ec-agent-execution/README.md'].includes(file));
      if (capture) await p.screenshot({path: path.join(evidence, 'screenshots', file.replaceAll('/', '__').replace('.md','') + `-${width}-${colorScheme}.png`), fullPage: true});
    }
    // Teclado: o primeiro foco deve abrir o caminho para o conteúdo.
    await p.goto(pathToFileURL(path.join(output, 'site/README.md.html')).href);
    await p.keyboard.press('Tab');
    assert.equal(await p.locator(':focus').innerText(), 'Pular para o conteúdo');
    await p.keyboard.press('Enter');
    assert(p.url().endsWith('#conteudo'));
    await p.getByRole('link',{name:'Visão funcional e técnica',exact:true}).click();
    await p.getByRole('heading',{name:'Visão funcional e técnica do Ec',exact:true}).waitFor();
    await p.goBack();
    await p.getByRole('link',{name:'Guia de contribuição',exact:true}).click();
    await p.getByRole('heading',{name:'Contribuir com o Ec',exact:true}).waitFor();
    await context.close();
  }
}
assert.equal(errors.length, 0, 'Erros JavaScript na prévia');
const sheets = await browser.newPage({viewport: {width: 1280, height: 1000}});
await sheets.route(/^https?:/, route => route.abort());
for (let index = 0; index < generated.length; index += 6) {
  const subset = generated.slice(index,index + 6);
  const filename = path.join(output, `figures-${Math.floor(index/6)+1}.html`);
  await fs.writeFile(filename, `<!doctype html><html lang="pt-BR"><meta charset="utf-8"><title>Figuras Ec</title><style>body{margin:0;padding:24px;font:16px Arial;background:#eaf0f6}.grid{display:grid;grid-template-columns:1fr 1fr;gap:20px}figure{margin:0;background:white;padding:16px;border:1px solid #aabccc}img{width:100%;height:480px;object-fit:contain}figcaption{padding-top:12px;color:#193047}</style><div class="grid">${subset.map(g => `<figure><img src="${pathToFileURL(path.join(assets,`diagrams/${g.name}.svg`)).href}" alt="${g.name}"><figcaption>${g.name}</figcaption></figure>`).join('')}</div></html>`);
  await sheets.goto(pathToFileURL(filename).href);
  await sheets.locator('img').evaluateAll(images => Promise.all(images.map(i => i.decode())));
  await sheets.screenshot({path:path.join(evidence,'screenshots',`figures-${Math.floor(index/6)+1}.png`),fullPage:true});
}
await sheets.close();
const iconPackage = await readJSON(path.join(assets,'vendor/lucide/provenance.json'));
await fs.writeFile(path.join(evidence, 'visual-checks.json'), JSON.stringify({versions, browser: browserVersion,
  executablePath, subject: 'Prévia local Markdown-it; não é a interface do Ec nem o renderizador de um serviço remoto.',
  generated, cases, errors, keyboard: 'Skip link observado nos seis ambientes de viewport/tema.',
  navigation: 'README → visão; voltar; README → contribuição, nos seis ambientes.', iconPackage,
  limits: ['Sem estudo com leitores ou tecnologia assistiva.', 'Compatibilidade com outros clientes Markdown exige observação própria.']}, null, 2) + '\n');
await browser.close();
console.log(JSON.stringify({figures: generated.length, documents: documents.length, cases: cases.length, errors: errors.length, preview: path.join(output,'site/README.md.html'), browser: browserVersion}));
