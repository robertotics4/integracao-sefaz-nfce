package br.com.lrostech.nfce_teste.domain.useCase;

import br.com.lrostech.nfce_teste.domain.contract.ICertificadoUtil;
import br.com.lrostech.nfce_teste.domain.contract.INfeLib;
import br.com.lrostech.nfce_teste.domain.input.ConsultarSituacaoInput;
import br.com.lrostech.nfce_teste.domain.output.ConsultarSituacaoOutput;
import br.com.lrostech.nfce_teste.support.constants.ConfigConstants;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsultarSituacaoUseCase {
    private final ICertificadoUtil certificadoUtil;
    private final INfeLib nfeLib;

    public ConsultarSituacaoOutput executar(ConsultarSituacaoInput input) throws CertificadoException, NfeException {
        Certificado certificado = this.certificadoUtil.certificadoPfxBytes(input.bytesCertificado(), input.senhaCertificado());
        ConfiguracoesNfe config = this.nfeLib.criarConfiguracoes(
                input.estado(),
                input.ambiente(),
                certificado,
                ConfigConstants.SCHEMAS_PATH
        );

        TRetConsSitNFe retornoConsulta = this.nfeLib.consultaXml(config, input.chave(), input.tipoDocumento());

        return this.converterParaRecord(retornoConsulta);
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
