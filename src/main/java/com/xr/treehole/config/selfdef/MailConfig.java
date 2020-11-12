package com.xr.treehole.config.selfdef;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
@ConfigurationProperties(prefix = "config.mail")
@Data
public class MailConfig {

    private String postfix;

}
