package br.com.lrostech.nfce_teste.useCase;

import br.com.lrostech.nfce_teste.domain.input.EnviarXMLInput;
import br.com.lrostech.nfce_teste.domain.output.EnviarXMLOutput;
import br.com.lrostech.nfce_teste.support.constants.ConfigConstants;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.Nfe;
import br.com.swconsultoria.nfe.dom.ConfiguracoesNfe;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TEnviNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TRetEnviNFe;
import br.com.swconsultoria.nfe.util.XmlNfeUtil;

import javax.xml.bind.JAXBException;

public class EnviarXMLUseCase {
    public EnviarXMLOutput executar(EnviarXMLInput input) throws CertificadoException, JAXBException, NfeException {
        ConfiguracoesNfe config = this.criarConfiguracoes(
                input.bytesCertificado(),
                input.senhaCertificado(),
                input.estado(),
                input.ambiente()
        );

        TEnviNFe enviNFe = XmlNfeUtil.xmlToObject(input.conteudoXML(), TEnviNFe.class);

        enviNFe = Nfe.montaNfe(config, enviNFe, input.validaXML());

        TRetEnviNFe retornoSefaz = Nfe.enviarNfe(config, enviNFe, input.tipoDocumento());

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
