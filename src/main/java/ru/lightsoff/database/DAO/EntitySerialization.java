package ru.lightsoff.database.DAO;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lightsoff.database.Entities.Player;
import ru.lightsoff.database.builders.QueryFactory;

import java.util.function.Function;

@Configuration
public class EntitySerialization {
    @Bean
    public Function<Player, String> selectPlayer(){
        return (player ->
           new QueryFactory()
                   .select()
                   .from("sdsd")
                   .all()
                   .toString()
        );
    }
}
