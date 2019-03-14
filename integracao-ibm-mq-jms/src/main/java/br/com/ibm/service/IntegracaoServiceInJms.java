package br.com.ibm.service;

import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

@AllArgsConstructor
public class IntegracaoServiceInJms {
    private JmsTemplate jmsTemplateIn;

    public void enviarMensagem(String mensagem) {
        jmsTemplateIn.convertAndSend(mensagem);
    }

    public String buscarMensagem() {
        return jmsTemplateIn.receiveAndConvert().toString();
    }
}