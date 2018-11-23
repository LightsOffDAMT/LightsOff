package ru.lightsoff.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.lightsoff.database.storage.StorageManager;

@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DatabaseApplication.class, args);
        StorageManager storageManager = context.getBean(StorageManager.class);
        storageManager.startMachine();
        System.out.println(1);
        storageManager.toActive();
        System.out.println(2);
        storageManager.toPassive();
    }

}
