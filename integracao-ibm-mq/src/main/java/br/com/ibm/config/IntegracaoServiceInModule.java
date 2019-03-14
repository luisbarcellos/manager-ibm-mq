package br.com.ibm.config;

import br.com.ibm.service.IntegracaoServiceIn;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class IntegracaoServiceInModule {
    @Autowired
    private IntegracaoPropertiesLoader integracaoPropertiesLoader;

    @Bean
    public IntegracaoServiceIn integracaoServiceIn(IntegracaoPropertiesLoader integracaoPropertiesLoader,
                                                   @Qualifier("jmsConnectionFactoryIn") JmsConnectionFactory jmsConnectionFactoryIn) {
        return new IntegracaoServiceIn(integracaoPropertiesLoader, jmsConnectionFactoryIn);
    }

    @Bean("jmsConnectionFactoryIn")
    @Primary
    public JmsConnectionFactory jmsConnectionFactoryIn() {
        try {
            JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
            JmsConnectionFactory cf = ff.createConnectionFactory();

            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, integracaoPropertiesLoader.getHostIn());
            cf.setIntProperty(WMQConstants.WMQ_PORT, integracaoPropertiesLoader.getPortIn());
            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, integracaoPropertiesLoader.getChannelIn());
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, integracaoPropertiesLoader.getQueueManagerIn());
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
            cf.setStringProperty(WMQConstants.USERID, integracaoPropertiesLoader.getUserIn());
            cf.setStringProperty(WMQConstants.PASSWORD, integracaoPropertiesLoader.getPasswordIn());

            return cf;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}