package br.com.lrostech.nfce_teste.domain.useCase;

import br.com.lrostech.nfce_teste.domain.contract.ICertificadoUtil;
import br.com.lrostech.nfce_teste.domain.contract.INfeLib;
import br.com.lrostech.nfce_teste.domain.input.ConsultarStatusServicoInput;
import br.com.lrostech.nfce_teste.domain.output.ConsultarStatusServicoOutput;
import br.com.lrostech.nfce_teste.support.constants.ConfigConstants;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsultarStatusServicoUseCase {
    private final ICertificadoUtil certificadoUtil;
    private final INfeLib nfeLib;

    public ConsultarStatusServicoOutput executar(ConsultarStatusServicoInput input) throws NfeException, CertificadoException {
        Certificado certificado = this.certificadoUtil.certificadoPfxBytes(input.bytesCertificado(), input.senhaCertificado());
        ConfiguracoesNfe config = this.nfeLib.criarConfiguracoes(
                input.estado(),
                input.ambiente(),
                certificado,
                ConfigConstants.SCHEMAS_PATH
        );

        TRetConsStatServ retornoSefaz = this.nfeLib.statusServico(config, input.tipoDocumento());

        return this.converterParaRecord(retornoSefaz);
    }

    private ConsultarStatusServicoOutput converterParaRecord(TRetConsStatServ tRetConsStatServ) {
        return new ConsultarStatusServicoOutput(
                tRetConsStatServ.getTpAmb(),
                tRetConsStatServ.getVerAplic(),
                tRetConsStatServ.getCStat(),
                tRetConsStatServ.getXMotivo(),
                tRetConsStatServ.getCUF(),
                tRetConsStatServ.getDhRecbto(),
                tRetConsStatServ.getTMed(),
                tRetConsStatServ.getDhRetorno(),
                tRetConsStatServ.getXObs(),
                tRetConsStatServ.getVersao()
        );
    }
}
