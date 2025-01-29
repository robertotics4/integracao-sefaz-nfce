package br.com.lrostech.nfce_teste.useCase;

import br.com.lrostech.nfce_teste.domain.input.InutilizarXMLInput;
import br.com.lrostech.nfce_teste.domain.output.InutilizarXMLOutput;
import br.com.lrostech.nfce_teste.support.constants.ConfigConstants;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TInutNFe;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TRetInutNFe;
import br.com.swconsultoria.nfe.util.InutilizacaoUtil;
import br.com.swconsultoria.nfe.util.RetornoUtil;

public class InutilizarXMLUseCase {
    public InutilizarXMLOutput executar(InutilizarXMLInput input) throws CertificadoException, NfeException {
        ConfiguracoesNfe config = this.criarConfiguracoes(
                input.bytesCertificado(),
                input.senhaCertificado(),
                input.estado(),
                input.ambiente()
        );

        TInutNFe inutNFe = InutilizacaoUtil.montaInutilizacao(
                input.tipoDocumento(),
                input.cnpj(),
                input.serie(),
                input.numeroInicial(),
                input.numeroFinal(),
                input.justificativa(),
                input.dataInutilizacao(),
                config
        );

        TRetInutNFe retorno = Nfe.inutilizacao(config, inutNFe, input.tipoDocumento(), input.validaXML());

        RetornoUtil.validaInutilizacao(retorno);

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

    private InutilizarXMLOutput converterParaRecord(TRetInutNFe tRetInutNFe) {
        if (tRetInutNFe == null || tRetInutNFe.getInfInut() == null) {
            return null;
        }

        TRetInutNFe.InfInut infInut = tRetInutNFe.getInfInut();

        return new InutilizarXMLOutput(
                infInut.getTpAmb(),
                infInut.getVerAplic(),
                infInut.getCStat(),
                infInut.getXMotivo(),
                infInut.getCUF(),
                infInut.getAno(),
                infInut.getCNPJ(),
                infInut.getMod(),
                infInut.getSerie(),
                infInut.getNNFIni(),
                infInut.getNNFFin(),
                infInut.getDhRecbto(),
                infInut.getNProt(),
                tRetInutNFe.getVersao()
        );
    }
}
