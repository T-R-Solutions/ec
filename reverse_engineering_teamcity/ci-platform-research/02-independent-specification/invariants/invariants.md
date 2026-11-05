# Invariantes normativos

I01: ack de submissão implica run, jobs, chave de idempotência e auditoria confirmados juntos.
I02: no máximo uma tentativa com lease ativo por job e uma reserva por slot.
I03: fence novo é estritamente maior; nenhuma mensagem antiga altera estado, logs ou manifesto atual.
I04: estado terminal é imutável. Retry cria Attempt novo; nunca reabre uma tentativa terminal.
I05: configuração e revisão Git não mudam dentro de Run.
I06: sucesso exige exit codes válidos, logs confirmados até last_seq e manifesto obrigatório committed.
I07: autenticação não substitui autorização por projeto/pool/attempt.
I08: banco é autoridade de tempo e estado; canais e notificações não são fontes duráveis.
I09: cancelamento e conclusão disputam o mesmo lock/CAS; primeira transação válida prevalece.
I10: efeitos externos não são exatamente uma vez; lease controla aceitação lógica, não desfaz comandos.
I11: GC não remove objetos referenciados; acesso a bytes sempre verifica escopo do projeto.
I12: limites de memória, disco, log, tempo e concorrência são aplicados e observáveis.
