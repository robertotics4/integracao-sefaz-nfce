package br.com.lrostech.nfce_teste.controller;

import br.com.lrostech.nfce_teste.domain.dto.CancelarXMLRequestDTO;
import br.com.lrostech.nfce_teste.domain.dto.InutilizarXMLRequestDTO;
import br.com.lrostech.nfce_teste.domain.output.*;
import br.com.lrostech.nfce_teste.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/nfce")
public class NFCEController {
    private final ConsultarNotaService consultarNotaService;
    private final ConsultarStatusServicoService consultarStatusServicoService;
    private final EnviarXMLService enviarXMLService;
    private final InutilizarXMLService inutilizarXMLService;
    private final CancelarXMLService cancelarXMLService;

    @GetMapping("/{chave}")
    public ConsultarSituacaoOutput consultarNota(@PathVariable String chave) {
        return consultarNotaService.executar(chave);
    }

    @GetMapping("/status")
    public ConsultarStatusServicoOutput consultarStatusServidor() {
        return consultarStatusServicoService.executar();
    }

    @PostMapping(name = "/autorizar", consumes = "application/xml", produces = "application/json")
    public EnviarXMLOutput enviarXML(@RequestBody String conteudoXML) {
        return enviarXMLService.executar(conteudoXML);
    }

    @PostMapping(name = "/inutilizar", consumes = "application/json", produces = "application/json")
    public InutilizarXMLOutput inutilizarXML(@RequestBody InutilizarXMLRequestDTO dto) {
        return inutilizarXMLService.executar(
                dto.getCnpj(),
                dto.getSerie(),
                dto.getNumeroInicial(),
                dto.getNumeroFinal(),
                dto.getJustificativa()
        );
    }

    @PostMapping(name = "/cancelar", consumes = "application/json", produces = "application/json")
    public CancelarXMLOutput cancelarXML(@RequestBody CancelarXMLRequestDTO dto) {
        return cancelarXMLService.executar(
                dto.getChave(),
                dto.getProtocolo(),
                dto.getCnpj(),
                dto.getMotivoCancelamento()
        );
    }
}