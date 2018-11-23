package ru.lightsoff.database.DAO.EntitySerializations;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lightsoff.database.Entities.GameMap;
import ru.lightsoff.database.builders.QueryFactory;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class GameMapSerialization {
    @Bean
    public Supplier<String> findAllGameMap() {
        return () -> QueryFactory
                .select()
                .from("game_maps")
                .all()
                .toString();
    }

    @Bean
    public Function<GameMap, String> findByIdGameMap() {
        return gameMap -> QueryFactory
                .select()
                .from("users")
                .all()
                .where("id = $", gameMap.getId().toString())
                .toString();

    }

    @Bean
    public Function<GameMap, String> insertGameMap() {
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
    public Function<GameMap, String> updateGameMap() {
        return gameMap -> QueryFactory
                .update()
                .from("game_maps")
                .set("name = $, map = $",
                gameMap.getName(), new Gson().toJson(gameMap.getMap()))
                .where("id = $", gameMap.getId().toString())
                .toString();

    }

    @Bean
    public Function<GameMap, String> deleteGameMap() {
        return gameMap -> QueryFactory
                .delete()
                .from("game_maps")
                .where("id = $", gameMap.getId().toString())
                .toString();
    }
}
