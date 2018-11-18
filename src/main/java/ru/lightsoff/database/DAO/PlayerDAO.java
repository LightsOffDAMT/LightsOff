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
import java.util.function.Function;

@Component(value = "PlayerDAO")
public class PlayerDAO implements ObjectDAO<Player> {
    private final Logger log = LoggerFactory.getLogger(ObjectDAO.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    Function<Player, String> findAllPlayer;
    @Autowired
    Function<Player, String> insertPlayer;
    Function<Player, String> updatePlayer;
    Function<Player, String> deletePlayer;
    Function<Player, String> findByIdPlayer;
    NonAnswerQueryExecutor<Player> queryExecutor = new NonAnswerQueryExecutor<>();

    @Override
    public Mono<QueryResponse<Player>> update(Player object) {
        return queryExecutor.execute(updatePlayer, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<Player>> insert(Player object) {
        return queryExecutor.execute(insertPlayer, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<Player>> delete(Player object) {
        return queryExecutor.execute(deletePlayer, dataSource, object);
    }

    @Override
    public Flux<QueryResponse<Player>> findById(String id) {
        return null;
    }
}
