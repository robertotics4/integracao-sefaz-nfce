package br.com.lrostech.nfce_teste.infra;

import br.com.lrostech.nfce_teste.domain.contract.ICertificadoUtil;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CertificadoUtil implements ICertificadoUtil {
    @Override
    public Certificado certificadoPfxBytes(byte[] bytes, String senha) throws CertificadoException {
        return CertificadoService.certificadoPfxBytes(bytes, senha);
    }
}
