"""T006: navegador Firefox do sistema + Server/Agent/Git/PostgreSQL reais.

Somente container dedicado ec-publication-test; portas 58139/5173/58282.
Sem tokens em traces, URLs ou screenshots. Rode em sequência com outros harnesses.
"""
import base64
import hashlib
import json
import os
from pathlib import Path
import subprocess
import sys
import tempfile
import time

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / 'integration'))
import publication_http as h
sys.path.insert(0, str(h.ROOT / 'application/server/frontend/tests/e2e'))
from firefox_driver import Firefox

ART = Path(os.environ['EC_E2E_ARTIFACT_DIR']) if 'EC_E2E_ARTIFACT_DIR' in os.environ else Path(tempfile.mkdtemp(prefix='ec-frontend-evidence-'))
FRONT = h.ROOT / 'application/server/frontend'
AGENT = h.ROOT / '.local/build/ec-agent-target/debug/ec-agent-app'


def main():
    ART.mkdir(exist_ok=True, parents=True)
    subprocess.run(['docker', 'exec', h.CONTAINER, 'createdb', '-U', 'postgres', h.DATABASE], check=True)
    processes = []
    results = []
    def passed(message):
        results.append(message)
        print('PASS ' + message, flush=True)
    try:
        with tempfile.TemporaryDirectory(prefix='ec-frontend-', dir=h.ROOT / '.local/build') as directory:
            tmp = Path(directory)
            h.cli('migrate')
            tokens = {}
            for actor, project, role in [('admin','ec','admin'),('viewer','ec','viewer'),('foreign','other','admin')]:
                path = tmp / actor
                h.cli('provision',actor,project,role,str(path),'local')
                tokens[actor] = path.read_text()
            credential = tmp / 'agent-token'
            h.cli('provision-agent','linux-agent','ec',str(credential))
            repo = tmp / 'source'
            subprocess.run(['git','init','-q',str(repo)],check=True)
            (repo/'pinned').write_bytes(b'pinned fixture\x00\xff')
            subprocess.run(['git','-C',str(repo),'add','.'],check=True)
            subprocess.run(['git','-C',str(repo),'-c','user.name=Ec Test','-c','user.email=test@example.invalid','commit','-qm','fixture'],check=True)
            commit = subprocess.check_output(['git','-C',str(repo),'rev-parse','HEAD'],text=True).strip()
            h.cli('admit-repository','ec',str(repo),commit)
            server = h.start();processes.append(server)
            def call(method,path,actor='admin',body=None, failure=None):
                return h.call(method,path,tokens[actor],body,failure)
            source = '\n'.join(['# Comentário preservado até escolha explícita','apiVersion: ec/v1alpha1','kind: ProjectPipeline','project: ec','pipeline: build','repository: '+str(repo),'commit: '+commit,'script: '+json.dumps("cat pinned > result.txt; printf '\\033[31m\\377<script>out'; printf err >&2"),'timeoutSeconds: "30"','artifact: result.txt',''])
            before = h.snapshot()
            for text in [source+'pipeline: duplicate\n',source+'unknown: x\n',source.replace('"30"','0x20'),source.replace('ProjectPipeline','null'),source.replace('pipeline: build','pipeline: &x build'),source.replace('pipeline: build','pipeline: [build]'),source.replace('pipeline: build','pipeline: !!str build'),source.replace('pipeline: build','pipeline: '+ 'x'*65)]:
                status,v=call('POST','/projects/ec/validation','viewer',{'yaml':text});assert status==200 and not v['valid'],v
            status,valid=call('POST','/projects/ec/validation','viewer',{'yaml':source});assert status==200 and valid['projection']['timeoutSeconds']=='30'
            assert call('POST','/projects/ec/validation','viewer',{'yaml':'x'*65537})[0]==413
            assert h.snapshot()==before
            assert call('GET','/projects','viewer')[1]['projects'][0]['role']=='viewer'
            assert len(call('GET','/projects','foreign')[1]['projects'])==1
            assert call('GET','/projects/ec/agents','foreign')[0]==404
            agents=call('GET','/projects/ec/agents','viewer')[1]['agents'];assert set(agents[0])=={'name','revoked','sessionValid'}
            passed('Contratos de descoberta, sessão, agentes e validação viewer sem persistência; rejeições parser/schema e limites')
            vite_log=(tmp/'vite.log').open('w')
            processes.append(subprocess.Popen(['npm','run','dev','--','--port','5173','--strictPort'],cwd=FRONT,stdout=vite_log,stderr=subprocess.STDOUT,start_new_session=True))
            profile=tmp/'firefox';profile.mkdir()
            (profile/'user.js').write_text('user_pref("marionette.port", 58282);\nuser_pref("browser.shell.checkDefaultBrowser", false);\nuser_pref("browser.download.folderList", 2);\nuser_pref("browser.download.dir", '+json.dumps(str(tmp/'downloads'))+');\nuser_pref("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");\n')
            ff_log=(tmp/'firefox.log').open('w')
            processes.append(subprocess.Popen(['/usr/bin/firefox','--headless','--no-remote','--profile',str(profile),'--marionette'],env=dict(os.environ,MOZ_REMOTE_ALLOW_SYSTEM_ACCESS='1'),stdout=ff_log,stderr=subprocess.STDOUT))
            browser=Firefox()
            browser.command('WebDriver:SetWindowRect',{'width':1440,'height':1050})
            time.sleep(1)
            browser.command('WebDriver:Navigate',{'url':'http://127.0.0.1:5173'})
            browser.wait("return !!document.querySelector('input[type=password]')")
            def login(actor):
                browser.input('input[type=password]',tokens[actor]);browser.click('Verificar sessão')
                browser.wait("return !!document.querySelector('option[value=ec]')" if actor!='foreign' else "return !!document.querySelector('option[value=other]')")
            def choose(project='ec'):
                browser.click('Configuração')
                browser.input('aside select',project)
                browser.wait("return document.body.innerText.includes('Configuração do pipeline')")
            def yaml(text):
                browser.click('YAML');browser.input('textarea',text)
                browser.wait("return document.body.innerText.includes('Semântica válida')")
            viewports={}
            def screenshot(name):
                viewports[name]=browser.js('return {width:innerWidth,height:innerHeight,dpr:devicePixelRatio}')
                data=browser.command('WebDriver:TakeScreenshot',{'full':False})
                (ART/name).write_bytes(base64.b64decode(data))
            login('admin');choose();yaml(source)
            browser.click('Visual')
            browser.wait("return document.body.innerText.includes('1 · Executar script Linux')")
            browser.input('.fields input[value=build]', 'compile') if False else None
            # Structured field change from Visual; explicit review of loss before apply.
            browser.js("const e=Array.from(document.querySelectorAll('.fields label')).find(l=>l.textContent.includes('pipeline')).querySelector('input'); e.value='compile';e.dispatchEvent(new Event('change',{bubbles:true}));")
            browser.wait("return document.body.innerText.includes('Confirmar substituição textual')")
            assert '# Comentário' in browser.text()
            browser.click('Preservar texto atual');browser.click('YAML')
            assert browser.js('return document.querySelector("textarea").value')==source
            browser.click('Formulário')
            browser.js("const e=Array.from(document.querySelectorAll('.fields label')).find(l=>l.textContent.includes('pipeline')).querySelector('input'); e.value='compile';e.dispatchEvent(new Event('change',{bubbles:true}));")
            browser.wait("return document.body.innerText.includes('Confirmar substituição textual')");browser.click('Aplicar alteração')
            browser.wait("return document.body.innerText.includes('Semântica válida')")
            browser.click('YAML');edited=browser.js('return document.querySelector("textarea").value')
            status,roundtrip=call('POST','/projects/ec/validation',body={'yaml':edited})
            expected=valid['projection'].copy();expected['pipeline']='compile'
            assert status==200 and roundtrip.get('projection')==expected, {'status':status,'roundtrip':roundtrip,'expected':expected}
            browser.input('textarea',edited+'pipeline: duplicate\n')
            browser.wait("return document.body.innerText.includes('duplicate')")
            assert browser.js("return Array.from(document.querySelectorAll('button')).find(b=>b.textContent==='Publicar rascunho').disabled")
            browser.input('textarea',edited);browser.wait("return document.body.innerText.includes('Semântica válida')")
            # Ignore AbortSignal in injected transport; older validation response must still be fenced.
            browser.js("const original=window.fetch;let first=true;window.fetch=async(url,opts)=>{if(String(url).endsWith('/validation')&&first){first=false;const r=await original(url,{...opts,signal:undefined});await new Promise(resolve=>setTimeout(resolve,900));return r}return original(url,opts)};window.savedFetch=original")
            browser.input('textarea',edited.replace('compile','obsolete'))
            time.sleep(.3)
            browser.input('textarea',edited)
            browser.wait("return document.body.innerText.includes('Semântica válida')")
            time.sleep(1)
            browser.click('Visual')
            assert 'ec / compile' in browser.text() and 'ec / obsolete' not in browser.text()
            browser.click('YAML');browser.js('window.fetch=window.savedFetch')
            passed('Firefox: Visual/Formulário/YAML, preservação de comentário, escolha explícita de perdas, round-trip pelo Server e publicação inválida bloqueada')
            browser.js('window.scrollTo(0,0)')
            screenshot('configuration-light.png')
            browser.click('Visual');browser.js('window.scrollTo(0,0)');screenshot('visual-light.png');browser.click('YAML')
            browser.wait("return Array.from(document.querySelectorAll('button')).some(b=>b.textContent==='Publicar rascunho'&&!b.disabled)");browser.click('Publicar rascunho');browser.wait("return document.body.innerText.includes('Recibo confirmado')")
            assert call('GET','/projects/ec')[1]['published']==1
            revision=call('GET','/projects/ec/revisions/1')[1];assert revision['sha256']==hashlib.sha256(edited.encode()).hexdigest()
            browser.input('input[type=number]',1);browser.click('Submeter revisão escolhida')
            browser.wait("return document.body.innerText.includes('runId')")
            run=call('GET','/projects/ec/runs')[1]['runs'][0]
            root=tmp/'agent';root.mkdir(mode=0o700)
            config=root/'config.json';config.write_text(json.dumps({'address':'127.0.0.1:58139','root':str(root),'credentialFile':str(credential),'repositories':[str(repo)],'once':True}));config.chmod(0o600)
            agent=subprocess.Popen([str(AGENT),str(config)],stdout=subprocess.DEVNULL,stderr=subprocess.PIPE);processes.append(agent)
            _,errors=agent.communicate(timeout=45);assert agent.returncode==0,errors
            browser.click('Execuções');browser.wait("return document.body.innerText.includes('succeeded')")
            browser.click('Execução #'+str(run['runId'])+' · succeeded')
            browser.wait("return document.body.innerText.includes('publicado íntegro no Server')")
            assert '\\u001b' in browser.text() and '�<script>out' in browser.text()
            assert browser.js('return document.querySelectorAll("main script").length')==0
            screenshot('run-real-firefox.png')
            browser.click('Baixar artefato verificado')
            browser.wait("return document.body.innerText.includes('Download verificado')")
            deadline=time.monotonic()+15
            download=tmp/'downloads'/f"ec-run-{run['runId']}-attempt-{call('GET','/projects/ec/runs')[1]['runs'][0]['attempts'][0]['attemptId']}.bin"
            while not download.exists() and time.monotonic()<deadline:time.sleep(.1)
            assert download.read_bytes()==(repo/'pinned').read_bytes()
            state=call('GET','/projects/ec/runs')[1]['runs'][0]
            assert state['revisionId']==1 and state['configHash']==revision['sha256'] and state['commit']==commit
            passed('Firefox + Agent real: publicação, submissão, snapshot, logs stdout/stderr com bytes inválidos e controles, artefato baixado comparado aos bytes Git')
            browser.click('Cancelar #'+str(run['runId']));browser.wait("return document.body.innerText.includes('Sem efeito')")
            browser.click('Configuração');browser.click('YAML');assert browser.js('return document.querySelector("textarea").value')==edited
            # Conflict preserves draft; resolve explicitly, then publish new revision.
            assert call('POST','/projects/ec/publications',body={'requestId':'external','baseRevision':1,'yaml':edited+'# revision 2\n'})[0]==200
            browser.wait("return Array.from(document.querySelectorAll('button')).some(b=>b.textContent==='Publicar rascunho'&&!b.disabled)");browser.click('Publicar rascunho');browser.wait("return document.body.innerText.includes('409')")
            assert browser.js('return document.querySelector("textarea").value')==edited
            browser.click('Comparar e adotar base atual');browser.wait("return Array.from(document.querySelectorAll('button')).some(b=>b.textContent==='Publicar rascunho'&&!b.disabled)");browser.click('Publicar rascunho')
            browser.wait("return document.body.innerText.includes('Recibo confirmado')")
            state=call('GET','/projects/ec/runs')[1]['runs'][0];assert state['revisionId']==1 and state['configHash']==revision['sha256']
            # Browser transport fault after the real committed HTTP response.
            browser.click('Carregar publicada (substitui rascunho)')
            browser.wait("return document.body.innerText.includes('Base 3')")
            browser.js("const original=window.fetch;window.fetch=async(...args)=>{const r=await original(...args);if(String(args[0]).endsWith('/publications')){window.fetch=original;throw new TypeError('Injected response loss after real response')}return r}")
            browser.wait("return Array.from(document.querySelectorAll('button')).some(b=>b.textContent==='Publicar rascunho'&&!b.disabled)");browser.click('Publicar rascunho')
            browser.wait("return document.body.innerText.includes('Resposta incerta')")
            assert call('GET','/projects/ec')[1]['published']==4
            browser.click('Consultar recibo');browser.wait("return document.body.innerText.includes('Recibo recuperado')")
            assert call('GET','/projects/ec')[1]['published']==4
            passed('Firefox: perda de resposta injetada depois do commit HTTP real; recuperação pelo mesmo requestId sem nova publicação')
            # Lost response by real Server failpoint; same request and bytes replayed.
            payload={'requestId':'lost-response','baseRevision':4,'yaml':edited+'# lost response\n'}
            assert call('POST','/projects/ec/publications',body=payload,failure='after-commit')[0]==0
            receipt=call('GET','/projects/ec/receipts/lost-response')[1]
            assert call('POST','/projects/ec/publications',body=payload)[1]==receipt
            passed('Conflito no browser preserva rascunho, resolução explícita e revisão usada imutável; resposta perdida e replay HTTP real (fora da UI)')
            # Queued cancellation is persisted and terminal, confirmed in browser.
            browser.input('input[type=number]',1);browser.click('Submeter revisão escolhida')
            browser.wait("return document.body.innerText.includes('Recibo confirmado')")
            queued=call('GET','/projects/ec/runs')[1]['runs'][-1]
            browser.click('Execuções');browser.wait("return document.body.innerText.includes('queued')")
            browser.click('Cancelar #'+str(queued['runId']))
            browser.wait("return document.body.innerText.includes('Cancelamento confirmado')")
            assert call('GET',f"/projects/ec/runs/{queued['runId']}")[1]['state']=='cancelled'
            # Disconnect then reconnect the polling transport, preserving operational states.
            browser.js("window.originalFetch=window.fetch;window.fetch=async()=>{throw new TypeError('Injected offline')}")
            browser.wait("return document.body.innerText.includes('possivelmente desatualizados')")
            browser.js("window.fetch=window.originalFetch")
            browser.wait("return document.body.innerText.includes('Atualizado pelo Server')")
            passed('Firefox: cancelamento queued confirmado; desconexão explícita e reconexão por polling sem inventar transição')
            # Active process cancellation, then real truncated output + missing artifact.
            def execute(script, name):
                current=call('GET','/projects/ec')[1]['published']
                text=source.replace(valid['projection']['script'],script) if False else '\n'.join([key+': '+json.dumps(script if key=='script' else value) for key,value in valid['projection'].items()])+'\n'
                assert call('POST','/projects/ec/publications',body={'requestId':name,'baseRevision':current,'yaml':text})[0]==200
                rid=call('POST','/projects/ec/runs',body={'requestId':name,'revisionId':current+1})[1]['runId']
                ar=tmp/name;ar.mkdir(mode=0o700);ac=ar/'config.json'
                ac.write_text(json.dumps({'address':'127.0.0.1:58139','root':str(ar),'credentialFile':str(credential),'repositories':[str(repo)],'once':True}));ac.chmod(0o600)
                process=subprocess.Popen([str(AGENT),str(ac)],stdout=subprocess.DEVNULL,stderr=subprocess.PIPE);processes.append(process)
                return rid,process
            active,ap=execute('sleep 20; printf ok > result.txt','active-cancel')
            browser.wait("return document.body.innerText.includes('running')")
            browser.click('Cancelar #'+str(active))
            browser.wait("return document.body.innerText.includes('Intenção persistida')")
            browser.wait("return document.body.innerText.includes('Execução #"+str(active)+" · cancelled')")
            ap.communicate(timeout=40);assert ap.returncode==0
            truncated,tp=execute("head -c 16777217 /dev/zero | tr '\\0' x",'truncated-output')
            tp.communicate(timeout=60);assert tp.returncode==0
            browser.wait("return document.body.innerText.includes('Execução #"+str(truncated)+" · succeeded')",timeout=40)
            browser.click('Execução #'+str(truncated)+' · succeeded')
            browser.wait("return document.body.innerText.includes('Truncado: sim')",timeout=40)
            assert 'artifactError' in browser.text()
            assert browser.js("return Array.from(document.querySelectorAll('button')).filter(b=>b.textContent==='Baixar artefato verificado').every(b=>b.disabled)")
            passed('Firefox: cancelamento de processo running distingue intenção e confirmação; logs reais truncados e artifactError sem download')
            browser.click('Configuração')
            # Responsive dark theme, focus and keyboard native navigation.
            browser.input('header select','dark');browser.command('WebDriver:SetWindowRect',{'width':390,'height':844})
            # System Firefox clamps outer windows to 500px. Native zoom tests reflow below 320 CSS px.
            browser.command('Marionette:SetContext',{'value':'chrome'})
            browser.js('window.gBrowser.selectedBrowser.browsingContext.fullZoom=1.7')
            browser.command('Marionette:SetContext',{'value':'content'})
            browser.wait('return innerWidth<=320')
            assert browser.js('return document.documentElement.scrollWidth<=window.innerWidth')
            browser.js('document.querySelector(".skip").focus()')
            assert browser.js('return document.activeElement.className')=='skip'
            browser.command('WebDriver:PerformActions',{'actions':[{'type':'key','id':'keyboard','actions':[{'type':'keyDown','value':'\ue004'},{'type':'keyUp','value':'\ue004'}]}]})
            assert browser.js('return document.activeElement.tagName')=='SELECT'
            screenshot('configuration-dark-mobile.png')
            browser.click('Revogar e sair');browser.wait("return !!document.querySelector('input[type=password]')")
            login('viewer');choose();yaml(source)
            assert browser.js("return Array.from(document.querySelectorAll('button')).find(b=>b.textContent==='Publicar rascunho').disabled")
            before=h.snapshot()
            assert call('POST','/projects/ec/publications','viewer',{'requestId':'denied','baseRevision':4,'yaml':source})[0]==403
            assert call('POST','/projects/ec/runs','viewer',{'requestId':'denied','revisionId':1})[0]==403
            assert call('POST',f"/projects/ec/runs/{run['runId']}/cancel",'viewer',{})[0]==403
            assert h.snapshot()==before
            browser.click('Execuções');browser.wait("return document.body.innerText.includes('Atualizado pelo Server')")
            h.sql("UPDATE sessions SET expires=clock_timestamp()-interval '1 second' WHERE identity='viewer'")
            browser.wait("return !!document.querySelector('input[type=password]')")
            fresh=tmp/'viewer-fresh';h.cli('provision','viewer','ec','viewer',str(fresh),'local');tokens['viewer']=fresh.read_text()
            login('viewer');choose();browser.click('Execuções');browser.wait("return document.body.innerText.includes('Atualizado pelo Server')");h.cli('revoke','viewer')
            browser.wait("return !!document.querySelector('input[type=password]')")
            assert 'pinned fixture' not in browser.text()
            login('foreign')
            assert browser.js("return !document.querySelector('option[value=ec]')")
            choose('other')
            assert str(repo) not in browser.text()
            browser.wait("return document.body.innerText.includes('Semântica válida')")
            passed('Firefox: tema escuro/mobile sem overflow, foco skip link, viewer desabilitado e HTTP 403 sem publicação; sessão revogada remove dados privados')
            browser.command('Marionette:Quit',{'flags':['eForceQuit']})
            processes[-1].wait(timeout=15)
            (ART/'browser-results.json').write_text(json.dumps({'browser':subprocess.check_output(['/usr/bin/firefox','--version'],text=True).strip(),'results':results,'viewports':viewports,'oracles':{'yamlSha256':revision['sha256'],'runId':run['runId'],'revisionId':1,'commit':commit,'artifactSha256':hashlib.sha256((repo/'pinned').read_bytes()).hexdigest(),'artifactSize':len((repo/'pinned').read_bytes())},'limitations':['Interação de campos usa eventos DOM via Marionette; cliques nativos WebDriver. Sem leitor de tela.','Falhas de transporte injetadas no fetch do Firefox; respostas de domínio e persistência são reais.','Sem teste com leitor de tela ou certificação WCAG completa; foco/teclado e reflow observados neste Firefox.']},ensure_ascii=False,indent=2)+'\n')
    finally:
        for p in reversed(processes):
            if p.poll() is None:
                if p.args[0]=='npm':
                    import signal
                    os.killpg(p.pid,signal.SIGTERM)
                else:p.terminate()
                try:p.wait(timeout=10)
                except subprocess.TimeoutExpired:p.kill();p.wait()
        subprocess.run(['docker','exec',h.CONTAINER,'dropdb','-U','postgres','--force',h.DATABASE],check=True)


if __name__=='__main__':
    main()
