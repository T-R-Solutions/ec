# Método e reprodução

Foi usada a skill `/home/rodrigo/.agents/skills/reverse-engineer-anything/SKILL.md`, versão 49. REA CLI 6.8.0 produziu reconhecimento de diretório com Evidence preservada; não foi usado como descompilador JVM. `rea doctor` mostrou registro Codex alinhado, mas ferramentas REA MCP ausentes na sessão e engines nativos não configurados. Nenhuma configuração do cliente foi alterada.

A inspeção externa usa ASM 9.11.1, `javap` do Microsoft OpenJDK 25.0.4.1 e CFR 0.152. Os JARs das ferramentas estão em `rea/tools/`. Foram obtidos dos sites oficiais do CFR e do Maven Central indicado pelo ASM. Hashes locais fixam identidade; não foi feita validação criptográfica de assinatura do fornecedor.

O inventário usa `os.walk` sem seguir symlinks e calcula SHA-256 integral dos arquivos legíveis. ZIP/JAR/WAR e arquivos `.BACKUP` ZIP são percorridos recursivamente até profundidade 12. Outros formatos compactados/nativos permanecem inventariados sem reconstrução semântica. Arquivos de runtime são apenas metadados/hash; seu conteúdo não é exportado. Não se descobrem Data Directories externos ao alvo.

O leitor ZIP emitiu avisos sobre entradas sobrepostas. Isso não estabelece corrupção ou exploração: nenhuma validação estrutural independente foi executada. Não houve erro de leitura registrado no inventário. O índice distingue ocorrências de classes e conteúdos distintos por hash; versões alternativas, backups e cópias não representam automaticamente classes carregadas.

O ASM preserva hierarquia, assinaturas, flags, descriptors, annotations por tipo, campos, constantes e métodos. No recorte de namespaces `jetbrains/`, `com/jetbrains/` e `org/jetbrains/teamcity/`, percorre corpos e registra chamadas, acesso a campos, variáveis locais disponíveis, saltos e handlers. Os demais namespaces têm assinaturas catalogadas. Valores detalhados das annotations REST são extraídos separadamente. Não há análise completa de pilha, aliasing, reflection ou escape. Métodos sintéticos e Kotlin Metadata são identificáveis, mas nomes de variáveis descompiladas não provam código original.

O primeiro serializador ASM deixou alguns controles JSON literais. O problema foi corrigido no código e o arquivo foi normalizado sem perder registros (`normalize_symbols.py`); a ingestão do catálogo original foi tolerante a esses controles. Não se alterou bytecode.

Scripts reproduzíveis, a partir da raiz do repositório:

```bash
javac -cp rea/tools/asm-9.11.1.jar rea/tools/IndexClasses.java
python3 rea/tools/inventory.py
python3 rea/tools/catalog.py
python3 rea/tools/inspect_focus.py
python3 rea/tools/inspect_extra.py
python3 rea/tools/inspect_more.py
python3 rea/tools/inspect_nested.py
python3 rea/tools/resources.py
python3 rea/tools/consolidate.py
```

`RestIndex.java` recebe o JAR do plugin REST e produz `25-evidencias/rest-annotations.jsonl`; sua compilação usa `rea/tools:rea/tools/asm-9.11.1.jar`. Não repetir produtores completos quando hashes e checkpoint correspondem: usar `query.py` e as evidências preservadas. O REA exporta bundles sem sobrescrever; um novo reconhecimento deve usar outro nome.

Os registros `STATIC_CONFIRMED` se referem exclusivamente aos artefatos. `DYNAMIC_CONFIRMED` não foi usado. Denominadores de cobertura separam filesystem, ocorrências/classes distintas, declarações de métodos e revisão funcional. Métodos semanticamente recuperados permanecem não mensurados.
