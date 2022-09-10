package com.example.twitterapi.security;

import com.example.twitterapi.security.auth.AuthMiddlware;
import com.example.twitterapi.security.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletResponse;

@Configuration
@RequiredArgsConstructor
public class SecConfig {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsImpl userDetails;
    @Value("${jwt_secret}")
    private String secret;

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
                                .antMatchers(HttpMethod.GET,"/api/auth").hasAuthority("USER")
                                .antMatchers(HttpMethod.GET,"/api/user/**").permitAll()
                                .antMatchers("/api/user","/api/user/**").hasAuthority("USER")
                                .anyRequest().permitAll()
                                .and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilter(new AuthMiddlware(authenticationManager,userDetails,secret))
                                .exceptionHandling()
                                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"));

                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }).httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
