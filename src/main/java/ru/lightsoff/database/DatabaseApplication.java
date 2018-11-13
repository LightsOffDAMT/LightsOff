package ru.lightsoff.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.lightsoff.database.DAO.ObjectDAO;
import ru.lightsoff.database.Entities.Player;
import ru.lightsoff.database.builders.ColumnType;
import ru.lightsoff.database.builders.DeleteQueryBuilder;
import ru.lightsoff.database.builders.InsertQueryBuilder;
import ru.lightsoff.database.builders.QueryBuilder;

@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DatabaseApplication.class, args);
        context.getBean(ObjectDAO.class, ObjectDAO.class).update(new Player(){{this.setId(100); this.setName("asdasd");}});
    }

}
