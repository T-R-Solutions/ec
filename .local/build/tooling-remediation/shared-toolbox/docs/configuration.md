# Configuração declarativa do consumidor

`toolbox.json` é JSON estrito, sem comentários ou campos duplicados. O [JSON Schema](consumer.schema.json) fornece autocomplete/validação estrutural; `toolbox validate --config ...` é a validação normativa, incluindo caminhos, versões disponíveis e constraints específicos. Não requer biblioteca de validação no host.

```json
{
  "schema": 1,
  "project": "example-python",
  "root": ".",
  "tools": {
    "ruff": {
      "version": "0.11.13",
      "config": "ruff.toml",
      "paths": ["src", "tests"],
      "timeout": 120,
      "network": "none"
    }
  }
}
```

`root` é relativo ao arquivo de configuração, ou absoluto. `project` precisa ser estável e único entre consumidores. Caminhos de fontes/configuração ficam dentro do root; glob shell não é expandido. Espaços são preservados como argumentos separados. Vírgulas e quebras de linha nos caminhos de mounts são rejeitadas com diagnóstico, devido ao formato CSV do Docker.

Perfis são seleções nomeadas em `profiles.json`. Não fixam implicitamente versões: cada membro precisa de entrada em `tools`. `--only nome` seleciona uma ferramenta ou perfil configurado. Sem `--only`, executa todas as ferramentas declaradas. Uma ferramenta pode ser `enabled: false` (omitted) ou `applicable: false` (inapplicable), com `reason` obrigatório. Regras continuam em arquivos nativos do consumidor.

`version` é exata. Playwright tem `variant` explícita; Gitleaks permite `mode: files` ou `history`. ShellCheck exige `paths` com arquivos. `env` aceita inicialmente somente `BASE_URL`. Não há passagem arbitrária de flags que possam instalar pacotes, atualizar bases, alterar exit codes ou sobrescrever fontes.

Grype exige `database` como SHA256 retornado por `update-db` e `max_database_age_hours` positivo escolhido pelo consumidor. O campo controla a validação nativa da base, não a data de cópia. Nenhuma idade ou severidade foi escolhida para produzir aprovação.

`prepare` cria, ao lado de `toolbox.json`, `toolbox.images.lock.json`. Versione ambos: o lock registra IDs locais completos, base por digest, arquitetura, versão observada e fontes/hashes. Configuração e versão/variante do lock precisam coincidir. O runtime nunca altera esse lock. IDs Docker locais e digests de registry são identidades diferentes; os registros mantêm essa distinção.

## Estados e códigos da CLI

| Estado agregado | Significado |
|---|---|
| `success` | Ferramenta executada, relatório válido, sem achados |
| `findings` | Achados conforme contrato do adapter e configuração nativa |
| `operational_failure` | Comando, relatório, timeout, cancelamento ou erro operacional |
| `prerequisite_missing` | Imagem, daemon ou integração ainda não disponível |
| `measurement` | Medição sem política de aprovação; reservado para SBOM/cobertura/unsafe |
| `omitted` | Omitida explicitamente com motivo, ou após cancelamento |
| `inapplicable` | Consumidor declarou ferramenta inaplicável com motivo |

Exit da CLI: `0` somente quando todas as ferramentas selecionadas executaram com sucesso; `1` achados sem falhas; `2` falha/pré-requisito ausente; `3` medição/omissão/inaplicabilidade ou seleção vazia. Interrupção antes de iniciar o runner pode retornar `130`. `result.json` preserva o exit original do container separadamente do exit do cliente Docker. A CLI não transforma execução ausente em aprovação.

## Localização dos volumes

Os consumidores não escolhem pastas de infraestrutura. Cada ferramenta persiste dados em `shared-toolbox/tools/<tool>/volume/`, com separação interna por projeto/run, versão e snapshot. `--state`/`TOOLBOX_STATE` controlam apenas o índice agregado e locks gerais, com padrão `shared-toolbox/.local/state/`. Os dados montados continuam dentro do módulo mesmo com índice personalizado. Fontes e configuração nativa permanecem no consumidor.
