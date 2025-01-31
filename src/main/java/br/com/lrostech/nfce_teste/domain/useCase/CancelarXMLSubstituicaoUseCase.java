package br.com.lrostech.nfce_teste.domain.useCase;

import br.com.lrostech.nfce_teste.domain.contract.ICertificadoUtil;
import br.com.lrostech.nfce_teste.domain.contract.INfeLib;
import br.com.lrostech.nfce_teste.domain.input.CancelarXMLSubstituicaoInput;
import br.com.lrostech.nfce_teste.domain.output.CancelarXMLSubstituicaoOutput;
import br.com.lrostech.nfce_teste.support.constants.ConfigConstants;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.Evento;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema.envEventoCancSubst.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envEventoCancSubst.TRetEnvEvento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CancelarXMLSubstituicaoUseCase {
    private final ICertificadoUtil certificadoUtil;
    private final INfeLib nfeLib;

    public CancelarXMLSubstituicaoOutput executar(CancelarXMLSubstituicaoInput input) throws CertificadoException, NfeException {
        Certificado certificado = this.certificadoUtil.certificadoPfxBytes(input.bytesCertificado(), input.senhaCertificado());
        ConfiguracoesNfe config = this.nfeLib.criarConfiguracoes(
                input.estado(),
                input.ambiente(),
                certificado,
                ConfigConstants.SCHEMAS_PATH
        );

        Evento eventoCancelamento = new Evento();
        eventoCancelamento.setChave(input.chave());
        eventoCancelamento.setChaveSusbstituta(input.chaveSubstituta());
        eventoCancelamento.setProtocolo(input.protocolo());
        eventoCancelamento.setCnpj(input.cnpj());
        eventoCancelamento.setMotivo(input.motivoCancelamento());
        eventoCancelamento.setDataEvento(input.dataCancelamento());

        TEnvEvento tEnvEvento = this.nfeLib.montaCancelamentoSubstituicao(eventoCancelamento, config);
        TRetEnvEvento retorno = this.nfeLib.cancelarSubstituicaoNfe(config, tEnvEvento, input.validaXML());

        this.nfeLib.validaCancelamentoSubstituicao(retorno);

        return this.converterParaRecord(retorno);
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

    private CancelarXMLSubstituicaoOutput converterParaRecord(TRetEnvEvento tRetEnvEvento) {
        if (tRetEnvEvento == null) {
            return null;
        }

        return new CancelarXMLSubstituicaoOutput(
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
