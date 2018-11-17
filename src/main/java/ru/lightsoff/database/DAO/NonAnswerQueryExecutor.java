package ru.lightsoff.database.DAO;

import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.Player;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

public class NonAnswerQueryExecutor<T> {
    public Mono<QueryResponse<T>> execute(Function<T, String> serialization, DataSource dataSource, T object){
        long startTime = System.currentTimeMillis();
        String query = serialization.apply(object);
        try(Statement statement = dataSource.getConnection().createStatement()) {
            if(statement.execute(query)){
                return Mono.just
                        (
                                new QueryResponse<T>()
                                        .withStatus("Ok")
                                        .withTime(startTime)
                        );
            } else {
                return Mono.just
                        (
                                new QueryResponse<T>()
                                        .withStatus(statement.getWarnings().getMessage())
                                        .withTime(startTime)
                        );
            }
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
