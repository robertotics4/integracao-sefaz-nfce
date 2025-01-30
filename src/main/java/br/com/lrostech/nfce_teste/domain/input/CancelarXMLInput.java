package br.com.lrostech.nfce_teste.domain.input;

import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;

import java.time.LocalDateTime;

public record CancelarXMLInput(
        String chave,
        String protocolo,
        String cnpj,
        String motivoCancelamento,
        LocalDateTime dataCancelamento,
        boolean validaXML,
        DocumentoEnum tipoDocumento,
        byte[] bytesCertificado,
        String senhaCertificado,
        EstadosEnum estado,
        AmbienteEnum ambiente
) {
}
