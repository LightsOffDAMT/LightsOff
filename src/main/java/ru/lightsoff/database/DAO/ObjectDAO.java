package ru.lightsoff.database.DAO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;

public interface ObjectDAO<T> {
    Mono<QueryResponse<T>> update(T object);
    Mono<QueryResponse<T>> insert(T object);
    Mono<QueryResponse<T>> delete(T object);
    Flux<QueryResponse<T>> findById(String id);
}
