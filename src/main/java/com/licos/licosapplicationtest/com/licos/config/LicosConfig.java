package com.licos.licosapplicationtest.com.licos.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zph
 * @Description:
 */
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.licos")
@Getter
@Setter
public class LicosConfig {
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 客户端secret
     */
    private String clientSecret;
}
