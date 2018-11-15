package ru.lightsoff.database.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.Player;

import javax.management.Query;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

@Component(value = "DAO")
public class ObjectDAO<T> {
    private final Logger log = LoggerFactory.getLogger(ObjectDAO.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    Function<Player, String> selectPlayer;
    @Autowired
    Function<Player, String> insertPlayer;
    Function<Player, String> deletePlayer;
    Function<Player, String> findByIdPlayer;

    public Mono<QueryResponse<?>> update(T object){

        return null;
    }
    public  Mono<QueryResponse<?>> insert(T object){
        long startTime = System.currentTimeMillis();
        long finishTime;
        if(object instanceof Player){
            Player entity = (Player)object;
            String query = insertPlayer.apply(entity);
            try(Statement statement = dataSource.getConnection().createStatement()) {
                if(statement.execute(query)){
                    finishTime = System.currentTimeMillis();
                    return Mono.just
                            (
                                new QueryResponse<Player>()
                                        .withStatus("Ok")
                                        .withTime(finishTime - startTime)
                            );
                } else {
                    finishTime = System.currentTimeMillis();
                    return Mono.just
                            (
                                new QueryResponse<Player>()
                                        .withStatus(statement.getWarnings().getMessage())
                                        .withTime(finishTime - startTime)
                            );
                }
            } catch (SQLException e) {
                finishTime = System.currentTimeMillis();
                return Mono.just
                        (
                            new QueryResponse<Player>()
                                    .withStatus(e.getMessage())
                                    .withTime(finishTime - startTime)
                        );
            }
        }
        return Mono.empty();
    }
    Mono<QueryResponse<?>> delete(T object){
        return null;
    }
    Flux<QueryResponse<?>> findById(String id){
        return null;
    }
}
