package br.com.ibm.config;

import br.com.ibm.facade.IntegracaoFacade;
import br.com.ibm.service.IntegracaoServiceIn;
import br.com.ibm.service.IntegracaoServiceOut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegracaoFacadeModule {
    @Bean
    public IntegracaoFacade integracaoFacade(IntegracaoServiceIn integracaoServiceIn,
                                               IntegracaoServiceOut integracaoServiceOut,
                                               IntegracaoPropertiesLoader integracaoPropertiesLoader) {
        return new IntegracaoFacade(integracaoServiceIn, integracaoServiceOut, integracaoPropertiesLoader);
    }
}
