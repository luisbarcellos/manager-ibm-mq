package br.com.ibm.config;

import br.com.ibm.service.IntegracaoServiceOut;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegracaoServiceOutModule {
    @Autowired
    private IntegracaoPropertiesLoader integracaoPropertiesLoader;

    @Bean
    public IntegracaoServiceOut integracaoServiceOut(IntegracaoPropertiesLoader integracaoPropertiesLoader,
                                                       @Qualifier("jmsConnectionFactoryOut")
                                                            JmsConnectionFactory jmsConnectionFactoryOut) {
        return new IntegracaoServiceOut(integracaoPropertiesLoader, jmsConnectionFactoryOut);
    }

    @Bean("jmsConnectionFactoryOut")
    public JmsConnectionFactory jmsConnectionFactoryOut() {
        try {
            JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
            JmsConnectionFactory cf = ff.createConnectionFactory();

            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, integracaoPropertiesLoader.getHostOut());
            cf.setIntProperty(WMQConstants.WMQ_PORT, integracaoPropertiesLoader.getPortOut());
            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, integracaoPropertiesLoader.getChannelOut());
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, integracaoPropertiesLoader.getQueueManagerOut());
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
            cf.setStringProperty(WMQConstants.USERID, integracaoPropertiesLoader.getUserOut());
            cf.setStringProperty(WMQConstants.PASSWORD, integracaoPropertiesLoader.getPasswordOut());

            return cf;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}