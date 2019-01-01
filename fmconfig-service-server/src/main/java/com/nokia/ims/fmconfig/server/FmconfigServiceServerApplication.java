package com.nokia.ims.fmconfig.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class FmconfigServiceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmconfigServiceServerApplication.class, args);
    }

}

