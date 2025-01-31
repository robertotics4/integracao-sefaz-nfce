package br.com.lrostech.nfce_teste.domain.provider;

import br.com.lrostech.nfce_teste.domain.contract.ISefazClient;
import br.com.lrostech.nfce_teste.domain.input.*;
import br.com.lrostech.nfce_teste.domain.output.*;
import br.com.lrostech.nfce_teste.domain.useCase.*;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.exception.NfeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

@Component
@RequiredArgsConstructor
public class SefazClient implements ISefazClient {
    private final ConsultarSituacaoUseCase consultarSituacaoUseCase;
    private final ConsultarStatusServicoUseCase consultarStatusServicoUseCase;
    private final InutilizarXMLUseCase inutilizarXMLUseCase;
    private final CancelarXMLUseCase cancelarXMLUseCase;
    private final CancelarXMLSubstituicaoUseCase cancelarXMLSubstituicaoUseCase;
    private final EnviarXMLUseCase enviarXMLUseCase;

    public ConsultarSituacaoOutput consultarSituacao(ConsultarSituacaoInput input) throws NfeException, CertificadoException {
        return this.consultarSituacaoUseCase.executar(input);
    }

    public ConsultarStatusServicoOutput consultarStatusServico(ConsultarStatusServicoInput input) throws NfeException, CertificadoException {
        return this.consultarStatusServicoUseCase.executar(input);
    }

    public EnviarXMLOutput enviarXML(EnviarXMLInput input) throws JAXBException, NfeException, CertificadoException {
        return this.enviarXMLUseCase.executar(input);
    }

    public InutilizarXMLOutput inutilizarXML(InutilizarXMLInput input) throws NfeException, CertificadoException {
        return this.inutilizarXMLUseCase.executar(input);
    }

    public CancelarXMLOutput cancelarXML(CancelarXMLInput input) throws NfeException, CertificadoException {
        return this.cancelarXMLUseCase.executar(input);
    }

    public CancelarXMLSubstituicaoOutput cancelarXMLSubstituicao(CancelarXMLSubstituicaoInput input) throws NfeException, CertificadoException {
        return this.cancelarXMLSubstituicaoUseCase.executar(input);
    }
}
