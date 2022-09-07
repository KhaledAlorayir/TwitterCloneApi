package com.example.twitterapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecBeans {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests((auth) -> {
                    try{
                        auth
                                .anyRequest().permitAll();

                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }).httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
