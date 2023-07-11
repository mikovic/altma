package com.mikovic.altma;

import com.mikovic.altma.config.AppProperties;
import com.mikovic.altma.controllers.ControllerExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class AltmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AltmaApplication.class, args);
	}

}
