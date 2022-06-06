package com.mscar.lease;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Lease application
 */
@SpringBootApplication
@Slf4j
public class LeaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeaseApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
