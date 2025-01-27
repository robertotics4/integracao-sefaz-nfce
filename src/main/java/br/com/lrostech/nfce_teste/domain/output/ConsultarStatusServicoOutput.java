package br.com.lrostech.nfce_teste.domain.output;

public record ConsultarStatusServicoOutput(
        String tpAmb,
        String verAplic,
        String cStat,
        String xMotivo,
        String cuf,
        String dhRecbto,
        String tMed,
        String dhRetorno,
        String xObs,
        String versao
) {
}
