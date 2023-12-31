package com.viceversa.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class ViceVersaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViceVersaApplication.class, args);
    }

}
