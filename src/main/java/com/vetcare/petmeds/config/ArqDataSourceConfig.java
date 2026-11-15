package com.vetcare.petmeds.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.vetcare.petmeds.archives",
    entityManagerFactoryRef = "arqEntityManagerFactory",
    transactionManagerRef = "arqTransactionManager"
)
public class ArqDataSourceConfig {

    @Bean(name = "arqDataSourceProperties")
    @ConfigurationProperties("spring.datasource.arq")
    public DataSourceProperties arqDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "arqDataSource")
    @ConfigurationProperties("spring.datasource.arq.hikari")
    public DataSource arqDataSource(@Qualifier("arqDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "arqEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean arqEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("arqDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.vetcare.petmeds.archives")
                .persistenceUnit("arq")
                .build();
    }

    @Bean(name = "arqTransactionManager")
    public PlatformTransactionManager arqTransactionManager(
            @Qualifier("arqEntityManagerFactory") EntityManagerFactory arqEntityManagerFactory) {
        return new JpaTransactionManager(arqEntityManagerFactory);
    }

    @Bean(name = "arqFlyway")
    public Flyway arqFlyway(@Qualifier("arqDataSource") DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:arq_migration")
                .baselineOnMigrate(true)
                .validateOnMigrate(false)
                .load();
        flyway.migrate();
        return flyway;
    }
}
