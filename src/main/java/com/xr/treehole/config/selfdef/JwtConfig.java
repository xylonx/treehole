package com.xr.treehole.config.selfdef;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:config.properties")
@ConfigurationProperties(prefix = "config.jwt")
@Data
public class JwtConfig {

    private int exp;

    private String secret;

    private int reissue;

    public long getExpTime(){
        // return the minute of the expDuration
        return exp * 60 * 1000;
    }

    public long getReissueDuration(){
        return reissue * 60 * 1000;
    }
}
