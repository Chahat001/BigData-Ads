package com.client;

import com.client.services.LogUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClientApplication {


	public static void main(String[] args) {

		ApplicationContext applicationContext = new SpringApplicationBuilder(ClientApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		LogUploader logUploader = applicationContext.getBean(LogUploader.class);
		logUploader.uploadLogs();
	}



}
