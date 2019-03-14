package br.com.ibm.service;

import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

@AllArgsConstructor
public class IntegracaoServiceOutJms {
    private JmsTemplate jmsTemplateOut;

    public void enviarMensagem(String mensagem) {
        jmsTemplateOut.convertAndSend(mensagem);
    }

    public String buscarMensagem() {
        return jmsTemplateOut.receiveAndConvert().toString();
    }
}