package br.com.ibm.service;

import br.com.ibm.config.IntegracaoPropertiesLoader;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import lombok.AllArgsConstructor;

import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import java.util.function.Function;

@AllArgsConstructor
public class IntegracaoServiceIn {
    private IntegracaoPropertiesLoader integracaoPropertiesLoader;
    private JmsConnectionFactory jmsConnectionFactoryIn;
    private JMSContext contextIn;
    private Destination destination;

    public IntegracaoServiceIn(IntegracaoPropertiesLoader integracaoPropertiesLoader,
                               JmsConnectionFactory jmsConnectionFactoryIn) {
        this.integracaoPropertiesLoader = integracaoPropertiesLoader;
        this.jmsConnectionFactoryIn = jmsConnectionFactoryIn;
    }

    public void enviarMsg(String mensagem){
            aplicarContextoFila()
                .andThen(aplicarDestinatario())
                .andThen(aplicarEnvioMensagem(mensagem))
                .apply(contextIn);
    }

    public String buscarMsg(){
        return aplicarContextoFila()
                .andThen(aplicarDestinatario())
                .andThen(buscarMensagem())
                .apply(contextIn);
    }

    private Function<JMSContext, JMSContext> aplicarContextoFila() {
        return context -> jmsConnectionFactoryIn.createContext();
//        return context -> jmsConnectionFactoryIn.createContext()
    }

    private Function<JMSContext, JMSContext> aplicarDestinatario() {
        return context -> {
            destination = context.createQueue("queue:///" + integracaoPropertiesLoader.getQueueIn());
            return context;
        };
    }

    private Function<JMSContext, JMSProducer> aplicarEnvioMensagem(String mensagem) {
        return context -> context.createProducer().send(destination, context.createTextMessage(mensagem));
    }

    private Function<JMSContext, String> buscarMensagem() {
        return context -> context.createConsumer(destination).receiveBody(String.class, integracaoPropertiesLoader.getTimeOut());
    }
}