package br.com.ibm.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MensagemException extends RuntimeException {
    public MensagemException(String message){
        super(message);
    }
}