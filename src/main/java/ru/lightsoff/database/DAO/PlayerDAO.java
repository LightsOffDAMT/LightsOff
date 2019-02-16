package ru.lightsoff.database.DAO;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.Player;
import ru.lightsoff.database.Entities.User;
import ru.lightsoff.database.client.entities.injected.PlayerInjected;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Class that methods of DB access for Player Entity
 */
@Component(value = "playerDAO")
public class PlayerDAO implements ObjectDAO<Player> {
    private final Logger log = LoggerFactory.getLogger(ObjectDAO.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private Supplier<String> findAllPlayer;
    @Autowired
    private Function<Player, String> insertPlayer;
    @Autowired
    private Function<Player, String> updatePlayer;
    @Autowired
    private Function<Player, String> deletePlayer;
    @Autowired
    private Function<Player, String> findByIdPlayer;
    @Autowired
    private Function<Player, String> findByIdPlayerInjected;
    @Autowired
    private Supplier<String> findAllPlayerInjected;

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
    public Mono<QueryResponse<ArrayList<Player>>> findById(Long id) {
        Long startTime = System.currentTimeMillis();
        String query = findByIdPlayer.apply(new Player().withId(id));
        return findQueryExecute(query, startTime);
    }

    @Override
    public Mono<QueryResponse<ArrayList<Player>>> findAll() {
        Long startTime = System.currentTimeMillis();
        String query = findAllPlayer.get();
        return findQueryExecute(query, startTime);
    }

    public Mono<QueryResponse<ArrayList<PlayerInjected>>> findByIdInjected(Long id) {
        Long startTime = System.currentTimeMillis();
        String query = findByIdPlayerInjected.apply(new Player().withId(id));
        return findQueryExecuteInjected(query, startTime);
    }

    public Mono<QueryResponse<ArrayList<PlayerInjected>>> findAllInjected() {
        Long startTime = System.currentTimeMillis();
        String query = findAllPlayerInjected.get();
        return findQueryExecuteInjected(query, startTime);
    }
    private Mono<QueryResponse<ArrayList<Player>>> findQueryExecute(final String query, final Long startTime){
            return Mono
                    .fromCallable(()->{
                        ArrayList<Player> result = new ArrayList<>();
                        Connection connection = dataSource.getConnection();
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(query);
                        while (resultSet.next()){
                            result.add(new Player()
                                    .withId(Long.valueOf(resultSet.getString("id")))
                                    .withUserID(Long.valueOf(resultSet.getString("userid")))
                                    .withName(resultSet.getString("name"))
                                    .withInventory(new Gson().fromJson(resultSet.getString("inventory"), ArrayList.class))
                                    .withPosition(new Gson().fromJson(resultSet.getString("position"), Point.class))
                                    .withStats(new Gson().fromJson(resultSet.getString("stats"), ArrayList.class))
                            );
                        }
                        connection.close();
                        return result;
                    })
                    .as
                            (
                                    players -> Mono.just
                                            (
                                                    new QueryResponse<ArrayList<Player>>()
                                                            .withStatus("[Ok]")
                                                            .withData(players)
                                                            .withTime(startTime)
                                            )
                            )
                    .onErrorResume
                            (
                                    throwable -> {
                                        throwable.printStackTrace();
                                        return Mono.just
                                                (
                                                        new QueryResponse<ArrayList<Player>>()
                                                            .withStatus("[Error]" + throwable.getMessage())
                                                            .withTime(startTime)
                                                            .withData(null)
                                                );
                                    }
                            );
    }

    private Mono<QueryResponse<ArrayList<PlayerInjected>>> findQueryExecuteInjected(final String query, final Long startTime){
        return Mono
                .fromCallable(()->{
                    ArrayList<PlayerInjected> result = new ArrayList<>();
                    Connection connection = dataSource.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    while (resultSet.next()){
                        Player player = new Player()
                                            .withId(Long.valueOf(resultSet.getString("id")))
                                            .withUserID(Long.valueOf(resultSet.getString("userid")))
                                            .withName(resultSet.getString("name"))
                                            .withInventory(new Gson().fromJson(resultSet.getString("inventory"), ArrayList.class))
                                            .withPosition(new Gson().fromJson(resultSet.getString("position"), Point.class))
                                            .withStats(new Gson().fromJson(resultSet.getString("stats"), ArrayList.class));
                        User user = new User()
                                            .withId(resultSet.getLong("id"))
                                            .online(resultSet.getBoolean("online"))
                                            .withLogin(resultSet.getString("login"))
                                            .withPassword(resultSet.getString("password"))
                                            .withEmail(resultSet.getString("email"))
                                            .withNickname(resultSet.getString("nickname"));
                        result.add(new PlayerInjected(player, user));
                    }
                    connection.close();
                    return result;
                })
                .as
                        (
                                players -> Mono.just
                                        (
                                                new QueryResponse<ArrayList<PlayerInjected>>()
                                                        .withStatus("[Ok]")
                                                        .withData(players)
                                                        .withTime(startTime)
                                        )
                        )
                .onErrorResume
                        (
                                throwable -> {
                                    throwable.printStackTrace();
                                    return Mono.just
                                            (
                                                    new QueryResponse<ArrayList<PlayerInjected>>()
                                                            .withStatus("[Error]" + throwable.getMessage())
                                                            .withTime(startTime)
                                                            .withData(null)
                                            );
                                }
                        );
    }
 }
