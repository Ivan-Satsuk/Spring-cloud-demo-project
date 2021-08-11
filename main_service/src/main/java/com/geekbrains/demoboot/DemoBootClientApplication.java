package com.geekbrains.demoboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DemoBootClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoBootClientApplication.class, args);
	}
}
