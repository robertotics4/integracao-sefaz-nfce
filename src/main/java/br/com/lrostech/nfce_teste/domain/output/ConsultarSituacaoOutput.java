package br.com.lrostech.nfce_teste.domain.output;

import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TProcEvento;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TProtNFe;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetCancNFe;

import java.util.List;

public record ConsultarSituacaoOutput(
        String tpAmb,
        String verAplic,
        String cStat,
        String xMotivo,
        String cuf,
        String dhRecbto,
        String chNFe,
        TProtNFe protNFe,
        TRetCancNFe retCancNFe,
        List<TProcEvento> procEventoNFe,
        String versao
) {}
