package ru.lightsoff.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.lightsoff.database.builders.ColumnType;
import ru.lightsoff.database.builders.DeleteQueryBuilder;
import ru.lightsoff.database.builders.QueryBuilder;

@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
        System.out.println(new QueryBuilder()
                .create()
                .withName("table")
                .withField()
                    .name("lo")
                    .type(ColumnType.SERIAL)
                    .constraint("PRIMARY KEY")
                .and()
                .toString());
    }

}
