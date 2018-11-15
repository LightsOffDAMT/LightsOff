package ru.lightsoff.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.lightsoff.database.DAO.ObjectDAO;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.Player;

@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DatabaseApplication.class, args);
        System.out.println(((QueryResponse)(context.getBean(ObjectDAO.class, ObjectDAO.class).insert(new Player(){{this.setId(100); this.setName("asdasd");}}).block())).getData());
    }

}
