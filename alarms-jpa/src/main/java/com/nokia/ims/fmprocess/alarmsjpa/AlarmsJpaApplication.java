package com.nokia.ims.fmprocess.alarmsjpa;

import com.nokia.ims.fmprocess.alarmsjpa.init.UserDAOCommandLineRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class AlarmsJpaApplication {
    private static final Logger logger = LoggerFactory.getLogger(AlarmsJpaApplication.class);

    public static void main(String[] args) {
        try (ConfigurableApplicationContext context =
                     SpringApplication.run(AlarmsJpaApplication.class, args)) {
            logger.info(" My context: " + context);
        }

    }

}

