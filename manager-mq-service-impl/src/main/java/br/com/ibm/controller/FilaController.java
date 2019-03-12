package br.com.ibm.controller;

import br.com.ibm.model.Mensagem;
import br.com.ibm.service.FilaIbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ibm-fila/")
public class FilaController {
    @Autowired
    private FilaIbmService filaIbmService;

    @PostMapping("simular/filain")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void simularCip(){
        filaIbmService.simularFilaEntradaCip();
    }

    @PostMapping("transferir/filain-to-filaout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transferirMensagensFila(){
        filaIbmService.transferirMensagensFila();
    }

    @GetMapping("buscar/filaout")
    @ResponseBody
    public List<Mensagem> buscarMensagensFilaOut(@RequestParam(name = "quantidade", required = true, defaultValue = "10") Integer quantidade){
        return filaIbmService.buscarMensagensFilaOut(quantidade);
    }

    @GetMapping("buscar/filain")
    @ResponseBody
    public List<Mensagem> buscarMensagensFilaIn(@RequestParam(name = "quantidade", required = true, defaultValue = "10") Integer quantidade){
        return filaIbmService.buscarMensagensFilaIn(quantidade);
    }
}