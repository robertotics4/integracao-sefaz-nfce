package br.com.lrostech.nfce_teste.service;

import br.com.lrostech.nfce_teste.domain.contract.ISefazClient;
import br.com.lrostech.nfce_teste.domain.input.ConsultarStatusServicoInput;
import br.com.lrostech.nfce_teste.domain.output.ConsultarStatusServicoOutput;
import br.com.lrostech.nfce_teste.support.util.FileUtil;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarStatusServicoService {
    private final ISefazClient sefazClient;

    public ConsultarStatusServicoOutput executar() {
        try {
            byte[] bytesCertificado = FileUtil.lerBytesCertificado();
            ConsultarStatusServicoInput input = new ConsultarStatusServicoInput(
                    DocumentoEnum.NFCE,
                    bytesCertificado,
                    "ARSOLUTI",
                    EstadosEnum.MA,
                    AmbienteEnum.PRODUCAO
            );

            return this.sefazClient.consultarStatusServico(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
