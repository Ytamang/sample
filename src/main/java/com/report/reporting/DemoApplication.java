package com.report.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("``````````````````````     "+System.currentTimeMillis());
		log.info("Application is running, go to http://localhost:8080/all to get the list of users.");
		log.info("Application is running, go to http://localhost:8080/store/1 to get the cam status of the store");
	}
}