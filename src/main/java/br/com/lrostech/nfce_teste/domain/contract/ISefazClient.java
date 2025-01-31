package br.com.lrostech.nfce_teste.domain.contract;

import br.com.lrostech.nfce_teste.domain.input.*;
import br.com.lrostech.nfce_teste.domain.output.*;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.exception.NfeException;

import javax.xml.bind.JAXBException;

public interface ISefazClient {
    ConsultarSituacaoOutput consultarSituacao(ConsultarSituacaoInput input) throws NfeException, CertificadoException;
    ConsultarStatusServicoOutput consultarStatusServico(ConsultarStatusServicoInput input) throws NfeException, CertificadoException;
    InutilizarXMLOutput inutilizarXML(InutilizarXMLInput input) throws NfeException, CertificadoException;
    CancelarXMLOutput cancelarXML(CancelarXMLInput input) throws NfeException, CertificadoException;
    CancelarXMLSubstituicaoOutput cancelarXMLSubstituicao(CancelarXMLSubstituicaoInput input) throws NfeException, CertificadoException;
    EnviarXMLOutput enviarXML(EnviarXMLInput input) throws JAXBException, NfeException, CertificadoException;
}
