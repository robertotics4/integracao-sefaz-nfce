package br.com.lrostech.nfce_teste.infra.sefaz;

import br.com.lrostech.nfce_teste.domain.input.ConsultarSituacaoInput;
import br.com.lrostech.nfce_teste.domain.input.ConsultarStatusServicoInput;
import br.com.lrostech.nfce_teste.domain.output.ConsultarSituacaoOutput;
import br.com.lrostech.nfce_teste.useCase.ConsultarSituacaoUseCase;
import br.com.lrostech.nfce_teste.useCase.ConsultarStatusServicoUseCase;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;

public class SefazUtilitario {
    private static final ConsultarSituacaoUseCase consultarSituacaoUseCase = new ConsultarSituacaoUseCase();
    private static final ConsultarStatusServicoUseCase consultarStatusServicoUseCase = new ConsultarStatusServicoUseCase();

    private SefazUtilitario() {}

    public static ConsultarSituacaoOutput consultarSituacao(ConsultarSituacaoInput input) throws NfeException, CertificadoException {
        return consultarSituacaoUseCase.executar(input);
    }

    public static TRetConsStatServ consultarStatusServico(ConsultarStatusServicoInput input) throws NfeException, CertificadoException {
        return consultarStatusServicoUseCase.executar(input);
    }
}
