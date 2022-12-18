package com.potato.auth;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ReactiveAuthApplication {

	private static String[] args;
	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		ReactiveAuthApplication.args = args;
		ReactiveAuthApplication.context = SpringApplication.run(ReactiveAuthApplication.class, args);
	}

	public static void restart() {
		context.close();

		ReactiveAuthApplication.context = SpringApplication.run(ReactiveAuthApplication.class, args);
	}

}
