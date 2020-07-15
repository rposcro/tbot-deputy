package com.tbot.deputy;

import java.util.Properties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DeputyApp implements CommandLineRunner {
    
    public static final String APPLICATION_NAME = "tbot-deputy";
    
    @Override
    public void run(String... arg0) throws Exception {
    }

    
    public static void main(String[] args) {
        new SpringApplicationBuilder(DeputyApp.class)
            .properties(getProperties())
            .listeners(new DeputyContextStartedListener())
            .run(args);
    }
    
    static Properties getProperties() {
        Properties props = new Properties();
        props.put("spring.config.name", APPLICATION_NAME);
        //props.put("spring.config.location", CONFIGURATION_LOCATION);
        return props;
    }
}
