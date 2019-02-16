package ru.lightsoff.database.storage;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Applications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.CollectionFactory;
import org.springframework.lang.Nullable;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lightsoff.database.DAO.ObjectDAO;
import ru.lightsoff.database.DAO.PlayerDAO;
import ru.lightsoff.database.DAO.QueryObjects.QueryResponse;
import ru.lightsoff.database.Entities.*;
import ru.lightsoff.database.client.entities.injected.PlayerInjected;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CacheConfig(cacheNames = "database")
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

    @Cacheable
    @RequestMapping("/get/{entity}")
    public Mono<ArrayList<Object>> get(@RequestParam @Nullable Long id, @PathVariable String entity){
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

    @RequestMapping("/get_injected/player")
    public Mono<ArrayList<PlayerInjected>> getInjectedPlayer(@RequestParam @Nullable Long id){
        if(id != null)
            return ((PlayerDAO)playerDAO).findByIdInjected(id).flatMap(QueryResponse::getData);
        else
            return ((PlayerDAO)playerDAO).findAllInjected().flatMap(QueryResponse::getData);
    }

    @CachePut
    @PostMapping("/insert/player")
    public Mono<QueryResponse<Player>> insertPlayer(@RequestBody Player player){
        return playerDAO.insert(player);
    }

    @CachePut
    @PostMapping("/insert/user")
    public Mono<QueryResponse<User>> insertUser(@RequestBody User user){
        return userDAO.insert(user);
    }

    @CachePut
    @PostMapping("/insert/itemInGame")
    public Mono<QueryResponse<ItemInGame>> insertItemInGame(@RequestBody ItemInGame itemInGame){
        return itemInGameDAO.insert(itemInGame);
    }

    @CachePut
    @PostMapping("/insert/itemInStorage")
    public Mono<QueryResponse<ItemInStorage>> insertItemInStorage(@RequestBody ItemInStorage itemInStorage){
        return itemInStorageDAO.insert(itemInStorage);
    }

    @CachePut
    @PostMapping("/insert/gameMap")
    public Mono<QueryResponse<GameMap>> insertGameMap(@RequestBody GameMap gameMap){
        return gameMapDAO.insert(gameMap);
    }

    @CachePut
    @PostMapping("/delete/player")
    public Mono<QueryResponse<Player>> deletePlayer(@RequestBody Player player){
        return playerDAO.delete(player);
    }

    @CachePut
    @PostMapping("/delete/user")
    public Mono<QueryResponse<User>> deleteUser(@RequestBody User user){
        return userDAO.delete(user);
    }

    @CachePut
    @PostMapping("/delete/itemInGame")
    public Mono<QueryResponse<ItemInGame>> deleteItemInGame(@RequestBody ItemInGame itemInGame){
        return itemInGameDAO.delete(itemInGame);
    }

    @CachePut
    @PostMapping("/delete/itemInStorage")
    public Mono<QueryResponse<ItemInStorage>> deleteItemInStorage(@RequestBody ItemInStorage itemInStorage){
        return itemInStorageDAO.delete(itemInStorage);
    }

    @CachePut
    @PostMapping("/delete/gameMap")
    public Mono<QueryResponse<GameMap>> deleteGameMap(@RequestBody GameMap gameMap){
        return gameMapDAO.delete(gameMap);
    }

    @CachePut
    @PostMapping("/update/player")
    public Mono<QueryResponse<Player>> updatePlayer(@RequestBody Player player){
        return playerDAO.update(player);
    }

    @CachePut
    @PostMapping("/update/user")
    public Mono<QueryResponse<User>> updateUser(@RequestBody User user){
        return userDAO.update(user);
    }

    @PostMapping("/update/itemInGame")
    public Mono<QueryResponse<ItemInGame>> updateItemInGame(@RequestBody ItemInGame itemInGame){
        return itemInGameDAO.update(itemInGame);
    }

    @CachePut
    @PostMapping("/update/itemInStorage")
    public Mono<QueryResponse<ItemInStorage>> updateItemInStorage(@RequestBody ItemInStorage itemInStorage){
        return itemInStorageDAO.update(itemInStorage);
    }

    @CachePut
    @PostMapping("/update/gameMap")
    public Mono<QueryResponse<GameMap>> updateGameMap(@RequestBody GameMap gameMap){
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
