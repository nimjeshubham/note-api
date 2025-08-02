package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        log.info("Configuring security web filter chain");
        SecurityWebFilterChain filterChain = http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/notes/**").permitAll()
                        .anyExchange().authenticated()
                )
                .httpBasic(httpBasic -> {})
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
        log.info("Security configuration completed");
        return filterChain;
    }
}