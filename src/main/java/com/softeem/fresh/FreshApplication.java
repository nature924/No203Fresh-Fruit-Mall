package com.softeem.fresh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class FreshApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreshApplication.class, args);
    }

}
