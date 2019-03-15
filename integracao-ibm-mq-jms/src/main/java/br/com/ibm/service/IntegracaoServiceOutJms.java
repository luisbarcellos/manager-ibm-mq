package br.com.ibm.service;

import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import java.util.Optional;

@AllArgsConstructor
public class IntegracaoServiceOutJms {
    private JmsTemplate jmsTemplateOut;

    public void enviarMensagem(String mensagem) {
        jmsTemplateOut.convertAndSend(mensagem);
    }

    public String buscarMensagem() {
        return Optional.ofNullable(jmsTemplateOut.receiveAndConvert())
                .map(message -> message.toString())
                .orElse(null);
    }
}