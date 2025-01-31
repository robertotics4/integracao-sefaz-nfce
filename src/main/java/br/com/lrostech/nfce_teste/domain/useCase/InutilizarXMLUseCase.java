package br.com.lrostech.nfce_teste.domain.useCase;

import br.com.lrostech.nfce_teste.domain.contract.ICertificadoUtil;
import br.com.lrostech.nfce_teste.domain.contract.INfeLib;
import br.com.lrostech.nfce_teste.domain.input.InutilizarXMLInput;
import br.com.lrostech.nfce_teste.domain.output.InutilizarXMLOutput;
import br.com.lrostech.nfce_teste.support.constants.ConfigConstants;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TInutNFe;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TRetInutNFe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InutilizarXMLUseCase {
    private final ICertificadoUtil certificadoUtil;
    private final INfeLib nfeLib;

    public InutilizarXMLOutput executar(InutilizarXMLInput input) throws CertificadoException, NfeException {
        Certificado certificado = this.certificadoUtil.certificadoPfxBytes(input.bytesCertificado(), input.senhaCertificado());
        ConfiguracoesNfe config = this.nfeLib.criarConfiguracoes(
                input.estado(),
                input.ambiente(),
                certificado,
                ConfigConstants.SCHEMAS_PATH
        );

        TInutNFe inutNFe = this.nfeLib.montaInutilizacao(
                input.tipoDocumento(),
                input.cnpj(),
                input.serie(),
                input.numeroInicial(),
                input.numeroFinal(),
                input.justificativa(),
                input.dataInutilizacao(),
                config
        );

        TRetInutNFe retorno = this.nfeLib.inutilizacao(config, inutNFe, input.tipoDocumento(), input.validaXML());

        this.nfeLib.validaInutilizacao(retorno);

        return this.converterParaRecord(retorno);
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
