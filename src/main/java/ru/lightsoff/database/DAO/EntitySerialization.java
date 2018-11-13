package ru.lightsoff.database.DAO;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lightsoff.database.Entities.Player;
import ru.lightsoff.database.builders.QueryBuilder;

import java.util.function.Function;

@Configuration
public class EntitySerialization {
    @Bean
    public Function<Player, String> playerSelect(){
        return (player ->
           new QueryBuilder()
                   .select()
                   .from("sdsd")
                   .all()
                   .toString()
        );
    }
}
