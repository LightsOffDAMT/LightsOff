package ru.lightsoff.database.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.Player;

import java.util.function.Function;

@Component(value = "DAO")
public class ObjectDAO<T> {
    private final Logger log = LoggerFactory.getLogger(ObjectDAO.class);
    @Autowired
    Function<Player, String> selectPlayer;
    Function<Player, String> insertPlayer;
    Function<Player, String> deletePlayer;
    Function<Player, String> findByIdPlayer;

    public Mono<QueryResponse<T>> update(T object){
        return null;
    }
    Mono<QueryResponse<T>> insert(T object){
        return null;
    }
    Mono<QueryResponse<T>> delete(T object){
        return null;
    }
    Flux<QueryResponse<T>> findById(String id){
        return null;
    }
}
