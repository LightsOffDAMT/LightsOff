package ru.lightsoff.database.DAO;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.ItemInGame;
import ru.lightsoff.database.Entities.ItemInGame;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

public class ItemInGameDAO implements ObjectDAO<ItemInGame> {
    @Autowired
    private Function<ItemInGame, String> findByIdItemInGame;
    @Autowired
    private Supplier<String> findAllItemInGame;
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

    @Override
    public Mono<QueryResponse<ArrayList<ItemInGame>>> findAll() {
        Long startTime = System.currentTimeMillis();
        String query = findAllItemInGame.get();
        return findQueryExecute(query, startTime);
    }

    private Mono<QueryResponse<ArrayList<ItemInGame>>> findQueryExecute(String query, Long startTime){
        ArrayList<ItemInGame> result = new ArrayList<>();
        try{
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                result.add(new ItemInGame()
                        .withId(resultSet.getLong("id"))
                        .withPosition(new Gson().fromJson(resultSet.getString("position"), Point.class))
                );
            }
            connection.close();
            return Mono.just
                    (
                            new QueryResponse<ArrayList<ItemInGame>>()
                                    .withData(Mono.just(result))
                                    .withStatus("[OK]")
                                    .withTime(startTime)
                    );
        } catch (SQLException e){
            e.printStackTrace();
            return Mono.just
                    (
                            new QueryResponse<ArrayList<ItemInGame>>()
                                    .withData(null)
                                    .withStatus("[Error]\n" + e.getMessage())
                                    .withTime(startTime)
                    );
        }
    }
}
