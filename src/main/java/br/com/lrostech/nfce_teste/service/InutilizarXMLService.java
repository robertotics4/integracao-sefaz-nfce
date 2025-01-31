package br.com.lrostech.nfce_teste.service;

import br.com.lrostech.nfce_teste.domain.contract.ISefazClient;
import br.com.lrostech.nfce_teste.domain.input.InutilizarXMLInput;
import br.com.lrostech.nfce_teste.domain.output.InutilizarXMLOutput;
import br.com.lrostech.nfce_teste.domain.provider.SefazClient;
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
public class InutilizarXMLService {
    private final ISefazClient sefazClient;

    public InutilizarXMLOutput executar(
            String cnpj,
            Integer serie,
            Integer numeroInicial,
            Integer numeroFinal,
            String justificativa
    ) {
        try {
            byte[] bytesCertificado = FileUtil.lerBytesCertificado();
            InutilizarXMLInput input = new InutilizarXMLInput(
                    cnpj,
                    serie,
                    numeroInicial,
                    numeroFinal,
                    justificativa,
                    LocalDateTime.now(),
                    false,
                    DocumentoEnum.NFCE,
                    bytesCertificado,
                    "ARSOLUTI",
                    EstadosEnum.MA,
                    AmbienteEnum.HOMOLOGACAO
            );

            return this.sefazClient.inutilizarXML(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
