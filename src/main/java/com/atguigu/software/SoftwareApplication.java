package com.atguigu.software;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.atguigu.software.mapper")
public class SoftwareApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftwareApplication.class, args);
    }

}
