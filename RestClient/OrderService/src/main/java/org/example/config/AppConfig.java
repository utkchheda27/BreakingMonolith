package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    public RestClient restClientInstance() {
        //RestClient is an interface, need to call it's static method create() either non parameterized
        //or by passing a RestTemplate object
        return RestClient.create();
    }
}
