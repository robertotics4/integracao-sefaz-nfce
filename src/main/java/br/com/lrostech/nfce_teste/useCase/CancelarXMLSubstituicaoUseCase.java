package br.com.lrostech.nfce_teste.useCase;

import br.com.lrostech.nfce_teste.domain.input.CancelarXMLSubstituicaoInput;
import br.com.lrostech.nfce_teste.domain.output.CancelarXMLSubstituicaoOutput;
import br.com.lrostech.nfce_teste.support.constants.ConfigConstants;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.Evento;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema.envEventoCancSubst.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envEventoCancSubst.TRetEnvEvento;
import br.com.swconsultoria.nfe.util.CancelamentoSubstituicaoUtil;
import br.com.swconsultoria.nfe.util.RetornoUtil;

import java.util.List;

public class CancelarXMLSubstituicaoUseCase {
    public CancelarXMLSubstituicaoOutput executar(CancelarXMLSubstituicaoInput input) throws CertificadoException, NfeException {
        ConfiguracoesNfe config = this.criarConfiguracoes(
                input.bytesCertificado(),
                input.senhaCertificado(),
                input.estado(),
                input.ambiente()
        );

        Evento eventoCancelamento = new Evento();
        eventoCancelamento.setChave(input.chave());
        eventoCancelamento.setChaveSusbstituta(input.chaveSubstituta());
        eventoCancelamento.setProtocolo(input.protocolo());
        eventoCancelamento.setCnpj(input.cnpj());
        eventoCancelamento.setMotivo(input.motivoCancelamento());
        eventoCancelamento.setDataEvento(input.dataCancelamento());

        TEnvEvento tEnvEvento = CancelamentoSubstituicaoUtil.montaCancelamento(eventoCancelamento, config);
        TRetEnvEvento retorno = Nfe.cancelarSubstituicaoNfe(config, tEnvEvento, input.validaXML());

        RetornoUtil.validaCancelamentoSubstituicao(retorno);

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
