package com.bob.ets_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.bob.ets_demo.model.*;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class EtsDemoApplication {

	Logger logger = LoggerFactory.getLogger(EtsDemoApplication .class);

	public static void main(String[] args) {
		SpringApplication.run(EtsDemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(AccountRepository repo) {
		return args -> {
			for (int i = 1; i <= 5; ++i) {
				AccountModel rec = new AccountModel("user_" + i, BigDecimal.valueOf(10000*i));
				repo.save(rec);
			}
			logger.info("***** repo count " + repo.count());
			for (AccountModel rec : repo.findAll()) {
				logger.info("*** rec " + rec);
			}
		};
	}

}
