package br.com.lrostech.nfce_teste.service;

import br.com.lrostech.nfce_teste.domain.input.ConsultarSituacaoInput;
import br.com.lrostech.nfce_teste.domain.output.ConsultarSituacaoOutput;
import br.com.lrostech.nfce_teste.infra.sefaz.SefazUtilitario;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultarNotaService {
    private static final String CERTIFICATES_PATH = "src/main/resources/certificates";
    private static final String CERTIFICATE_NAME = "certificado.pfx";

    public ConsultarSituacaoOutput executar(String chave) {
        try {
            File arquivo = new File(CERTIFICATES_PATH + File.separator + CERTIFICATE_NAME);
            byte[] bytesCertificado = Files.readAllBytes(arquivo.toPath());
            ConsultarSituacaoInput input = new ConsultarSituacaoInput(
                    chave,
                    DocumentoEnum.NFCE,
                    bytesCertificado,
                    "ARSOLUTI",
                    EstadosEnum.MA,
                    AmbienteEnum.PRODUCAO
            );

            return SefazUtilitario.consultarSituacao(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
