package ru.lightsoff.database.DAO;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lightsoff.database.Entities.Player;
import ru.lightsoff.database.builders.QueryFactory;

import java.util.Locale;
import java.util.function.Function;

@Configuration
public class EntitySerialization {
    @Bean
    public Function<Player, String> findAllPlayer(){
        return player -> QueryFactory
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
                    .withValue(String.format(Locale.US, "(%f,%f)", player.getPosition().getX(), player.getPosition().getY()))
                    .withValue(new Gson().toJson(player.getStats()))
                .and()
                .toString();
    }

    @Bean
    public Function<Player, String> updatePlayer(){
        return player -> QueryFactory
                .update()
                .from("players")
                .set("id = $, userid = $, name = $, inventory = $, position = $, stats = $",
                        player.getId().toString(), player.getUserID().toString(), new Gson().toJson(player.getInventory()),
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
