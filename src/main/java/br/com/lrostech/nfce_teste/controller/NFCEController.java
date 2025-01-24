package br.com.lrostech.nfce_teste.controller;

import br.com.lrostech.nfce_teste.domain.output.ConsultarSituacaoOutput;
import br.com.lrostech.nfce_teste.service.ConsultarNotaService;
import br.com.lrostech.nfce_teste.service.ConsultarStatusServicoService;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;
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
    private final ConsultarStatusServicoService consultarStatusServicoService;

    @GetMapping("/{chave}")
    public ConsultarSituacaoOutput consultarNota(@PathVariable String chave) {
        return consultarNotaService.executar(chave);
    }

    @GetMapping("/status")
    public TRetConsStatServ consultarStatusServidor() {
        return consultarStatusServicoService.executar();
    }
}
