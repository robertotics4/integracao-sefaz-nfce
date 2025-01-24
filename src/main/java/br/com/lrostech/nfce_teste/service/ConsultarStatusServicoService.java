package br.com.lrostech.nfce_teste.service;

import br.com.lrostech.nfce_teste.domain.input.ConsultarStatusServicoInput;
import br.com.lrostech.nfce_teste.infra.sefaz.SefazUtilitario;
import br.com.lrostech.nfce_teste.support.util.FileUtil;
import br.com.swconsultoria.nfe.dom.enuns.AmbienteEnum;
import br.com.swconsultoria.nfe.dom.enuns.DocumentoEnum;
import br.com.swconsultoria.nfe.dom.enuns.EstadosEnum;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultarStatusServicoService {
    public TRetConsStatServ executar() {
        try {
            byte[] bytesCertificado = FileUtil.lerBytesCertificado();
            ConsultarStatusServicoInput input = new ConsultarStatusServicoInput(
                    DocumentoEnum.NFCE,
                    bytesCertificado,
                    "ARSOLUTI",
                    EstadosEnum.MA,
                    AmbienteEnum.PRODUCAO
            );

            return SefazUtilitario.consultarStatusServico(input);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
