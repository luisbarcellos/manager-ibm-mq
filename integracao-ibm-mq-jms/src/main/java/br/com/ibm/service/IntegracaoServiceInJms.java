package br.com.ibm.service;

import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;

import java.util.Optional;

@AllArgsConstructor
public class IntegracaoServiceInJms {
    private JmsTemplate jmsTemplateIn;

    public void enviarMensagem(String mensagem) {
        jmsTemplateIn.convertAndSend(mensagem);
    }

    public String buscarMensagem() {
        return Optional.ofNullable(jmsTemplateIn.receiveAndConvert())
                .map(message -> message.toString())
                .orElse(null);
    }
}