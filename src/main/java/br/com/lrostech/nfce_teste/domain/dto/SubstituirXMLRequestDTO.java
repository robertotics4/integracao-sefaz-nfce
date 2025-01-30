package br.com.lrostech.nfce_teste.domain.dto;

import lombok.Data;

@Data
public class SubstituirXMLRequestDTO {
    private String chave;
    private String chaveSubstituta;
    private String protocolo;
    private String cnpj;
    private String motivoCancelamento;
}
