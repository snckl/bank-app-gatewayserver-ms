package com.cnsn.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity){
        serverHttpSecurity.authorizeExchange(exchanges -> exchanges
                .pathMatchers(HttpMethod.GET).permitAll() // First one has priority cuz of order.
                .pathMatchers("/bank/accounts/**").authenticated()
                .pathMatchers("/bank/cards/**").authenticated()
                .pathMatchers("/bank/loans/**").authenticated())
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(Customizer.withDefaults()));
        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return (SecurityFilterChain) serverHttpSecurity.build();
    }

}
