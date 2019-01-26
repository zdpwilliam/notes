package com.william;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by william on 17-3-24.
 */
@Slf4j
@SpringBootApplication
public class MainRunner {

    public static void main(String[] args) {
        SpringApplication.run(MainRunner.class, args);
    }
}
