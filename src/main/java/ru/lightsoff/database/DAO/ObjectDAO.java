package ru.lightsoff.database.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.Player;

import javax.management.Query;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

@Component(value = "DAO")
public interface ObjectDAO<T> {
    Mono<QueryResponse<T>> update(T object);
    Mono<QueryResponse<T>> insert(T object);
    Mono<QueryResponse<T>> delete(T object);
    Flux<QueryResponse<T>> findById(String id);
}
