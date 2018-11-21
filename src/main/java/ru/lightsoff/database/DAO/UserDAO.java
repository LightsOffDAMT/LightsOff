package ru.lightsoff.database.DAO;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.Player;
import ru.lightsoff.database.Entities.User;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.function.Function;

public class UserDAO implements ObjectDAO<User> {
    @Autowired
    private Function<User, String> findByIdUser;
    @Autowired
    private Function<User, String> findAllUser;
    @Autowired
    private Function<User, String> insertUser;
    @Autowired
    private Function<User, String> updateUser;
    @Autowired
    private Function<User, String> deleteUser;
    private NonAnswerQueryExecutor<User> queryExecutor = new NonAnswerQueryExecutor<>();
    @Autowired
    private DataSource dataSource;

    @Override
    public Mono<QueryResponse<User>> update(User object) {
        return queryExecutor.execute(updateUser, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<User>> insert(User object) {
        return queryExecutor.execute(insertUser, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<User>> delete(User object) {
        return queryExecutor.execute(deleteUser, dataSource, object);
    }

    @Override
    public Mono<QueryResponse<ArrayList<User>>> findById(Long id) {
        Long startTime = System.currentTimeMillis();
        String query = findByIdUser.apply(new User().withId(id));
        return findQueryExecute(query, startTime);
    }

    @Override
    public Mono<QueryResponse<ArrayList<User>>> findAll() {
        Long startTime = System.currentTimeMillis();
        String query = findAllUser.apply(null);
        return findQueryExecute(query, startTime);
    }

    private Mono<QueryResponse<ArrayList<User>>> findQueryExecute(String query, Long startTime){
        ArrayList<User> result = new ArrayList<>();
        try{
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                result.add(new User()
                            .withId(resultSet.getLong("id"))
                            .online(resultSet.getBoolean("online"))
                            .withLogin(resultSet.getString("login"))
                            .withPassword(resultSet.getString("password"))
                            .withEmail(resultSet.getString("email"))
                            .withNickname(resultSet.getString("nickname"))
                );
            }
            connection.close();
            return Mono.just
                    (
                            new QueryResponse<ArrayList<User>>()
                                    .withData(result)
                                    .withStatus("[OK]")
                                    .withTime(startTime)
                    );
        } catch (SQLException e){
            e.printStackTrace();
            return Mono.just
                    (
                            new QueryResponse<ArrayList<User>>()
                                    .withData(null)
                                    .withStatus("[Error]\n" + e.getMessage())
                                    .withTime(startTime)
                    );
        }
    }
}
