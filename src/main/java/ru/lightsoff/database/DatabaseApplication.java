package ru.lightsoff.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.ObjectDAO;
import ru.lightsoff.database.DAO.PlayerDAO;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DatabaseApplication.class, args);
        Player player = new Player().withId(9992L).withName("ddd").withInventory(new ArrayList<ArrayList<Integer>>()).withPosition(new Point(1, 2)).withStats(new ArrayList<Integer>()).withUserID(222);
        Player player1 = new Player().withId(99932L).withName("ddd").withInventory(new ArrayList<ArrayList<Integer>>()).withPosition(new Point(1, 2)).withStats(new ArrayList<Integer>()).withUserID(222);
        PlayerDAO dao = context.getBean(PlayerDAO.class);
        Mono<QueryResponse<Player>> mono = dao.insert(player);

        long avg = 0;

        for(int i = 0; i < 100; i++){
            QueryResponse<ArrayList<Player>> response = dao
                    .findAll()
                    .block();
            avg += response
                    .getTime();
            System.out.println(response.getStatus());
        }
        System.out.println(avg);

    }

}
