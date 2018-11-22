package ru.lightsoff.database.DAO;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.GameMap;
import ru.lightsoff.database.Entities.GameMap;
import ru.lightsoff.database.Entities.GameMap;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class GameMapDAO<T> implements ObjectDAO<GameMap> {
    @Autowired
    private Function<GameMap, String> findByIdGameMap;
    @Autowired
    private Supplier<String> findAllGameMap;
    @Autowired
    private Function<GameMap, String> insertGameMap;
    @Autowired
    private Function<GameMap, String> updateGameMap;
    @Autowired
    private Function<GameMap, String> deleteGameMap;
    private NonAnswerQueryExecutor<GameMap> queryExecutor = new NonAnswerQueryExecutor<>();
    @Autowired
    private DataSource dataSource;

    @Override
    public Mono<QueryResponse<GameMap>> update(GameMap object) {
        return queryExecutor.execute(updateGameMap, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<GameMap>> insert(GameMap object) {
        return queryExecutor.execute(insertGameMap, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<GameMap>> delete(GameMap object) {
        return queryExecutor.execute(deleteGameMap, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<ArrayList<GameMap>>> findById(Long id) {
        Long startTime = System.currentTimeMillis();
        String query = findByIdGameMap.apply(new GameMap().withId(id));
        return findQueryExecute(query, startTime);
    }

    @Override
    public Mono<QueryResponse<ArrayList<GameMap>>> findAll() {
        Long startTime = System.currentTimeMillis();
        String query = findAllGameMap.get();
        return findQueryExecute(query, startTime);
    }

    private Mono<QueryResponse<ArrayList<GameMap>>> findQueryExecute(final String query, final Long startTime){
        return Mono
                .fromCallable(()->{
                    ArrayList<GameMap> result = new ArrayList<>();
                    Connection connection = dataSource.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    while (resultSet.next()){
                        result.add(new GameMap()
                                .withId(resultSet.getLong("id"))
                                .withName(resultSet.getString("name"))
                                .withMap(new Gson().fromJson(resultSet.getString("map"), ArrayList.class))
                        );
                    }
                    connection.close();
                    return result;
                })
                .as
                        (
                                maps -> Mono.just
                                        (
                                                new QueryResponse<ArrayList<GameMap>>()
                                                        .withStatus("[Ok]")
                                                        .withData(maps)
                                                        .withTime(startTime)
                                        )
                        )
                .onErrorResume
                        (
                                throwable -> {
                                    throwable.printStackTrace();
                                    return Mono.just
                                            (
                                                    new QueryResponse<ArrayList<GameMap>>()
                                                            .withStatus("[Error]" + throwable.getMessage())
                                                            .withTime(startTime)
                                                            .withData(null)
                                            );
                                }
                        );
    }
}
