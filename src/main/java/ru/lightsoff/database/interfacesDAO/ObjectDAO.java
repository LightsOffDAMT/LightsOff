package ru.lightsoff.database.interfacesDAO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.interfacesDAO.QueryObjects.QueryResponse;

public interface ObjectDAO<T> {
    Mono<QueryResponse<T>> update(T object);
    Mono<QueryResponse<T>> insert(T object);
    Mono<QueryResponse<T>> delete(T object);
    Flux<QueryResponse<T>> findById(String id);
}
