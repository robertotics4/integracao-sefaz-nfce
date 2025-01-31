package br.com.lrostech.nfce_teste.domain.useCase;

import br.com.lrostech.nfce_teste.domain.contract.ICertificadoUtil;
import br.com.lrostech.nfce_teste.domain.contract.INfeLib;
import br.com.lrostech.nfce_teste.domain.input.CancelarXMLInput;
import br.com.lrostech.nfce_teste.domain.output.CancelarXMLOutput;
import br.com.lrostech.nfce_teste.support.constants.ConfigConstants;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.Evento;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TRetEnvEvento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CancelarXMLUseCase {
    private final ICertificadoUtil certificadoUtil;
    private final INfeLib nfeLib;

    public CancelarXMLOutput executar(CancelarXMLInput input) throws CertificadoException, NfeException {
        Certificado certificado = this.certificadoUtil.certificadoPfxBytes(input.bytesCertificado(), input.senhaCertificado());
        ConfiguracoesNfe config = this.nfeLib.criarConfiguracoes(
                input.estado(),
                input.ambiente(),
                certificado,
                ConfigConstants.SCHEMAS_PATH
        );

        Evento eventoCancelamento = new Evento();
        eventoCancelamento.setChave(input.chave());
        eventoCancelamento.setProtocolo(input.protocolo());
        eventoCancelamento.setCnpj(input.cnpj());
        eventoCancelamento.setMotivo(input.motivoCancelamento());
        eventoCancelamento.setDataEvento(input.dataCancelamento());

        TEnvEvento tEnvEvento = this.nfeLib.montaCancelamento(eventoCancelamento, config);
        TRetEnvEvento retorno = this.nfeLib.cancelarNfe(config, tEnvEvento, input.validaXML(), input.tipoDocumento());

        this.nfeLib.validaCancelamento(retorno);

        return this.converterParaRecord(retorno);
    }

    private CancelarXMLOutput converterParaRecord(TRetEnvEvento tRetEnvEvento) {
        if (tRetEnvEvento == null) {
            return null;
        }

        return new CancelarXMLOutput(
                tRetEnvEvento.getIdLote(),
                tRetEnvEvento.getTpAmb(),
                tRetEnvEvento.getVerAplic(),
                tRetEnvEvento.getCOrgao(),
                tRetEnvEvento.getCStat(),
                tRetEnvEvento.getXMotivo(),
                tRetEnvEvento.getRetEvento() != null ? tRetEnvEvento.getRetEvento() : List.of(),
                tRetEnvEvento.getVersao()
        );
    }
}
