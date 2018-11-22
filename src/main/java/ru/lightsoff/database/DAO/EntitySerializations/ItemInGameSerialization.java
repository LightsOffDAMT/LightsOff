package ru.lightsoff.database.DAO.EntitySerializations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lightsoff.database.Entities.ItemInGame;
import ru.lightsoff.database.builders.QueryFactory;

import java.util.function.Function;
import java.util.function.Supplier;

public class ItemInGameSerialization {
    @Bean
    public Supplier<String> findAll() {
        return () -> QueryFactory
                .select()
                .from("items_in_game")
                .all()
                .toString();
    }

    @Bean
    public Function<ItemInGame, String> findById() {
        return item -> QueryFactory
                .select()
                .from("items_in_game")
                .all()
                .where("id = $", item.getId().toString())
                .toString();
    }

    @Bean
    public Function<ItemInGame, String> insert() {
        return item -> QueryFactory
                .insert()
                .into("items_in_game")
                .withColumn("id")
                .withColumn("position")
                .withRow()
                    .withValue(item.getId().toString())
                    .withValue(
                            String.format("(%f,%f)", item.getPosition().getX(), item.getPosition().getY())
                    )
                .and()
                .toString();
    }

    @Bean
    public Function<ItemInGame, String> update() {
        return item -> QueryFactory
                .update()
                .from("items_in_game")
                .set("position = $",
                        String.format("(%f,%f)", item.getPosition().getX(), item.getPosition().getY()))
                .where("id = $", item.getId().toString())
                .toString();
    }

    @Bean
    public Function<ItemInGame, String> delete() {
        return item -> QueryFactory
                .delete()
                .from("items_in_game")
                .where("id = $", item.getId().toString())
                .toString();
    }
}
