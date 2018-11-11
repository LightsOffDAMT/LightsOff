package ru.lightsoff.database.ObjectsDAO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.Entities.Player;
import ru.lightsoff.database.builders.QueryBuilder;
import ru.lightsoff.database.builders.SelectQueryBuilder;
import ru.lightsoff.database.interfacesDAO.ObjectDAO;
import ru.lightsoff.database.interfacesDAO.QueryObjects.QueryResponse;

import javax.management.Query;

public class PlayerDAO implements ObjectDAO<Player> {


    @Override
    public Mono<QueryResponse<Player>> update(Player object) {
        new QueryBuilder()
                .update()
                .from("PLAYER")
                .where("id = $", object.getId())
                .set("name = $, inventory = $, stats = $, userID = $, position = $",
                        object.getName(), object.getInventory(), object.getStats(),
                        object.getUserID(), object.getPosiiton())
                .toString()


    }

    @Override
    public Mono<QueryResponse<Player>> insert(Player object) {
        return null;
    }

    @Override
    public Mono<QueryResponse<Player>> delete(Player object) {
        return null;
    }

    @Override
    public Flux<QueryResponse<Player>> findById(String id) {
        new QueryBuilder().select().withPrimaryKey("id")
                //no where
    }
}
