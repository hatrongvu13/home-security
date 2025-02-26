package com.xxx.homesecurity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "home-security")
public class ApplicationConfig {
    String pathOutput;
    String pathInput;
}
