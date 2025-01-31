package br.com.lrostech.nfce_teste.domain.useCase;

import br.com.lrostech.nfce_teste.domain.contract.ICertificadoUtil;
import br.com.lrostech.nfce_teste.domain.contract.INfeLib;
import br.com.lrostech.nfce_teste.domain.input.EnviarXMLInput;
import br.com.lrostech.nfce_teste.domain.output.EnviarXMLOutput;
import br.com.lrostech.nfce_teste.support.constants.ConfigConstants;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TEnviNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TRetEnviNFe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

@Component
@RequiredArgsConstructor
public class EnviarXMLUseCase {
    private final ICertificadoUtil certificadoUtil;
    private final INfeLib nfeLib;

    public EnviarXMLOutput executar(EnviarXMLInput input) throws CertificadoException, JAXBException, NfeException {
        Certificado certificado = this.certificadoUtil.certificadoPfxBytes(input.bytesCertificado(), input.senhaCertificado());
        ConfiguracoesNfe config = this.nfeLib.criarConfiguracoes(
                input.estado(),
                input.ambiente(),
                certificado,
                ConfigConstants.SCHEMAS_PATH
        );

        TEnviNFe enviNFe = this.nfeLib.xmlToObject(input.conteudoXML(), TEnviNFe.class);

        enviNFe = this.nfeLib.montaNfe(config, enviNFe, input.validaXML());

        TRetEnviNFe retornoSefaz = this.nfeLib.enviarNfe(config, enviNFe, input.tipoDocumento());

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

    private EnviarXMLOutput converterParaRecord(TRetEnviNFe tRetEnviNFe) {
        return new EnviarXMLOutput(
                tRetEnviNFe.getTpAmb(),
                tRetEnviNFe.getVerAplic(),
                tRetEnviNFe.getCStat(),
                tRetEnviNFe.getXMotivo(),
                tRetEnviNFe.getCUF(),
                tRetEnviNFe.getDhRecbto(),
                tRetEnviNFe.getInfRec() != null ? tRetEnviNFe.getInfRec() : null,
                tRetEnviNFe.getProtNFe(),
                tRetEnviNFe.getVersao()
        );
    }
}
