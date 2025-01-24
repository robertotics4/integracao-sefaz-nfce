package br.com.lrostech.nfce_teste.support.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {
    private static final String CERTIFICATES_PATH = "src/main/resources/certificates";
    private static final String CERTIFICATE_NAME = "certificado.pfx";

    private FileUtil() {}

    public static byte[] lerBytesCertificado() throws IOException {
        File arquivo = new File(CERTIFICATES_PATH + File.separator + CERTIFICATE_NAME);
        return Files.readAllBytes(arquivo.toPath());
    }
}
