package ru.lightsoff.database.DAO;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;

import javax.sql.DataSource;

@TestConfiguration
public class TestsConfig {
    @Bean
    @Profile("Test")
    DataSource datasource() throws Exception{
        EmbeddedPostgres postgres = new EmbeddedPostgres();
        String url = postgres.start("localhost", 5432, "testdb", "user", "pass");
        return ds;
    }

}
