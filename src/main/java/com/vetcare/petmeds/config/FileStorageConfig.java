package com.vetcare.petmeds.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
@NoArgsConstructor
@Data
public class FileStorageConfig {
    private String uploadDir;
}
