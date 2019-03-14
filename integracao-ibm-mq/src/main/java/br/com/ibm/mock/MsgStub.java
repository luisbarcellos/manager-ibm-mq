package br.com.ibm.mock;

public class MsgStub {
    public static String getMsg(){
        return "<MENSAGEM>"
             + "    <descricao>Testando fila MQ</descricao>"
             + "</MENSAGEM>";
    }
}