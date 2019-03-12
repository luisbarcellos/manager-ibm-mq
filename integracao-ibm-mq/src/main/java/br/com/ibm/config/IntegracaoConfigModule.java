package br.com.ibm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegracaoConfigModule {
    @Bean
    public IntegracaoPropertiesLoader integracaoPropertiesLoader() {
        return new IntegracaoPropertiesLoader();
    }
}
