package com.vetcare.petmeds.config;

import org.springframework.context.annotation.Configuration;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class DotenvConfig {

    static {
        try {
            java.io.File envFile = new java.io.File("./.env");
            if (envFile.exists()) {
                Dotenv dotenv = Dotenv.configure()
                        .directory("./")  // raiz do projeto
                        .load();

                dotenv.entries().forEach(entry -> {
                    System.setProperty(entry.getKey(), entry.getValue());
                });

                System.out.println("Arquivo .env carregado com sucesso!");
            } else {
                System.out.println("Arquivo .env não encontrado, usando variáveis de sistema");
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo .env: " + e.getMessage());
        }
    }
}