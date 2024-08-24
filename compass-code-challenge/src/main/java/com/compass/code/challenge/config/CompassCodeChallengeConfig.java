package com.compass.code.challenge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "compass-code-config")
public class CompassCodeChallengeConfig {

    private Integer highAmountOfMatches;
    private Integer mediumAmountOfMatches;
}
