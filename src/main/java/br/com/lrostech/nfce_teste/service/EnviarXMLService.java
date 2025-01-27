package br.com.lrostech.nfce_teste.service;

import br.com.lrostech.nfce_teste.domain.input.EnviarXMLInput;
import br.com.lrostech.nfce_teste.domain.output.EnviarXMLOutput;
import br.com.lrostech.nfce_teste.infra.sefaz.SefazUtilitario;
import br.com.lrostech.nfce_teste.support.util.FileUtil;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnviarXMLService {
    public EnviarXMLOutput executar(String conteudoXML) {
        try {
            byte[] bytesCertificado = FileUtil.lerBytesCertificado();
            EnviarXMLInput input = new EnviarXMLInput(
                    conteudoXML,
                    false,
                    DocumentoEnum.NFCE,
                    bytesCertificado,
                    "ARSOLUTI",
                    EstadosEnum.MA,
                    AmbienteEnum.HOMOLOGACAO
            );

            return SefazUtilitario.enviarXML(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
