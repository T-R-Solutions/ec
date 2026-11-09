// Assinatura do arquivo de teste antivírus EICAR. Não constitui catálogo universal de malware.
rule Ec_Eicar_Test_File {
    meta:
        description = "Detecta a assinatura de teste EICAR em arquivos analisados"
        reference = "https://www.eicar.org/download-anti-malware-testfile/"
    strings:
        $signature = "EICAR-STANDARD-ANTIVIRUS-TEST-FILE"
    condition:
        $signature
}
