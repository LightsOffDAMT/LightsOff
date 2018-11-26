package ru.lightsoff.database.DAO;

import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.function.Function;

/**
 * Class which provides method for executing non-select queries
 * @param <T> Class of entity corresponding to DAO
 */
public class NonAnswerQueryExecutor<T> {
    public Mono<QueryResponse<T>> execute(Function<T, String> serialization, DataSource dataSource, T object) {
        long startTime = System.currentTimeMillis();
        String query = serialization.apply(object);
            return Mono.fromCallable
                    (
                            ()->{
                                Connection connection = dataSource.getConnection();
                                Statement statement = connection.createStatement();
                                Integer result = statement.executeUpdate(query);
                                connection.close();
                                return new QueryResponse<T>()
                                        .withStatus("[OK] Rows changed: " + result)
                                        .withTime(startTime);
                            }
                    )
                    .onErrorResume
                    (
                            throwable -> Mono.just
                                        (
                                                new QueryResponse<T>()
                                                        .withStatus(throwable.getMessage())
                                                        .withTime(startTime)
                                        )
                    );
    }
}
