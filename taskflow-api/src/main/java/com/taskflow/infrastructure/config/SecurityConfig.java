package com.taskflow.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// Classes for configuring registration endpoint
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Tells Spring that this class contains configuration
@EnableWebSecurity // Enables config for the web security
public class SecurityConfig {
  
  @Bean // Defines a bean for password encoding
  public PasswordEncoder passwordEncoder() {
    // Uses BCrypt algorithm for hashing passwords
    return new BCryptPasswordEncoder();
  }

  // Main security filter of the app
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable()) // Disable csrf (common in REST APIs)
      .authorizeHttpRequests(authorizeRequests ->
        authorizeRequests
          // We able api/v1/users/register to be public
          .requestMatchers("/api/v1/users/register").permitAll()
          // Any other request must be authenticated
          .anyRequest().authenticated()
      );
    
    return http.build();
  }
}
