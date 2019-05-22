package com.umpaytest;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@SpringBootApplication
public class UmpayTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmpayTestApplication.class, args);
    }

}
