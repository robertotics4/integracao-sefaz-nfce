package br.com.lrostech.nfce_teste.domain.output;

import br.com.swconsultoria.nfe.schema.envEventoCancSubst.TRetEvento;

import java.util.List;

public record CancelarXMLSubstituicaoOutput(
        String idLote,
        String tpAmb,
        String verAplic,
        String cOrgao,
        String cStat,
        String xMotivo,
        List<TRetEvento> retEvento,
        String versao
) {}
