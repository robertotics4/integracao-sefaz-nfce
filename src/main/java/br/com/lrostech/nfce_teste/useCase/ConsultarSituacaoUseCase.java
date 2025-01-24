package br.com.lrostech.nfce_teste.useCase;

import br.com.lrostech.nfce_teste.domain.input.ConsultarSituacaoInput;
import br.com.lrostech.nfce_teste.domain.output.ConsultarSituacaoOutput;
import br.com.lrostech.nfce_teste.support.constants.ConfigConstants;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;

import java.util.List;

public class ConsultarSituacaoUseCase {
    public ConsultarSituacaoOutput executar(ConsultarSituacaoInput input) throws CertificadoException, NfeException {
        ConfiguracoesNfe config = this.criarConfiguracoes(
                input.bytesCertificado(),
                input.senhaCertificado(),
                input.estado(),
                input.ambiente()
        );

        TRetConsSitNFe retornoConsulta = Nfe.consultaXml(config, input.chave(), input.tipoDocumento());

        return this.converterParaRecord(retornoConsulta);
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

    private ConsultarSituacaoOutput converterParaRecord(TRetConsSitNFe tRetConsSitNFe) {
        return new ConsultarSituacaoOutput(
                tRetConsSitNFe.getTpAmb(),
                tRetConsSitNFe.getVerAplic(),
                tRetConsSitNFe.getCStat(),
                tRetConsSitNFe.getXMotivo(),
                tRetConsSitNFe.getCUF(),
                tRetConsSitNFe.getDhRecbto(),
                tRetConsSitNFe.getChNFe(),
                tRetConsSitNFe.getProtNFe(),
                tRetConsSitNFe.getRetCancNFe(),
                tRetConsSitNFe.getProcEventoNFe() != null ? tRetConsSitNFe.getProcEventoNFe() : List.of(),
                tRetConsSitNFe.getVersao()
        );
    }
}
