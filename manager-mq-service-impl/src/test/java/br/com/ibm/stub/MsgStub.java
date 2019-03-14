package br.com.ibm.stub;

import br.com.ibm.model.Mensagem;

public class MsgStub {
    public static String getMsgXml(){
        return "<MENSAGEM>"
             + "    <descricao>Testando fila MQ</descricao>"
             + "</MENSAGEM>";
    }

    public static String getMsgXmlComErro(){
        return "<MENSAGEM>"
                + "    <descricao>Testando fila MQ</descricao>"
                + "</ME>";
    }

    public static Mensagem getMsg(){
        return Mensagem.builder()
                .mensagem("Testando fila MQ")
                .build();
    }
}