package br.com.lrostech.nfce_teste.controller;

import br.com.lrostech.nfce_teste.domain.output.ConsultarSituacaoOutput;
import br.com.lrostech.nfce_teste.service.ConsultarNotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/nfce")
public class NFCEController {
    private final ConsultarNotaService consultarNotaService;

    @GetMapping("/{chave}")
    public ConsultarSituacaoOutput consultarNota(@PathVariable String chave) {
        return consultarNotaService.executar(chave);
    }
}
