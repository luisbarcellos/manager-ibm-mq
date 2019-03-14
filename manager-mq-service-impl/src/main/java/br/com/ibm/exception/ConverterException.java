package br.com.ibm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ConverterException extends RuntimeException {
    public ConverterException(String message){
        super(message);
    }
}