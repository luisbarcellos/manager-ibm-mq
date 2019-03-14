package br.com.ibm.config;

import br.com.ibm.facade.IntegracaoFacadeJms;
import br.com.ibm.service.IntegracaoServiceInJms;
import br.com.ibm.service.IntegracaoServiceOutJms;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegracaoFacadeJmsModule {
    @Bean
    public IntegracaoFacadeJms integracaoFacadeJms(IntegracaoPropertiesLoader integracaoPropertiesLoader,
                                                   IntegracaoServiceInJms integracaoServiceInJms,
                                                   IntegracaoServiceOutJms integracaoServiceOutJms) {
        return new IntegracaoFacadeJms(integracaoPropertiesLoader, integracaoServiceInJms, integracaoServiceOutJms);
    }
}
