package ru.lightsoff.database.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import ru.lightsoff.database.DAO.ObjectDAO;
import ru.lightsoff.database.Entities.*;

@Component
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
    @Autowired
    StateMachine<States, Events> stateMachine;
    private Logger log = LoggerFactory.getLogger(StorageManager.class);

    public void startMachine() {
        stateMachine.start();
    }

    public void toActive(){
        stateMachine.sendEvent(Events.TO_ACTIVE);
        System.out.println(stateMachine.getState());
    }

    public void toPassive(){
        stateMachine.sendEvent(Events.TO_PASSIVE);
        System.out.println(stateMachine.getState());
    }
}
