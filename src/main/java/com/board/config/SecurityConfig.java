package com.board.config;


import com.board.common.jwt.CustomLogoutFilter;
import com.board.common.jwt.JWTFilter;
import com.board.common.jwt.JWTUtil;
import com.board.common.jwt.LoginFilter;
import com.board.member.repository.RefreshRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                // csrf : 주로 SSR인 경우 필요(html 코드를 수정하기 때문이라고 함), API 이기에 disable
                .csrf(AbstractHttpConfigurer::disable)

                // form 로그인 방식 미사용으로 disable
                .formLogin(AbstractHttpConfigurer::disable)

                // http 인증방식 미사용으로 disable
                .httpBasic(AbstractHttpConfigurer::disable)

                // 경로 인증 인가 설정
                .authorizeHttpRequests(
                        (auth) -> auth
                        .requestMatchers("/login", "/logout", "/join",
                                "/api/v1/auth/**").permitAll() // 모든 경로 허용
                        .requestMatchers("/admin").hasRole("ADMIN") // admin 권한자만 사용
                        .requestMatchers("/api/v1/post/**").authenticated()
                ) // 로그인한 사용자는 가능


                // 세션 없이 (stateless) / JWT 사용
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                /** JWT 설정 **/
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class)

                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class)

                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

        return http.build();
    }

    // 비밀번호 암호화
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager 반환 Bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
}
