package br.com.lrostech.nfce_teste.domain.contract;

import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.exception.CertificadoException;

public interface ICertificadoUtil {
    Certificado certificadoPfxBytes(byte[] bytes, String senha) throws CertificadoException;
}
