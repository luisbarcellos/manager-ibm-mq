package br.com.ibm.service;

import br.com.ibm.config.IntegracaoPropertiesLoader;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import java.util.function.Function;

@AllArgsConstructor
@Slf4j
public class IntegracaoServiceOut {
    private IntegracaoPropertiesLoader integracaoPropertiesLoader;
    private JmsConnectionFactory jmsConnectionFactoryOut;
    private JMSContext contextOut;
    private Destination destination;

    public IntegracaoServiceOut(IntegracaoPropertiesLoader integracaoPropertiesLoader,
                                JmsConnectionFactory jmsConnectionFactoryOut) {
        this.integracaoPropertiesLoader = integracaoPropertiesLoader;
        this.jmsConnectionFactoryOut = jmsConnectionFactoryOut;
    }

    public void enviarMsg(String mensagem){
        aplicarContextoFila()
                .andThen(aplicarDestinatario())
                .andThen(aplicarEnvioMensagem(mensagem))
                .apply(contextOut);
    }

    public String buscarMsg(){
        return aplicarContextoFila()
                .andThen(aplicarDestinatario())
                .andThen(buscarMensagem())
                .apply(contextOut);
    }

    private Function<JMSContext, JMSContext> aplicarContextoFila() {
        return context -> jmsConnectionFactoryOut.createContext();
    }

    private Function<JMSContext, JMSContext> aplicarDestinatario() {
        return context -> {
            destination = context.createQueue("queue:///" + integracaoPropertiesLoader.getQueueIn());
            return context;
        };
    }

    private Function<JMSContext, JMSProducer> aplicarEnvioMensagem(String mensagem) {
        return context -> {
            System.out.println(mensagem);
            return context.createProducer().send(destination, context.createTextMessage(mensagem));
        };
    }

    private Function<JMSContext, String> buscarMensagem() {
        return context -> context.createConsumer(destination).receiveBody(String.class, integracaoPropertiesLoader.getTimeOut());
    }
}