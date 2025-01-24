package br.com.lrostech.nfce_teste.infra.sefaz;

import br.com.lrostech.nfce_teste.domain.input.ConsultarSituacaoInput;
import br.com.lrostech.nfce_teste.domain.output.ConsultarSituacaoOutput;
import br.com.lrostech.nfce_teste.useCase.ConsultarSituacaoUseCase;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.exception.NfeException;

public class SefazUtilitario {
    private static final ConsultarSituacaoUseCase consultarSituacaoUseCase = new ConsultarSituacaoUseCase();

    private SefazUtilitario() {}

    public static ConsultarSituacaoOutput consultarSituacao(ConsultarSituacaoInput input) throws NfeException, CertificadoException {
        return consultarSituacaoUseCase.executar(input);
    }
}
