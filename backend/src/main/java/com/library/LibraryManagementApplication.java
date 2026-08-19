package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Library Management System.
 *
 * @SpringBootApplication combines three annotations:
 * - @Configuration: Marks this as a configuration class
 * - @EnableAutoConfiguration: Tells Spring Boot to auto-configure
 * - @ComponentScan: Scans this package and sub-packages for components
 */
@SpringBootApplication
public class LibraryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }
}
