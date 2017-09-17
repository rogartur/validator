package com.fx.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;

@SpringBootApplication
@EnableMetrics(proxyTargetClass = true)
public class ValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidatorApplication.class, args);
	}

}
