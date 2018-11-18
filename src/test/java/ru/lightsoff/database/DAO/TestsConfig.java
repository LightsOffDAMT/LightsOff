package ru.lightsoff.database.DAO;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.lightsoff.database.Entities.Player;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;

import javax.sql.DataSource;

import java.sql.DriverManager;

import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_6;

@TestConfiguration
public class TestsConfig {

    @Bean
    EntitySerialization entitySerialization(){
        return new EntitySerialization();
    }

    @Bean
    public PlayerDAO playerDAO(){
        return new PlayerDAO();
    }

    @Bean
    @Profile("Test")
    DataSource datasource() throws Exception{
        EmbeddedPostgres postgres = new EmbeddedPostgres(V9_6);
        String url = postgres.start("localhost", 5432, "testdb", "user", "pass");
        DataSource dataSource = new DriverManagerDataSource();
        ((DriverManagerDataSource) dataSource).setUrl(url);
        return dataSource;
    }

}
