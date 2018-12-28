package com.xcloud.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import be.ordina.msdashboard.EnableMicroservicesDashboardServer;

@SpringBootApplication
@EnableEurekaClient
@EnableMicroservicesDashboardServer
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
