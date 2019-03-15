package br.com.ibm.config;

import br.com.ibm.service.IntegracaoServiceOutJms;
import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class IntegracaoServiceOutJmsModule {
    @Autowired
    private IntegracaoPropertiesLoader integracaoPropertiesLoader;

    @Bean
    public IntegracaoServiceOutJms integracaoServiceOutJms(@Qualifier("jmsTemplateOut") JmsTemplate jmsTemplateOut) {
        return new IntegracaoServiceOutJms(jmsTemplateOut);
    }

    @Bean("cachingConnectionFactoryOut")
    public CachingConnectionFactory cachingConnectionFactoryOut(@Qualifier("connectionFactoryOut") ConnectionFactory connectionFactoryOut) {
        return new CachingConnectionFactory(connectionFactoryOut);
    }

    @Bean("connectionFactoryOut")
    public ConnectionFactory connectionFactoryOut(){
        MQConnectionFactory connectionFactoryOut = new MQConnectionFactory();
        try {
            connectionFactoryOut.setHostName(integracaoPropertiesLoader.getHostOut());
            connectionFactoryOut.setPort(integracaoPropertiesLoader.getPortOut());
            connectionFactoryOut.setQueueManager(integracaoPropertiesLoader.getQueueManagerOut());
            connectionFactoryOut.setChannel(integracaoPropertiesLoader.getChannelOut());
            connectionFactoryOut.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            connectionFactoryOut.setClientReconnectTimeout(integracaoPropertiesLoader.getTimeOut());
            connectionFactoryOut.setClientReconnectOptions(WMQConstants.WMQ_CLIENT_RECONNECT);
            connectionFactoryOut.setStringProperty(WMQConstants.USERID, integracaoPropertiesLoader.getUserOut());
            connectionFactoryOut.setStringProperty(WMQConstants.PASSWORD, integracaoPropertiesLoader.getPasswordOut());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connectionFactoryOut;
    }

    @Bean("jmsTemplateOut")
    public JmsTemplate jmsTemplateOut(@Qualifier("cachingConnectionFactoryOut") CachingConnectionFactory cachingConnectionFactoryOut) {
        JmsTemplate jmsTemplateIn = new JmsTemplate();
        jmsTemplateIn.setConnectionFactory(cachingConnectionFactoryOut);
        jmsTemplateIn.setDefaultDestinationName(integracaoPropertiesLoader.getQueueOut());
        jmsTemplateIn.setReceiveTimeout(integracaoPropertiesLoader.getTimeOut());

        return jmsTemplateIn;
    }
}