package ru.lightsoff.database.DAO.EntitySerializations;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lightsoff.database.Entities.ItemInGame;
import ru.lightsoff.database.builders.QueryFactory;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of queries that return functions for queries and operate on ItemInGame object.
 */
@Configuration
public class ItemInGameSerialization {
    @Bean
    public Supplier<String> findAllItemInGame() {
        return () -> QueryFactory
                .select()
                .from("items_in_game")
                .all()
                .toString();
    }

    @Bean
    public Function<ItemInGame, String> findByIdItemInGame() {
        return item -> QueryFactory
                .select()
                .from("items_in_game")
                .all()
                .where("id = $", item.getId().toString())
                .toString();
    }

    @Bean
    public Supplier<String> findAllItemInGameInjected() {
        return () -> QueryFactory
                .select()
                .from("items_in_game")
                .all()
                .innerJoin("items_in_storage")
                .joinOn("items_in_game.itemid = items_in_storage.id")
                .toString();
    }

    @Bean
    public Function<ItemInGame, String> findByIdItemInGameInjected() {
        return item -> QueryFactory
                .select()
                .from("items_in_game")
                .all()
                .innerJoin("items_in_storage")
                .joinOn("items_in_game.itemid = items_in_storage.id")
                .where("items_in_game.id = $", item.getId().toString())
                .toString();
    }

    @Bean
    public Function<ItemInGame, String> insertItemInGame() {
        return item -> QueryFactory
                .insert()
                .into("items_in_game")
                .withColumn("id")
                .withColumn("itemid")
                .withColumn("position")
                .withRow()
                    .withValue(item.getId().toString())
                    .withValue(item.getItemID().toString())
                    .withValue(new Gson().toJson(item.getPosition()))
                .and()
                .toString();
    }

    @Bean
    public Function<ItemInGame, String> updateItemInGame() {
        return item -> QueryFactory
                .update()
                .from("items_in_game")
                .set("position = $",
                        String.format("(%f,%f)", item.getPosition().getX(), item.getPosition().getY()))
                .where("id = $", item.getId().toString())
                .toString();
    }

    @Bean
    public Function<ItemInGame, String> deleteItemInGame() {
        return item -> QueryFactory
                .delete()
                .from("items_in_game")
                .where("id = $", item.getId().toString())
                .toString();
    }
}
