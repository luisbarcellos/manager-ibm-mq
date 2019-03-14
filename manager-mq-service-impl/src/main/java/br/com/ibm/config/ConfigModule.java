package br.com.ibm.config;

import br.com.ibm.facade.IntegracaoFacade;
import br.com.ibm.facade.IntegracaoFacadeJms;
import br.com.ibm.service.FilaIbmService;
import br.com.ibm.service.FilaIbmJmsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigModule {
    @Bean
    public FilaIbmService filaIbmService(IntegracaoFacade integracaoFacade) {
        return new FilaIbmService(integracaoFacade);
    }

    @Bean
    public FilaIbmJmsService filaIbmServiceJms(IntegracaoFacadeJms integracaoFacadeJms) {
        return new FilaIbmJmsService(integracaoFacadeJms);
    }
}