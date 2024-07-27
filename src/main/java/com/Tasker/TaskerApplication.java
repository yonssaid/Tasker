package com.Tasker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Tasker application.
 * <p>
 * This class is the entry point for the Spring Boot application.
 * </p>
 *
 * @author Yons Said
 */
@SpringBootApplication
public class TaskerApplication {

	/**
	 * The main method to run the Spring Boot application.
	 *
	 * @param args command-line arguments.
	 */
	public static void main(String[] args) {
		try {
			SpringApplication.run(TaskerApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
