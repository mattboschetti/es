package com.mattboschetti.sandbox.es;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;

@Configuration
@EnableAsync
@EnableScheduling
public class AppConfiguration {

    @Bean
    public Clock utcClock() {
        return Clock.systemUTC();
    }

}
