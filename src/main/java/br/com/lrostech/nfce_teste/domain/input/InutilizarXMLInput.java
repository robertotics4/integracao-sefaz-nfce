package br.com.lrostech.nfce_teste.domain.input;

import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;

import java.time.LocalDateTime;

public record InutilizarXMLInput(
        String cnpj,
        Integer serie,
        Integer numeroInicial,
        Integer numeroFinal,
        String justificativa,
        LocalDateTime dataInutilizacao,
        boolean validaXML,
        DocumentoEnum tipoDocumento,
        byte[] bytesCertificado,
        String senhaCertificado,
        EstadosEnum estado,
        AmbienteEnum ambiente
) {
}
