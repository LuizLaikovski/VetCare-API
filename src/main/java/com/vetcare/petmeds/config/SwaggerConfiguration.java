package com.vetcare.petmeds.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("VetCare")
                .version("1.0")
                .description("Essa é uma API voltada para o gerenciamento de uma clinica veterinária")

        );
    }
}
