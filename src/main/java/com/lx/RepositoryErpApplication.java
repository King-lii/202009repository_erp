package com.lx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.lx.*.mapper"})
public class RepositoryErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepositoryErpApplication.class, args);
    }

}
