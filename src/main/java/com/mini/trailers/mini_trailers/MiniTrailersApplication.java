package com.mini.trailers.mini_trailers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mini.trailers.mini_trailers.Repositorio") 
public class MiniTrailersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniTrailersApplication.class, args);
	}

	@Bean
	public SpringDataDialect springDataDialect(){
		return new SpringDataDialect();
	}

}
