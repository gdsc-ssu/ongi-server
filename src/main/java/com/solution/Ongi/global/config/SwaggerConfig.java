package com.solution.Ongi.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // Define the security scheme
        SecurityScheme apiKey = new SecurityScheme()
            .type(Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")

            .in(SecurityScheme.In.HEADER)
            .name("Authorization");

        // Define the security requirement
        SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList("Authorization");

        // Add the security scheme to components
        Components components = new Components()
            .addSecuritySchemes("Authorization", apiKey);

        return new OpenAPI()
            .addServersItem(new Server().url("/"))
            .components(components)
            .addSecurityItem(securityRequirement)
            .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
            .title("Ongi API")
            .description("Ongi Swagger입니다.")
            .version("1.0.0");
    }
}
