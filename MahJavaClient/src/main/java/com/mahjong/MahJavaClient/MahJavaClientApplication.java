package com.mahjong.MahJavaClient;

import com.mahjong.MahJavaClient.client.MahJavaClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MahJavaClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MahJavaClientApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return new MahJavaClient();
	}
}
