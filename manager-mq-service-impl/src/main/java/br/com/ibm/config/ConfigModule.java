package br.com.ibm.config;

import br.com.ibm.facade.IntegracaoFacade;
import br.com.ibm.service.FilaIbmService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigModule {
    @Bean
    public FilaIbmService virtualCobService(IntegracaoFacade integracaoFacade) {
        return new FilaIbmService(integracaoFacade);
    }
}