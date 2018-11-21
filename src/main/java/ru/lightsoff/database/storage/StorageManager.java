package ru.lightsoff.database.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.builders.StateMachineConfigurer;
import ru.lightsoff.database.DAO.ObjectDAO;
import ru.lightsoff.database.Entities.*;

public class StorageManager {
    @Autowired
    ObjectDAO<Player> playerDAO;
    @Autowired
    ObjectDAO<User> userDAO;
    @Autowired
    ObjectDAO<ItemInStorage> itemInStorageDAO;
    @Autowired
    ObjectDAO<ItemInGame> itemIndGameDAO;
    @Autowired
    ObjectDAO<GameMap> gameMapDAO;


}
