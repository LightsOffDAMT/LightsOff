package ru.lightsoff.database.DAO;

import com.google.gson.Gson;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonFactoryBean;
import ru.lightsoff.database.Entities.Player;
import ru.lightsoff.database.builders.QueryFactory;

import java.util.function.Function;

@Configuration
public class EntitySerialization {
    @Bean
    public Function<Player, String> selectPlayer(){
        return player -> QueryFactory
                   .select()
                   .from("sdsd")
                   .all()
                   .toString();
    }

    @Bean
    public Function<Player, String> insertPlayer(){
        return player -> QueryFactory
                .insert()
                .into("PLAYERS")
                .withColumn("ID")
                .withColumn("USER_ID")
                .withColumn("NAME")
                .withColumn("INVENTORY")
                .withColumn("POSITION")
                .withColumn("STATS")
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
}
