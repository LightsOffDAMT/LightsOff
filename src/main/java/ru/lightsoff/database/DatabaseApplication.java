package ru.lightsoff.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.config.EnableWebFlux;
import ru.lightsoff.database.builders.ColumnType;
import ru.lightsoff.database.builders.QueryFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
@SpringBootApplication
@EnableWebFlux
@EnableEurekaClient
public class DatabaseApplication {

    public static void main(String[] args) throws SQLException {
        ApplicationContext context = SpringApplication.run(DatabaseApplication.class, args);
        DataSource dao = context.getBean(DataSource.class);

    }
    //Creates all tables except Players, helpful initial method
    private void createAllTables(DataSource dao) throws Exception{
        dao.getConnection().createStatement().execute
                (
                        QueryFactory
                                .create()
                                .withName("game_maps")
                                .withField()
                                .name("id")
                                .type(ColumnType.SERIAL)
                                .and()
                                .withField()
                                .name("name")
                                .type(ColumnType.TEXT)
                                .and()
                                .withField()
                                .name("map")
                                .type(ColumnType.TEXT)
                                .and()
                                .toString()

                );
        dao.getConnection().createStatement().execute
                (
                        QueryFactory
                                .create()
                                .withName("items_in_game")
                                .withField()
                                .name("id")
                                .type(ColumnType.SERIAL)
                                .and()
                                .withField()
                                .name("name")
                                .type(ColumnType.TEXT)
                                .and()
                                .toString()

                );
        dao.getConnection().createStatement().execute
                (
                        QueryFactory
                                .create()
                                .withName("items_in_storage")
                                .withField()
                                .name("id")
                                .type(ColumnType.SERIAL)
                                .and()
                                .withField()
                                .name("name")
                                .type(ColumnType.TEXT)
                                .and()
                                .withField()
                                .name("properties")
                                .type(ColumnType.TEXT)
                                .and()
                                .toString()

                );
        dao.getConnection().createStatement().execute
                (
                        QueryFactory
                                .create()
                                .withName("users")
                                .withField()
                                .name("id")
                                .type(ColumnType.SERIAL)
                                .and()
                                .withField()
                                .name("login")
                                .type(ColumnType.TEXT)
                                .and()
                                .withField()
                                .name("password")
                                .type(ColumnType.TEXT)
                                .and()
                                .withField()
                                .name("email")
                                .type(ColumnType.TEXT)
                                .and()
                                .withField()
                                .name("nickname")
                                .type(ColumnType.TEXT)
                                .and()
                                .withField()
                                .name("online")
                                .type(ColumnType.TEXT)
                                .and()
                                .toString()

                );
    }
}
