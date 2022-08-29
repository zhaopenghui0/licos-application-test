package com.licos.licosapplicationtest.com.licos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * @author zph
 * @Description:
 */
@Configuration
@EnableConfigurationProperties(LicosConfig.class)
public class ApplicationSecurityConfig
        extends WebSecurityConfigurerAdapter {
    @Autowired
    private LicosConfig licosConfig;

    //@Override
    //protected void configure(HttpSecurity http)
    //        throws Exception {
    //    http.authorizeRequests()
    //            .anyRequest()
    //            .authenticated()
    //            .and()
    //            .oauth2Login();
    //}
    ApplicationSecurityConfig(LicosConfig licosConfig){
        this.licosConfig = licosConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth2login").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login().loginPage("/oauth2login")
                .defaultSuccessUrl("/login_success")
                .failureUrl("/login_failure");
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(
                clientRegistrationRepository());
    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration licosClient =
                CommonOAuth2Provider.GOOGLE.getBuilder("licos")
                        .clientId(licosConfig.getClientId())
                        .authorizationUri("http://192.168.12.203:8090/authorize")
                        .clientSecret(licosConfig.getClientSecret())
                        .scope("refresh_token","password","client_credentials")
                        .clientName("Licos")
                        .build();

        return new InMemoryClientRegistrationRepository(
                licosClient);
    }

}
