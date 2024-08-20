package com.taskmanagement.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class TaskmanagementApplication {

	public static void main(String[] args) {

		System.out.println("testing");
		SpringApplication.run(TaskmanagementApplication.class, args);
	}

}
