package com.manju.fmprocess.jpa.alarmsjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.manju.fmprocess.jpa.alarmsjpa")
public class AlarmsJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlarmsJpaApplication.class, args);
    }

}

