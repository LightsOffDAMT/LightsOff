package ru.lightsoff.database.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.Player;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

@Component(value = "PlayerDAO")
public class PlayerDAO implements ObjectDAO<Player> {
    private final Logger log = LoggerFactory.getLogger(ObjectDAO.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    Function<Player, String> selectPlayer;
    @Autowired
    Function<Player, String> insertPlayer;
    Function<Player, String> deletePlayer;
    Function<Player, String> findByIdPlayer;

    @Override
    public Mono<QueryResponse<Player>> update(Player object) {
        return null;
    }

    @Override
    public Mono<QueryResponse<Player>> insert(Player object) {
        long startTime = System.currentTimeMillis();
        String query = insertPlayer.apply(object);
        try(Statement statement = dataSource.getConnection().createStatement()) {
            if(statement.execute(query)){
                return Mono.just
                        (
                                new QueryResponse<Player>()
                                        .withStatus("Ok")
                                        .withTime(startTime)
                        );
            } else {
                return Mono.just
                        (
                                new QueryResponse<Player>()
                                        .withStatus(statement.getWarnings().getMessage())
                                        .withTime(startTime)
                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Mono.just
                    (
                            new QueryResponse<Player>()
                                    .withStatus(e.getMessage())
                                    .withTime(startTime)
                    );
        }
    }

    @Override
    public Mono<QueryResponse<Player>> delete(Player object) {
        return null;
    }

    @Override
    public Flux<QueryResponse<Player>> findById(String id) {
        return null;
    }
}
