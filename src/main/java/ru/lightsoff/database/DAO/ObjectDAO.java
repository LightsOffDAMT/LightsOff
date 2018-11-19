package ru.lightsoff.database.DAO;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;

import java.util.ArrayList;

@Component(value = "DAO")
public interface ObjectDAO<T> {
    Mono<QueryResponse<T>> update(T object);
    Mono<QueryResponse<T>> insert(T object);
    Mono<QueryResponse<T>> delete(T object);
    Mono<QueryResponse<ArrayList<T>>> findById(Long id);
    Mono<QueryResponse<ArrayList<T>>> findAll();
}
