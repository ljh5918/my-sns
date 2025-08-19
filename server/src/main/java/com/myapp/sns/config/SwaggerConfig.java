package com.myapp.sns.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT"; // SecurityScheme 이름 (식별자)

        // 1. SecurityRequirement: API 요청마다 JWT 인증이 필요하다는 설정
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);

        // 2. Components: SecurityScheme 정의
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)                           // 스키마 이름
                .type(SecurityScheme.Type.HTTP)      // HTTP 방식 인증
                .scheme("bearer")                    // Bearer 방식 사용
                .bearerFormat("JWT")                 // JWT 토큰 포맷
        );

        // 3. OpenAPI 객체 반환 (Swagger UI 설정)
        return new OpenAPI()
                .components(new Components())        // (여기 부분은 없어도 됨 → 아래 components가 덮어씀)
                .info(apiInfo())                     // API 메타 정보 (제목, 설명, 버전)
                .addSecurityItem(securityRequirement)// JWT 인증을 전체 API에 적용
                .components(components);             // 위에서 정의한 인증 스키마 등록
    }

    // API 메타 정보
    private Info apiInfo() {
        return new Info()
                .title("API Test")
                .description("Let's practice Swagger UI")
                .version("1.0.0");
    }
}
