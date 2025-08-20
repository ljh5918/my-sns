package com.myapp.sns.config;

import com.myapp.sns.jwt.JwtFilter;
import com.myapp.sns.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;

import java.beans.Customizer;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtUtil jwtUtil;

    // @Bean  --> Appconfig.java에 선언됨 (Appconfig에 전역에서 사용할 Bean 설정하는 클래스)
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> {}) //  CORS 활성화
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // .authorizeHttpRequests(auth -> auth
        //     .requestMatchers("/api/auth/**").permitAll()  // 로그인, 회원가입 허용
        //     .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll() // 조회는 허용
        //     .requestMatchers(HttpMethod.POST, "/api/posts/**").authenticated()
        //     .requestMatchers(HttpMethod.PUT, "/api/posts/**").authenticated()
        //     .requestMatchers(HttpMethod.DELETE, "/api/posts/**").authenticated()
        //     .anyRequest().authenticated()
        // )

           .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll() // 로그인, 회원가입 허용
            .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll() // 조회는 허용
            .requestMatchers(HttpMethod.POST, "/api/posts/**").authenticated()
            .requestMatchers(HttpMethod.PUT, "/api/posts/**").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/api/posts/**").authenticated()
                
           // Swagger 허용
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() 
            .anyRequest().authenticated()
            )

        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}


  // ✅ CORS 전역 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // React 개발 서버 주소
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

