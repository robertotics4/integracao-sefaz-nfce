package br.com.lrostech.nfce_teste.controller;

import br.com.lrostech.nfce_teste.domain.output.ConsultarSituacaoOutput;
import br.com.lrostech.nfce_teste.domain.output.ConsultarStatusServicoOutput;
import br.com.lrostech.nfce_teste.domain.output.EnviarXMLOutput;
import br.com.lrostech.nfce_teste.service.ConsultarNotaService;
import br.com.lrostech.nfce_teste.service.ConsultarStatusServicoService;
import br.com.lrostech.nfce_teste.service.EnviarXMLService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/nfce")
public class NFCEController {
    private final ConsultarNotaService consultarNotaService;
    private final ConsultarStatusServicoService consultarStatusServicoService;
    private final EnviarXMLService enviarXMLService;

    @GetMapping("/{chave}")
    public ConsultarSituacaoOutput consultarNota(@PathVariable String chave) {
        return consultarNotaService.executar(chave);
    }

    @GetMapping("/status")
    public ConsultarStatusServicoOutput consultarStatusServidor() {
        return consultarStatusServicoService.executar();
    }

    @PostMapping(consumes = "application/xml", produces = "application/json")
    public EnviarXMLOutput consultarStatusServidor(@RequestBody String conteudoXML) {
        return enviarXMLService.executar(conteudoXML);
    }
}
