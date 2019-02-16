package ru.lightsoff.database.DAO.EntitySerializations;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lightsoff.database.Entities.Player;
import ru.lightsoff.database.builders.QueryFactory;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of queries that return functions for queries and operate on Player object.
 */
@Configuration
public class PlayerSerialization {
    @Bean
    public Supplier<String> findAllPlayer(){
        return () -> QueryFactory
                .select()
                .from("players")
                .all()
                .toString();
    }

    @Bean
    public Function<Player, String> findByIdPlayer(){
        return player -> QueryFactory
                .select()
                .from("players")
                .all()
                .where("id = $", player.getId().toString())
                .toString();
    }

    @Bean
    public Function<Player, String> findByIdPlayerInjected(){
        return player -> QueryFactory
                .select()
                .from("players")
                .all()
                .innerJoin("users")
                .joinOn("players.userid = users.id")
                .where("id = $", player.getId().toString())
                .toString();
    }

    @Bean
    public Supplier<String> findAllPlayerInjected(){
        return () -> QueryFactory
                .select()
                .from("players")
                .all()
                .innerJoin("users")
                .joinOn("players.userid = users.id")
                .toString();
    }

    @Bean
    public Function<Player, String> insertPlayer(){
        return player -> QueryFactory
                .insert()
                .into("players")
                .withColumn("id")
                .withColumn("userid")
                .withColumn("name")
                .withColumn("inventory")
                .withColumn("position")
                .withColumn("stats")
                .withRow()
                    .withValue(player.getId().toString())
                    .withValue(player.getUserID().toString())
                    .withValue(player.getName())
                    .withValue(new Gson().toJson(player.getInventory()))
                    .withValue(new Gson().toJson(player.getPosition()))
                    .withValue(new Gson().toJson(player.getStats()))
                .and()
                .toString();
    }

    @Bean
    public Function<Player, String> updatePlayer(){
        return player -> QueryFactory
                .update()
                .from("players")
                .set("userid = $, name = $, inventory = $, position = $, stats = $",
                        player.getUserID().toString(), new Gson().toJson(player.getInventory()),
                        String.format("(%f,%f)", player.getPosition().getX(), player.getPosition().getY()),
                        new Gson().toJson(player.getStats()))
                .where("id = $", player.getId().toString())
                .toString();
    }

    @Bean
    public Function<Player, String> deletePlayer(){
        return player -> QueryFactory
                .delete()
                .from("players")
                .where("id = $", player.getId().toString())
                .toString();
    }
}
