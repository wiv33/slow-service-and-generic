package com.example.webclient.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SlowServiceAndGenericApplication {

    public static void main(String[] args) {
        SpringApplication.run(SlowServiceAndGenericApplication.class, args);
    }

}
