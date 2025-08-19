
// package com.myapp.sns.config;

// import com.myapp.sns.jwt.JwtFilter;
// import com.myapp.sns.jwt.JwtUtil;
// import lombok.RequiredArgsConstructor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// @RequiredArgsConstructor
// public class SecurityConfig {

//     private final JwtFilter jwtFilter;
//     private final JwtUtil jwtUtil;

//     // @Bean  --> Appconfig.java에 선언됨 (Appconfig에 전역에서 사용할 Bean 설정하는 클래스)
//     // public PasswordEncoder passwordEncoder() {
//     //     return new BCryptPasswordEncoder();
//     // }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(AbstractHttpConfigurer::disable)
//             .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/api/auth/**").permitAll()  // 로그인, 회원가입은 허용
//                 .requestMatchers("/api/posts").authenticated() // 게시글 작성은 인증 필요
//                 .anyRequest().permitAll()
//             )
//             .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }
// }














package com.myapp.sns.config;

import com.myapp.sns.jwt.JwtFilter;
import com.myapp.sns.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
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
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                
           // Swagger 허용
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() 
            .requestMatchers(HttpMethod.POST, "/api/posts/**").authenticated()
            .requestMatchers(HttpMethod.PUT, "/api/posts/**").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/api/posts/**").authenticated()
            .anyRequest().authenticated()
            )

        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

}
