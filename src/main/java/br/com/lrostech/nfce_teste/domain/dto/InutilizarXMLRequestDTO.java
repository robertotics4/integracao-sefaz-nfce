package br.com.lrostech.nfce_teste.domain.dto;

import lombok.Data;

@Data
public class InutilizarXMLRequestDTO {
    private String cnpj;
    private Integer serie;
    private Integer numeroInicial;
    private Integer numeroFinal;
    private String justificativa;
}
