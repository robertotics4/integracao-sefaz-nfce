package br.com.lrostech.nfce_teste.useCase;

import br.com.lrostech.nfce_teste.domain.input.ConsultarStatusServicoInput;
import br.com.lrostech.nfce_teste.domain.output.ConsultarStatusServicoOutput;
import br.com.lrostech.nfce_teste.support.constants.ConfigConstants;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;


public class ConsultarStatusServicoUseCase {
    public ConsultarStatusServicoOutput executar(ConsultarStatusServicoInput input) throws NfeException, CertificadoException {
        ConfiguracoesNfe config = this.criarConfiguracoes(
                input.bytesCertificado(),
                input.senhaCertificado(),
                input.estado(),
                input.ambiente()
        );

        TRetConsStatServ retornoSefaz = Nfe.statusServico(config, input.tipoDocumento());

        return this.converterParaRecord(retornoSefaz);
    }

    private ConfiguracoesNfe criarConfiguracoes(
            byte[] bytesCertificado,
            String senhaCertificado,
            EstadosEnum estado,
            AmbienteEnum ambiente
    ) throws CertificadoException {
        Certificado certificado = CertificadoService.certificadoPfxBytes(bytesCertificado, senhaCertificado);
        return ConfiguracoesNfe.criarConfiguracoes(estado, ambiente, certificado, ConfigConstants.SCHEMAS_PATH);
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
