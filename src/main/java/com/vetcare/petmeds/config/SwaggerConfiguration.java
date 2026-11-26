package com.vetcare.petmeds.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    private static final String BEARER = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("VetCare")
                        .version("1.0")
                        .description("Essa é uma API voltada para o gerenciamento de uma clinica veterinária. "
                                + "Faça login em /user/login, copie o token retornado e informe-o no botão Authorize."))
                .components(new Components().addSecuritySchemes(BEARER, new SecurityScheme()
                        .name(BEARER)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .description("Token retornado pelo login")))
                .addSecurityItem(new SecurityRequirement().addList(BEARER));
    }
}
