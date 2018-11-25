package ru.lightsoff.database;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.config.EnableWebFlux;
import ru.lightsoff.database.DAO.PlayerDAO;
import ru.lightsoff.database.storage.StorageManager;

@SpringBootApplication
@EnableWebFlux
public class DatabaseApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DatabaseApplication.class, args);
        PlayerDAO dao = context.getBean(PlayerDAO.class);
        System.out.println(new Gson().toJson(dao.findById(new Long(999)).block().getData().block()));
    }

}
