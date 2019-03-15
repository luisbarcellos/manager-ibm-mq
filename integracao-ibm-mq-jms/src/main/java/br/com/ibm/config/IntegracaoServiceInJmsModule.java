package br.com.ibm.config;

import br.com.ibm.service.IntegracaoServiceInJms;
import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class IntegracaoServiceInJmsModule {
    @Autowired
    private IntegracaoPropertiesLoader integracaoPropertiesLoader;

    @Bean
    public IntegracaoServiceInJms integracaoServiceInJms(@Qualifier("jmsTemplateIn") JmsTemplate jmsTemplateIn) {
        return new IntegracaoServiceInJms(jmsTemplateIn);
    }

    @Bean("cachingConnectionFactoryIn")
    public CachingConnectionFactory cachingConnectionFactoryIn(@Qualifier("connectionFactoryIn") ConnectionFactory connectionFactoryIn) {
        return new CachingConnectionFactory(connectionFactoryIn);
    }

    @Bean("connectionFactoryIn")
    public ConnectionFactory connectionFactoryIn(){
        MQConnectionFactory connectionFactoryIn = new MQConnectionFactory();
        try {
            connectionFactoryIn.setHostName(integracaoPropertiesLoader.getHostIn());
            connectionFactoryIn.setPort(integracaoPropertiesLoader.getPortIn());
            connectionFactoryIn.setQueueManager(integracaoPropertiesLoader.getQueueManagerIn());
            connectionFactoryIn.setChannel(integracaoPropertiesLoader.getChannelIn());
            connectionFactoryIn.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            connectionFactoryIn.setClientReconnectTimeout(integracaoPropertiesLoader.getTimeOut());
            connectionFactoryIn.setClientReconnectOptions(WMQConstants.WMQ_CLIENT_RECONNECT);
            connectionFactoryIn.setStringProperty(WMQConstants.USERID, integracaoPropertiesLoader.getUserIn());
            connectionFactoryIn.setStringProperty(WMQConstants.PASSWORD, integracaoPropertiesLoader.getPasswordIn());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connectionFactoryIn;
    }

    @Bean("jmsTemplateIn")
    @Primary
    public JmsTemplate jmsTemplateIn(@Qualifier("cachingConnectionFactoryIn") CachingConnectionFactory cachingConnectionFactoryIn) {
        JmsTemplate jmsTemplateIn = new JmsTemplate();
        jmsTemplateIn.setConnectionFactory(cachingConnectionFactoryIn);
        jmsTemplateIn.setDefaultDestinationName(integracaoPropertiesLoader.getQueueIn());
        jmsTemplateIn.setReceiveTimeout(integracaoPropertiesLoader.getTimeOut());

        return jmsTemplateIn;
    }
}