if .SchemaVersion == 2 and (has("Results") | not) then .Results = [] else . end |
# O relatório persistido contém identificação/localização, sem valor/trecho do segredo.
if (.Results|type)!="array" then error("Results Trivy inválido") else
  .Results |= map(
    if ((.ExperimentalModifiedFindings//[])|length)>0 then error("supressões Trivy proibidas") else . end |
    if .Secrets then .Secrets |= map({RuleID,Category,Severity,Title,StartLine,EndLine}) else . end |
    if .Misconfigurations then .Misconfigurations |= map(del(.CauseMetadata.Code)) else . end
  )
end
