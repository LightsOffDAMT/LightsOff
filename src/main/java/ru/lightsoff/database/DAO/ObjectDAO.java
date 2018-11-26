package ru.lightsoff.database.DAO;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;

import java.util.ArrayList;

/**
 * Interface of DAO objects
 * @param <T> Class of entity corresponding to DAO
 */
@Component(value = "DAO")
public interface ObjectDAO<T> {
    /**
     * Sends query to update the row corresponding to the specified object.
     * @param object Object to gather data from
     * @return Mono<QueryResponse<T>>
     */
    Mono<QueryResponse<T>> update(T object);

    /**
     * Sends query to insert a row with data from the specified object.
     * @param object Object to gather data from
     * @return Mono<QueryResponse<T>>
     */
    Mono<QueryResponse<T>> insert(T object);

    /**
     * Sends query to delete the row corresponding to the specified object.
     * @param object Object with data of the deleting row
     * @return Mono<QueryResponse<T>>
     */
    Mono<QueryResponse<T>> delete(T object);

    /**
     * Sends query to select row with specified ID
     * @param id ID of the selecting object
     * @return Mono<QueryResponse<T>>
     */
    Mono<QueryResponse<ArrayList<T>>> findById(Long id);

    /**
     * Sends query to select all rows from table of this class.
     * @return Mono<QueryResponse<T>>
     */
    Mono<QueryResponse<ArrayList<T>>> findAll();
}
