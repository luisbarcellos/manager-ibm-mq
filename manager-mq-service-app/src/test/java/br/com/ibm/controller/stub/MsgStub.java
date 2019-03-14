package br.com.ibm.controller.stub;

import br.com.ibm.model.Mensagem;

import java.util.Arrays;
import java.util.List;

public class MsgStub {
    public static List<Mensagem> getMsgList(){
        return Arrays.asList(getMsg());
    }

    private static Mensagem getMsg(){
        return Mensagem.builder()
                .mensagem("Testando fila MQ")
                .build();
    }
}