package com.fullstack.taskboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TaskboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskboardApplication.class, args);
	}

}
