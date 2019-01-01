package com.nokia.ims.fmprocess;

import com.nokia.ims.fmprocess.fm.FMAgentEngine.FMAgent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class FmprocessApplication {

    @Bean
    public FMAgent helloRunner() {
        return new FMAgent();
    }

    public static void main(String[] args) {
        SpringApplication.run(FmprocessApplication.class, args);
    }

}

