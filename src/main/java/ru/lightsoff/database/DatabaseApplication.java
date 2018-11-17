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

@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DatabaseApplication.class, args);
//        Player player = new Player().withId(999).withName("ddd").withInventory(new ArrayList<ArrayList<Integer>>()).withPosition(new Point(1, 2)).withStats(new ArrayList<Integer>()).withUserID(222);
//        Mono<QueryResponse<Player>> mono = new PlayerDAO().insert(player);
//        mono.subscribe(playerQueryResponse -> System.out.println(playerQueryResponse.getStatus()));
//        System.out.println(((QueryResponse)(context.getBean(PlayerDAO.class, PlayerDAO.class).insert(new Player(){{this.setId(100); this.setName("asdasd");}}).block())).getData());
    }

}
