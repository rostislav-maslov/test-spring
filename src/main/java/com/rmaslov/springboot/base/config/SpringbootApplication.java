package com.rmaslov.springboot.base.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication(scanBasePackages = "com.rmaslov.springboot", exclude = {EmbeddedMongoAutoConfiguration.class})
@EnableMongoRepositories(basePackages = "com.rmaslov.springboot")
public class SpringbootApplication {



	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
