package br.com.lrostech.nfce_teste.service;

import br.com.lrostech.nfce_teste.domain.input.CancelarXMLSubstituicaoInput;
import br.com.lrostech.nfce_teste.domain.output.CancelarXMLSubstituicaoOutput;
import br.com.lrostech.nfce_teste.infra.sefaz.SefazUtilitario;
import br.com.lrostech.nfce_teste.support.util.FileUtil;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CancelarXMLSubstituicaoService {
    public CancelarXMLSubstituicaoOutput executar(
            String chave,
            String chaveSubstituta,
            String protocolo,
            String cnpj,
            String motivoCancelamento
    ) {
        try {
            byte[] bytesCertificado = FileUtil.lerBytesCertificado();
            CancelarXMLSubstituicaoInput input = new CancelarXMLSubstituicaoInput(
                    chave,
                    chaveSubstituta,
                    protocolo,
                    cnpj,
                    motivoCancelamento,
                    LocalDateTime.now(),
                    false,
                    bytesCertificado,
                    "ARSOLUTI",
                    EstadosEnum.MA,
                    AmbienteEnum.HOMOLOGACAO
            );

            return SefazUtilitario.cancelarXMLSubstituicao(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
