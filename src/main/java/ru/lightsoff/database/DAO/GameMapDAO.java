package ru.lightsoff.database.DAO;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.GameMap;
import ru.lightsoff.database.Entities.GameMap;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class GameMapDAO<T> implements ObjectDAO<GameMap> {
    @Autowired
    private Function<GameMap, String> findByIdGameMap;
    @Autowired
    private Function<GameMap, String> findAllGameMap;
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
        String query = findAllGameMap.apply(null);
        return findQueryExecute(query, startTime);
    }

    private Mono<QueryResponse<ArrayList<GameMap>>> findQueryExecute(String query, Long startTime){
        ArrayList<GameMap> result = new ArrayList<>();
        try{
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                result.add(new GameMap()
                        .withId(resultSet.getLong("id"))
                        .withName(resultSet.getString("name"))
                        .withMap(new Gson().fromJson(resultSet.getString("map"), ArrayList.class))
                );
            }
            connection.close();
            return Mono.just
                    (
                            new QueryResponse<ArrayList<GameMap>>()
                                    .withData(result)
                                    .withStatus("[OK]")
                                    .withTime(startTime)
                    );
        } catch (SQLException e){
            e.printStackTrace();
            return Mono.just
                    (
                            new QueryResponse<ArrayList<GameMap>>()
                                    .withData(null)
                                    .withStatus("[Error]\n" + e.getMessage())
                                    .withTime(startTime)
                    );
        }
    }
}
