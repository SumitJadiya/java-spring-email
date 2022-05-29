package com.project.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(EmailApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
