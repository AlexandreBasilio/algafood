package com.algaworks.algafood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    /*
      LocalValidatorFactoryBean : configura/integra o bean validation com o Spring
      MessageSource : responsavel por ler o message.properties et fazer a resolucao de messagens

      metodo que produz um LocalValidatorFactoryBean
      e estamos customizando uma instacia do LocalValidatorFactoryBean indicando que o ValidationMessageSource sera o MessagSource
      se nao especificamos .. ele usara o ValidationMessages.properties
     */

    @Bean
    public LocalValidatorFactoryBean validator (MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);

        return bean;
    }
}
