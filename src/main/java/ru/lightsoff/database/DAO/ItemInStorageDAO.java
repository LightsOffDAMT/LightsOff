package ru.lightsoff.database.DAO;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.ItemInStorage;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class ItemInStorageDAO implements ObjectDAO<ItemInStorage> {
    @Autowired
    private Function<ItemInStorage, String> findByIdItemInStorage;
    @Autowired
    private Function<ItemInStorage, String> findAllItemInStorage;
    @Autowired
    private Function<ItemInStorage, String> insertItemInStorage;
    @Autowired
    private Function<ItemInStorage, String> updateItemInStorage;
    @Autowired
    private Function<ItemInStorage, String> deleteItemInStorage;
    private NonAnswerQueryExecutor<ItemInStorage> queryExecutor = new NonAnswerQueryExecutor<>();
    @Autowired
    private DataSource dataSource;

    @Override
    public Mono<QueryResponse<ItemInStorage>> update(ItemInStorage object) {
        return queryExecutor.execute(updateItemInStorage, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<ItemInStorage>> insert(ItemInStorage object) {
        return queryExecutor.execute(insertItemInStorage, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<ItemInStorage>> delete(ItemInStorage object) {
        return queryExecutor.execute(deleteItemInStorage, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<ArrayList<ItemInStorage>>> findById(Long id) {
        Long startTime = System.currentTimeMillis();
        String query = findByIdItemInStorage.apply(new ItemInStorage().withId(id));
        return findQueryExecute(query, startTime);
    }

    @Override
    public Mono<QueryResponse<ArrayList<ItemInStorage>>> findAll() {
        Long startTime = System.currentTimeMillis();
        String query = findAllItemInStorage.apply(null);
        return findQueryExecute(query, startTime);
    }

    private Mono<QueryResponse<ArrayList<ItemInStorage>>> findQueryExecute(String query, Long startTime){
        ArrayList<ItemInStorage> result = new ArrayList<>();
        try{
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                result.add(new ItemInStorage()
                        .withId(resultSet.getLong("id"))
                        .withName(resultSet.getString("name"))
                        .withProperties(new Gson().fromJson(resultSet.getString("properties"), HashMap.class))
                );
            }
            connection.close();
            return Mono.just
                    (
                            new QueryResponse<ArrayList<ItemInStorage>>()
                                    .withData(result)
                                    .withStatus("[OK]")
                                    .withTime(startTime)
                    );
        } catch (SQLException e){
            e.printStackTrace();
            return Mono.just
                    (
                            new QueryResponse<ArrayList<ItemInStorage>>()
                                    .withData(null)
                                    .withStatus("[Error]\n" + e.getMessage())
                                    .withTime(startTime)
                    );
        }
    }
}
