package ru.lightsoff.database.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories
public class Config {
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    @Profile({"Danya", "Drew"})
    DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://127.0.0.1/game@1");
        dataSource.setUsername("postgres");
        System.out.println(password);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    @Profile("Danya")
    DataSource datasource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    @Profile({"Danya", "Drew"})
    NamedParameterJdbcOperations operations(){
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
