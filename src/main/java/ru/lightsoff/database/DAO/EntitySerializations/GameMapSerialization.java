package ru.lightsoff.database.DAO.EntitySerializations;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lightsoff.database.Entities.GameMap;
import ru.lightsoff.database.builders.QueryFactory;

import java.util.function.Function;

public class GameMapSerialization {
    @Bean
    public Function<GameMap, String> findAll() {
        return gameMap -> QueryFactory
                .select()
                .from("game_maps")
                .all()
                .toString();
    }

    @Bean
    public Function<GameMap, String> findById() {
        return gameMap -> QueryFactory
                .select()
                .from("users")
                .all()
                .where("id = $", gameMap.getId().toString())
                .toString();

    }

    @Bean
    public Function<GameMap, String> insert() {
        return gameMap -> QueryFactory
                .insert()
                .into("game_maps")
                .withColumn("id")
                .withColumn("name")
                .withColumn("map")
                .withRow()
                    .withValue(gameMap.getId().toString())
                    .withValue(gameMap.getName())
                    .withValue(new Gson().toJson(gameMap.getMap()))
                .and()
                .toString();

    }

    @Bean
    public Function<GameMap, String> update() {
        return gameMap -> QueryFactory
                .update()
                .from("game_maps")
                .set("name = $, map = $",
                gameMap.getName(), new Gson().toJson(gameMap.getMap()))
                .where("id = $", gameMap.getId().toString())
                .toString();

    }

    @Bean
    public Function<GameMap, String> delete() {
        return gameMap -> QueryFactory
                .delete()
                .from("game_maps")
                .where("id = $", gameMap.getId().toString())
                .toString();
    }
}
