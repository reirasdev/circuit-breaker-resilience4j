package com.reiras.localidademicroservice.service.parser;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParserConfig {

	@Bean("parserFactory")
	public ServiceLocatorFactoryBean serviceLocatorFactoryBean() {
		ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
		serviceLocatorFactoryBean.setServiceLocatorInterface(ParserFactory.class);
		return serviceLocatorFactoryBean;
	}
}
