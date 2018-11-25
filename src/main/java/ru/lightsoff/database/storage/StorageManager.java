package ru.lightsoff.database.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.ObjectDAO;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.*;

import java.util.ArrayList;

@RestController
public class StorageManager {
    @Autowired
    ObjectDAO<Player> playerDAO;
    @Autowired
    ObjectDAO<User> userDAO;
    @Autowired
    ObjectDAO<ItemInStorage> itemInStorageDAO;
    @Autowired
    ObjectDAO<ItemInGame> itemInGameDAO;
    @Autowired
    ObjectDAO<GameMap> gameMapDAO;
    private Logger log = LoggerFactory.getLogger(StorageManager.class);

    @RequestMapping("/get/players")
    public Mono<ArrayList<Player>> getPlayersById(@RequestParam @Nullable Long id){
        Mono<QueryResponse<ArrayList<Player>>> responseMono;
        if(id == null)
            responseMono = playerDAO.findAll();
        else
            responseMono = playerDAO.findById(id);
        return responseMono.flatMap(QueryResponse::getData);
    }

}
