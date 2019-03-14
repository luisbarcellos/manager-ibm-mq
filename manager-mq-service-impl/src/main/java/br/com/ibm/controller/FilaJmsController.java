package br.com.ibm.controller;

import br.com.ibm.model.Mensagem;
import br.com.ibm.service.FilaIbmJmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ibm-fila-jms/")
public class FilaJmsController {
    @Autowired
    private FilaIbmJmsService filaIbmJmsService;

    @PostMapping("simular/filain")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void simularCip(){
        filaIbmJmsService.simularFilaIn();
    }

    @PostMapping("transferir/filain-to-filaout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transferirMensagensFila(){
        filaIbmJmsService.transferirMensagensFila();
    }

    @GetMapping("buscar/filain")
    @ResponseBody
    public List<Mensagem> buscarMensagensFilaInJms(@RequestParam(name = "quantidade", required = true, defaultValue = "10") Integer quantidade){
        return filaIbmJmsService.buscarMensagensFilaInJms(quantidade);
    }

    @GetMapping("buscar/filaout")
    @ResponseBody
    public List<Mensagem> buscarMensagensFilaOut(@RequestParam(name = "quantidade", required = true, defaultValue = "10") Integer quantidade){
        return filaIbmJmsService.buscarMensagensFilaOut(quantidade);
    }
}