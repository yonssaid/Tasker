package com.Tasker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskerApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(TaskerApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
