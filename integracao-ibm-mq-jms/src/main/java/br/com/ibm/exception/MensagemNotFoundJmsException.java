package br.com.ibm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MensagemNotFoundJmsException extends RuntimeException {
    public MensagemNotFoundJmsException(){
        super("Não foi encontrado mensagem(ns) na fila.");
    }
}