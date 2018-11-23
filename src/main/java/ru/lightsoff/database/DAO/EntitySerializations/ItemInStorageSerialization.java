package ru.lightsoff.database.DAO.EntitySerializations;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lightsoff.database.Entities.ItemInStorage;
import ru.lightsoff.database.builders.QueryFactory;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class ItemInStorageSerialization {
    @Bean
    public Supplier<String> findAllItemInStorage() {
        return () -> QueryFactory
                .select()
                .from("items_in_storage")
                .all()
                .toString();
    }

    @Bean
    public Function<ItemInStorage, String> findByIdItemInStorage() {
        return item -> QueryFactory
                .select()
                .from("items_in_storage")
                .all()
                .where("id = $", item.getId().toString())
                .toString();

    }

    @Bean
    public Function<ItemInStorage, String> insertItemInStorage() {
        return item -> QueryFactory
                .insert()
                .into("items_in_storage")
                .withColumn("id")
                .withColumn("name")
                .withColumn("properties")
                .withRow()
                    .withValue(item.getId().toString())
                    .withValue(item.getName())
                    .withValue(new Gson().toJson(item.getProperties()))
                .and()
                .toString();

    }

    @Bean
    public Function<ItemInStorage, String> updateItemInStorage() {
        return item -> QueryFactory
                .update()
                .from("items_in_storage")
                .set("name = $, properties = $",
                    item.getName(), new Gson().toJson(item.getProperties()))
                .where("id = $", item.getId().toString())
                .toString();

    }

    @Bean
    public Function<ItemInStorage, String> deleteItemInStorage() {
        return item -> QueryFactory
                .delete()
                .from("items_in_storage")
                .where("id = $", item.getId().toString())
                .toString();
    }
}
