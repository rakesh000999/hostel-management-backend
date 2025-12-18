package com.hostel.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())  // Disable CSRF for Postman or React testing
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/public/**").permitAll()   // allow these without login
//                        .requestMatchers("/api/students/**").permitAll() // temporary: allow all student endpoints
//                        .requestMatchers("/api/room/**").permitAll()
//                        .requestMatchers("/api/attendance/**").permitAll()
////                        .anyRequest().authenticated()                    // other requests need authentication
//                        .anyRequest().permitAll()
//                );
////                .httpBasic();  // enable basic auth for testing (username/password)
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/api/students/**").permitAll()
                    .requestMatchers("/api/rooms/**").permitAll()
                    .requestMatchers("/api/bookings/**").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2Login(Customizer.withDefaults());
    return http.build();
    }

}
