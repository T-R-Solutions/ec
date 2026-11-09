from pathlib import Path
import hashlib,json,zipfile
root=Path.cwd();out=root/'docs/validation/tooling-remediation-20261106'
report='docs/validation/tooling-remediation-20261106/report.md'
def digest(path):return hashlib.sha256((root/path).read_bytes()).hexdigest()
with zipfile.ZipFile(out/'previous-evidence.zip') as z:
 for feature in ['governance-adoption','first-functional-journey','scripts-verification']:
  path=f'sdd/features/{feature}/evidence.json';old=json.loads(z.read(path));requirements=old['requirements'];identifier='E-REMEDIATION-20261107-'+feature.upper()
  for row in requirements:
   if feature=='scripts-verification' and row['id']=='AC-04':row['status']='REJECTED'
   if row['status'] in ['APPROVED','REJECTED']:row['evidence_ids']=[identifier]
  inputs={item['path']:item.get('role','other') for record in old['evidence'] for item in record['inputs']}
  observed=json.loads((root/'.local/automation/runs/run-QqUf7ohQ/inputs.json').read_text())
  for name in observed:
   if '/evidence.json' not in name:inputs.setdefault(name,'implementation')
  inputs['docs/validation/tooling-remediation-20261106/shared-inputs.json']='configuration'
  current={'schema_version':2,'environment':'Linux openSUSE/SELinux; Rust 1.99.1, Cargo Machete 0.9.2/Deny 0.20.2 globais observados; Ruff 0.11.13, ShellCheck 0.11.0, Opengrep 1.33.1, OSV 2.7.0 e Gitleaks em imagens toolbox, rede none; PostgreSQL/Firefox reais.','requirements':requirements,'evidence':[{'id':identifier,'kind':'test','command_or_method':'Revisão contextual dos 102 alertas; runner completo run-QqUf7ohQ; reexecução E2E run-y02SwyvS e fechamento docs/scripts run-ajzm1IER aprovado; 43 testes shell, 34 de governança e 23 da plataforma. Novos relatórios e limitações no artefato. Registros anteriores preservados em previous-evidence.zip.','observed_result':'Ruff/ShellCheck limpos, oito testes de regras e SAST completo, OSV nos três lockfiles sem achados após atualizações; Cargo Machete/Audit passaram. Deny executou: advisories/bans/sources aprovados e licenças rejeitadas por 11 crates Ec sem licença. Integração/E2E e fechamento documental conforme report.md; nenhum critério de produto anteriormente UNVERIFIED foi promovido.','artifact':report,'sha256':digest(report),'inputs':[{'path':name,'sha256':digest(name),'role':role} for name,role in sorted(inputs.items()) if name!=path and (root/name).is_file()]}],'limitations':['Deny reprova os 11 crates privados Ec sem licença; nenhuma licença foi inventada nem exceção aplicada sem decisão do usuário.','AC-04 de scripts está REJECTED pelo gate de licenças. AC-01/AC-07 da primeira jornada mantêm UNVERIFIED e suas lacunas próprias.','Ruff: 28 alertas corrigidos e 74 de subprocesso/PATH justificados por ocorrência; PATH pressupõe operador confiável. SAST usa oito regras locais e inspeção textual de Vue; não certifica segurança completa.','Hashes oficiais de artefatos foram conferidos; assinaturas não foram verificadas. Bases OSV por HTTPS com hash de snapshot e validade de 24h; isso não prova frescor upstream.','Observações anteriores preservadas em docs/validation/tooling-remediation-20261106/previous-evidence.zip; nenhuma evidência histórica foi renovada.']}
  (root/path).write_text(json.dumps(current,ensure_ascii=False,indent=2)+'\n')
