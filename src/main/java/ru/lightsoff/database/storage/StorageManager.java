package ru.lightsoff.database.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.CollectionFactory;
import org.springframework.lang.Nullable;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.ObjectDAO;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IllegalFormatException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @RequestMapping("/get/{entity}")
    public Mono<ArrayList<Object>> get(@RequestBody @RequestParam @Nullable Long id, @PathVariable String entity){
        Optional<ObjectDAO> optionalObjectDAO = selectEntity(entity);
        ObjectDAO objectDAO;
        if(optionalObjectDAO.isPresent())
            objectDAO = optionalObjectDAO.get();
        else
            return Mono.just(new ArrayList<Object>(){{this.add("Error in path variable");}});
        Mono<QueryResponse<ArrayList<Object>>> responseMono;
        if(id == null)
            responseMono = objectDAO.findAll();
        else
            responseMono = objectDAO.findById(id);
        return responseMono.flatMap(QueryResponse::getData);
    }

    @PostMapping("/insert/player")
    private Mono<QueryResponse<Player>> insertPlayer(@RequestBody Player player){
        return playerDAO.insert(player);
    }

    @PostMapping("/insert/user")
    private Mono<QueryResponse<User>> insertUser(@RequestBody User user){
        return userDAO.insert(user);
    }

    @PostMapping("/insert/itemInGame")
    private Mono<QueryResponse<ItemInGame>> insertItemInGame(@RequestBody ItemInGame itemInGame){
        return itemInGameDAO.insert(itemInGame);
    }

    @PostMapping("/insert/itemInStorage")
    private Mono<QueryResponse<ItemInStorage>> insertItemInStorage(@RequestBody ItemInStorage itemInStorage){
        return itemInStorageDAO.insert(itemInStorage);
    }

    @PostMapping("/insert/gameMap")
    private Mono<QueryResponse<GameMap>> insertGameMap(@RequestBody GameMap gameMap){
        return gameMapDAO.insert(gameMap);
    }

    @PostMapping("/delete/player")
    private Mono<QueryResponse<Player>> deletePlayer(@RequestBody Player player){
        return playerDAO.delete(player);
    }

    @PostMapping("/delete/user")
    private Mono<QueryResponse<User>> deleteUser(@RequestBody User user){
        return userDAO.delete(user);
    }

    @PostMapping("/delete/itemInGame")
    private Mono<QueryResponse<ItemInGame>> deleteItemInGame(@RequestBody ItemInGame itemInGame){
        return itemInGameDAO.delete(itemInGame);
    }

    @PostMapping("/delete/itemInStorage")
    private Mono<QueryResponse<ItemInStorage>> deleteItemInStorage(@RequestBody ItemInStorage itemInStorage){
        return itemInStorageDAO.delete(itemInStorage);
    }

    @PostMapping("/delete/gameMap")
    private Mono<QueryResponse<GameMap>> deleteGameMap(@RequestBody GameMap gameMap){
        return gameMapDAO.delete(gameMap);
    }

    @PostMapping("/update/player")
    private Mono<QueryResponse<Player>> updatePlayer(@RequestBody Player player){
        return playerDAO.update(player);
    }

    @PostMapping("/update/user")
    private Mono<QueryResponse<User>> updateUser(@RequestBody User user){
        return userDAO.update(user);
    }

    @PostMapping("/update/itemInGame")
    private Mono<QueryResponse<ItemInGame>> updateItemInGame(@RequestBody ItemInGame itemInGame){
        return itemInGameDAO.update(itemInGame);
    }

    @PostMapping("/update/itemInStorage")
    private Mono<QueryResponse<ItemInStorage>> updateItemInStorage(@RequestBody ItemInStorage itemInStorage){
        return itemInStorageDAO.update(itemInStorage);
    }

    @PostMapping("/update/gameMap")
    private Mono<QueryResponse<GameMap>> updateGameMap(@RequestBody GameMap gameMap){
        return gameMapDAO.update(gameMap);
    }

    private Optional<ObjectDAO> selectEntity(String entity){
        switch (entity){
            case "player":
                return Optional.of(playerDAO);
            case "user":
                return Optional.of(userDAO);
            case "item_in_storage":
                return Optional.of(itemInStorageDAO);
            case "item_in_game":
                return Optional.of(itemInGameDAO);
            case "game_map":
                return Optional.of(gameMapDAO);
            default:
                return Optional.empty();
        }
    }
}
