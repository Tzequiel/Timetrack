package com.timetrack.metrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MetricsApplication {
	public static void main(String[] args) {
		SpringApplication.run(MetricsApplication.class, args);
	}
}