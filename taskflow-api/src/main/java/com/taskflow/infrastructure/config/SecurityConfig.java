package com.taskflow.infrastructure.config;

import com.taskflow.infrastructure.adapter.out.security.service.CustomUserDetailsService;
import com.taskflow.infrastructure.adapter.in.web.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
// Classes for configuring registration endpoint
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Tells Spring that this class contains configuration
@EnableWebSecurity // Enables config for the web security
@RequiredArgsConstructor
public class SecurityConfig {

  // Inyect the UserDetailsService
  private final CustomUserDetailsService customUserDetailsService;
  private final JwtAuthenticationFilter jwtAuthenticationFilter; // Filter Inyection
  
  @Bean // Defines a bean for password encoding
  public PasswordEncoder passwordEncoder() {
    // Uses BCrypt algorithm for hashing passwords
    return new BCryptPasswordEncoder();
  }

  @Bean // Provider that tells Spring how authenticate
  public AuthenticationProvider authenticationProvider () {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    // Tells who is the user manager
    authProvider.setUserDetailsService(customUserDetailsService);
    // Tells what to encrypt
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  // Main security filter of the app
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable()) // Disable csrf (common in REST APIs)
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(authorizeRequests ->
        authorizeRequests
          // We able api/v1/users/register to be public
          .requestMatchers(
              "/api/v1/auth/**",
              "/error"
            ).permitAll()
          // Any other request must be authenticated
          .anyRequest().authenticated()
      )
      .authenticationProvider(authenticationProvider())
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }
}
