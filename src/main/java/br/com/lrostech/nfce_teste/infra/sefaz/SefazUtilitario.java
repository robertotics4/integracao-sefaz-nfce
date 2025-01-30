package br.com.lrostech.nfce_teste.infra.sefaz;

import br.com.lrostech.nfce_teste.domain.input.*;
import br.com.lrostech.nfce_teste.domain.output.*;
import br.com.lrostech.nfce_teste.useCase.*;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.exception.NfeException;

import javax.xml.bind.JAXBException;

public class SefazUtilitario {
    private static final ConsultarSituacaoUseCase consultarSituacaoUseCase = new ConsultarSituacaoUseCase();
    private static final ConsultarStatusServicoUseCase consultarStatusServicoUseCase = new ConsultarStatusServicoUseCase();
    private static final EnviarXMLUseCase enviarXMLUseCase = new EnviarXMLUseCase();
    private static final InutilizarXMLUseCase inutilizarXMLUseCase = new InutilizarXMLUseCase();
    private static final CancelarXMLUseCase cancelarXMLUseCase = new CancelarXMLUseCase();
    private static final CancelarXMLSubstituicaoUseCase cancelarXMLSubstituicaoUseCase = new CancelarXMLSubstituicaoUseCase();

    private SefazUtilitario() {}

    public static ConsultarSituacaoOutput consultarSituacao(ConsultarSituacaoInput input) throws NfeException, CertificadoException {
        return consultarSituacaoUseCase.executar(input);
    }

    public static ConsultarStatusServicoOutput consultarStatusServico(ConsultarStatusServicoInput input) throws NfeException, CertificadoException {
        return consultarStatusServicoUseCase.executar(input);
    }

    public static EnviarXMLOutput enviarXML(EnviarXMLInput input) throws JAXBException, NfeException, CertificadoException {
        return enviarXMLUseCase.executar(input);
    }

    public static InutilizarXMLOutput inutilizarXML(InutilizarXMLInput input) throws NfeException, CertificadoException {
        return inutilizarXMLUseCase.executar(input);
    }

    public static CancelarXMLOutput cancelarXML(CancelarXMLInput input) throws NfeException, CertificadoException {
        return cancelarXMLUseCase.executar(input);
    }

    public static CancelarXMLSubstituicaoOutput cancelarXMLSubstituicao(CancelarXMLSubstituicaoInput input) throws NfeException, CertificadoException {
        return cancelarXMLSubstituicaoUseCase.executar(input);
    }
}
