package com.iot.monitoring;

import org.jtransfo.JTransfo;
import org.jtransfo.spring.JTransfoSpringFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.iot.monitoring.dao")
@EntityScan("com.iot.monitoring.model")
@ComponentScan("com.iot.monitoring")
public class MonitoringToolsBoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringToolsBoApplication.class, args);
	}
	
	@Bean
	@Scope("singleton")
	public JTransfoSpringFactory jTransfoFactory() {
							
		JTransfoSpringFactory c = new JTransfoSpringFactory();
		
		return c;
	}
	
	@Bean
	@Scope("singleton")
	public JTransfo jTransfo() {
		
		return this.jTransfoFactory().get();
	} 
}
