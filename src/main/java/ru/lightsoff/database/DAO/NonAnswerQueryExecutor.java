package ru.lightsoff.database.DAO;

import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

public class NonAnswerQueryExecutor<T> {
    public Mono<QueryResponse<T>> execute(Function<T, String> serialization, DataSource dataSource, T object) {
        long startTime = System.currentTimeMillis();
        String query = serialization.apply(object);
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            Integer result = statement.executeUpdate(query);
            connection.close();
            return Mono.just
                    (
                            new QueryResponse<T>()
                                    .withStatus("[OK] Rows changed: " + result)
                                    .withTime(startTime)
                    );
        } catch (SQLException e) {
            e.printStackTrace();
            return Mono.just
                    (
                            new QueryResponse<T>()
                                    .withStatus(e.getMessage())
                                    .withTime(startTime)
                    );
        }
    }
}
