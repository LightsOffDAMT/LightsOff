package ru.lightsoff.database.DAO;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.ItemInGame;
import ru.lightsoff.database.Entities.ItemInStorage;
import ru.lightsoff.database.client.entities.injected.ItemInGameInjected;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Class that methods of DB access for ItemInGame Entity
 */
@Component
public class ItemInGameDAO implements ObjectDAO<ItemInGame> {
    @Autowired
    private Function<ItemInGame, String> findByIdItemInGame;
    @Autowired
    private Supplier<String> findAllItemInGame;
    @Autowired
    private Function<ItemInGame, String> findByIdItemInGameInjected;
    @Autowired
    private Supplier<String> findAllItemInGameInjected;
    @Autowired
    private Function<ItemInGame, String> insertItemInGame;
    @Autowired
    private Function<ItemInGame, String> updateItemInGame;
    @Autowired
    private Function<ItemInGame, String> deleteItemInGame;
    private NonAnswerQueryExecutor<ItemInGame> queryExecutor = new NonAnswerQueryExecutor<>();
    @Autowired
    private DataSource dataSource;

    @Override
    public Mono<QueryResponse<ItemInGame>> update(ItemInGame object) {
        return queryExecutor.execute(updateItemInGame, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<ItemInGame>> insert(ItemInGame object) {
        return queryExecutor.execute(insertItemInGame, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<ItemInGame>> delete(ItemInGame object) {
        return queryExecutor.execute(deleteItemInGame, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<ArrayList<ItemInGame>>> findById(Long id) {
        Long startTime = System.currentTimeMillis();
        String query = findByIdItemInGame.apply(new ItemInGame().withId(id));
        return findQueryExecute(query, startTime);
    }

    public Mono<QueryResponse<ArrayList<ItemInGameInjected>>> findByIdInjected(Long id) {
        Long startTime = System.currentTimeMillis();
        String query = findByIdItemInGameInjected.apply(new ItemInGame().withId(id));
        return findQueryExecuteInjected(query, startTime);
    }

    @Override
    public Mono<QueryResponse<ArrayList<ItemInGame>>> findAll() {
        Long startTime = System.currentTimeMillis();
        String query = findAllItemInGame.get();
        return findQueryExecute(query, startTime);
    }

    public Mono<QueryResponse<ArrayList<ItemInGameInjected>>> findAllInjected() {
        Long startTime = System.currentTimeMillis();
        String query = findAllItemInGameInjected.get();
        return findQueryExecuteInjected(query, startTime);
    }


    private Mono<QueryResponse<ArrayList<ItemInGame>>> findQueryExecute(final String query, final Long startTime){
        return Mono
                .fromCallable(()->{
                    ArrayList<ItemInGame> result = new ArrayList<>();
                    Connection connection = dataSource.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    while (resultSet.next()){
                        result.add(new ItemInGame()
                                .withId(resultSet.getLong("id"))
                                .withPosition(new Gson().fromJson(resultSet.getString("position"), Point.class))
                        );
                    }
                    connection.close();
                    return result;
                })
                .as
                        (
                                items -> Mono.just
                                        (
                                                new QueryResponse<ArrayList<ItemInGame>>()
                                                        .withStatus("[Ok]")
                                                        .withData(items)
                                                        .withTime(startTime)
                                        )
                        )
                .onErrorResume
                        (
                                throwable -> {
                                    throwable.printStackTrace();
                                    return Mono.just
                                            (
                                                    new QueryResponse<ArrayList<ItemInGame>>()
                                                            .withStatus("[Error]" + throwable.getMessage())
                                                            .withTime(startTime)
                                                            .withData(null)
                                            );
                                }
                        );
    }

    private Mono<QueryResponse<ArrayList<ItemInGameInjected>>> findQueryExecuteInjected(final String query, final Long startTime){
        return Mono
                .fromCallable(()->{
                    ArrayList<ItemInGameInjected> result = new ArrayList<>();
                    Connection connection = dataSource.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    while (resultSet.next()){
                        ItemInGame itemInGame = new ItemInGame()
                                .withId(resultSet.getLong("id"))
                                .withItemId(resultSet.getLong("itemid"))
                                .withPosition(new Gson().fromJson(resultSet.getString("position"), Point.class));
                        ItemInStorage itemInStorage = new ItemInStorage()
                                .withId(resultSet.getLong("id"))
                                .withName(resultSet.getString("name"))
                                .withProperties(new Gson().fromJson(resultSet.getString("properties"), HashMap.class));
                        result.add(new ItemInGameInjected(itemInStorage, itemInGame));
                    }
                    connection.close();
                    return result;
                })
                .as
                        (
                                items -> Mono.just
                                        (
                                                new QueryResponse<ArrayList<ItemInGameInjected>>()
                                                        .withStatus("[Ok]")
                                                        .withData(items)
                                                        .withTime(startTime)
                                        )
                        )
                .onErrorResume
                        (
                                throwable -> {
                                    throwable.printStackTrace();
                                    return Mono.just
                                            (
                                                    new QueryResponse<ArrayList<ItemInGameInjected>>()
                                                            .withStatus("[Error]" + throwable.getMessage())
                                                            .withTime(startTime)
                                                            .withData(null)
                                            );
                                }
                        );
    }
}
