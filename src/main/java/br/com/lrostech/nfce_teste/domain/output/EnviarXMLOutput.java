package br.com.lrostech.nfce_teste.domain.output;

import br.com.swconsultoria.nfe.schema_4.enviNFe.TProtNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TRetEnviNFe;

public record EnviarXMLOutput(
        String tpAmb,
        String verAplic,
        String cStat,
        String xMotivo,
        String cuf,
        String dhRecbto,
        TRetEnviNFe.InfRec infRec,
        TProtNFe protNFe,
        String versao
) {
}
