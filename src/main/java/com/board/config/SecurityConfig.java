package com.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.rmi.server.ExportException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // csrf 보안 해제 (개발 환경에서 설정시 복잡성)
        http
                .csrf(csrf -> csrf.disable());

        // 접근 경로별 인가 설정
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll());

        // 로그인 방식 설정 Form 로그인 방식
        http
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
