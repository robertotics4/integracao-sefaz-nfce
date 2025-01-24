package br.com.lrostech.nfce_teste.useCase;

import br.com.lrostech.nfce_teste.domain.input.ConsultarStatusServicoInput;
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
    public TRetConsStatServ executar(ConsultarStatusServicoInput input) throws NfeException, CertificadoException {
        ConfiguracoesNfe config = this.criarConfiguracoes(
                input.bytesCertificado(),
                input.senhaCertificado(),
                input.estado(),
                input.ambiente()
        );

        return Nfe.statusServico(config, input.tipoDocumento());
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
}
