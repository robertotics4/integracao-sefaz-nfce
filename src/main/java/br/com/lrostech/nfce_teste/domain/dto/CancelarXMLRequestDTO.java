package br.com.lrostech.nfce_teste.domain.dto;

import lombok.Data;

@Data
public class CancelarXMLRequestDTO {
    private String chave;
    private String protocolo;
    private String cnpj;
    private String motivoCancelamento;
}
