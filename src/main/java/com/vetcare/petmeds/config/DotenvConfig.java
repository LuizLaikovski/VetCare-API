package com.vetcare.petmeds.config;

import org.springframework.context.annotation.Configuration;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class DotenvConfig {

    static {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory("./")  // raiz do projeto
                    .ignoreIfMissing()
                    .load();

            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
            });

            System.out.println("✅ Arquivo .env carregado com sucesso!");
        } catch (Exception e) {
            System.out.println("⚠️ Arquivo .env não encontrado, usando variáveis de sistema");
        }
    }
}