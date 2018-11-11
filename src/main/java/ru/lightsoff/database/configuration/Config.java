//package ru.lightsoff.database.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
//import org.springframework.jdbc.core.metadata.PostgresCallMetaDataProvider;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableJdbcRepositories
//public class Config {
//    @Bean
//    DataSource dataSource(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://127.0.0.1/game@1");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("kkh753");
//        return dataSource;
//    }
//
//    @Bean
//    NamedParameterJdbcOperations operations(){
//        return new NamedParameterJdbcTemplate(dataSource());
//    }
//}
