package br.com.lrostech.nfce_teste.service;

import br.com.lrostech.nfce_teste.domain.input.CancelarXMLInput;
import br.com.lrostech.nfce_teste.domain.output.CancelarXMLOutput;
import br.com.lrostech.nfce_teste.infra.sefaz.SefazUtilitario;
import br.com.lrostech.nfce_teste.support.util.FileUtil;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CancelarXMLService {
    public CancelarXMLOutput executar(
            String chave,
            String protocolo,
            String cnpj,
            String motivoCancelamento
    ) {
        try {
            byte[] bytesCertificado = FileUtil.lerBytesCertificado();
            CancelarXMLInput input = new CancelarXMLInput(
                    chave,
                    protocolo,
                    cnpj,
                    motivoCancelamento,
                    LocalDateTime.now(),
                    false,
                    DocumentoEnum.NFCE,
                    bytesCertificado,
                    "ARSOLUTI",
                    EstadosEnum.MA,
                    AmbienteEnum.HOMOLOGACAO
            );

            return SefazUtilitario.cancelarXML(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
