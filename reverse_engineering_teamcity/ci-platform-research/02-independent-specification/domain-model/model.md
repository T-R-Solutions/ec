# Modelo de domínio independente

Project tem escopo de autorização e quota. PipelineRevision é imutável, com digest e schema_version.
Run é uma invocação sobre revisão de configuração e source_revision fixadas; contém Jobs.
Job é a unidade de scheduling, contém Steps ordenados e política de retry. Attempt é uma execução física do Job.
AgentSession identifica um processo registrado; Slot é capacidade reservável. Lease dá autoridade temporária
sobre Attempt, com fence monotônico por Job. Logs e ArtifactManifest pertencem à tentativa, nunca apenas ao Job.
SecretRef identifica autorização de segredo, sem valor na configuração. AuditEvent registra mudança de domínio.

IDs distintos: ProjectId, RunId, JobId, AttemptId, AgentId, SessionId, SlotId; não intercambiáveis.
CompletedAttempt conserva outcome e recibo mesmo após vencimento do lease, para responder retry idêntico.
Release/deploy é step com efeito externo; deve usar chave idempotente do destino ou aprovação própria.
