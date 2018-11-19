package ru.lightsoff.database.DAO;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.Player;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
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
    @Autowired
    Function<Player, String> updatePlayer;
    @Autowired
    Function<Player, String> deletePlayer;
    @Autowired
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
    public Mono<QueryResponse<ArrayList<Player>>> findById(Long id) {
        Long startTime = System.currentTimeMillis();
        String query = findByIdPlayer.apply(new Player().withId(id));
        return findQueryExecute(query, startTime);
    }

    @Override
    public Mono<QueryResponse<ArrayList<Player>>> findAll() {
        Long startTime = System.currentTimeMillis();
        String query = findAllPlayer.apply(null);
        return findQueryExecute(query, startTime);
    }

    private Mono<QueryResponse<ArrayList<Player>>> findQueryExecute(String query, Long startTime){
        ArrayList<Player> result = new ArrayList<>();
        try{
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
            return Mono.just
                    (
                            new QueryResponse<ArrayList<Player>>()
                                    .withStatus("[Ok]")
                                    .withTime(startTime)
                                    .withData(result)
                    );
        } catch (SQLException e) {
            e.printStackTrace();
            return Mono.just
                    (
                            new QueryResponse<ArrayList<Player>>()
                                    .withStatus("[Error]\n" + e.getMessage())
                                    .withTime(startTime)
                                    .withData(null)
                    );
        }
    }
 }
