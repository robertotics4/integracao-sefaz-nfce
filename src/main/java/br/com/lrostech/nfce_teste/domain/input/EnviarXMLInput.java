package br.com.lrostech.nfce_teste.domain.input;

import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;

public record EnviarXMLInput(
        String conteudoXML,
        boolean validaXML,
        DocumentoEnum tipoDocumento,
        byte[] bytesCertificado,
        String senhaCertificado,
        EstadosEnum estado,
        AmbienteEnum ambiente
) {
}
