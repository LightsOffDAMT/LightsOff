package ru.lightsoff.database.DAO.EntitySerializations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lightsoff.database.Entities.User;
import ru.lightsoff.database.builders.QueryFactory;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of queries that return functions for queries and operate on User object.
 */
@Configuration
public class UserSerialization {

    @Bean
    public Supplier<String> findAllUser() {
        return () -> QueryFactory
                .select()
                .from("users")
                .all()
                .toString();
    }

    @Bean
    public Function<User, String> findByIdUser() {
        return user -> QueryFactory
                .select()
                .from("users")
                .all()
                .where("id = $", user.getId().toString())
                .toString();

    }

    @Bean
    public Function<User, String> insertUser() {
        return user -> QueryFactory
                .insert()
                .into("users")
                .withColumn("id")
                .withColumn("login")
                .withColumn("password")
                .withColumn("email")
                .withColumn("nickname")
                .withColumn("online")
                .withRow()
                    .withValue(user.getId().toString())
                    .withValue(user.getLogin())
                    .withValue(user.getPassword())
                    .withValue(user.getEmail())
                    .withValue(user.getNickname())
                    .withValue(user.getOnline().toString())
                .and()
                .toString();

    }

    @Bean
    public Function<User, String> updateUser() {
        return user -> QueryFactory
                .update()
                .from("users")
                .set("login = $, password = $, email = $, nickname = $, online = $",
                user.getLogin(), user.getPassword(), user.getEmail(),
                user.getNickname(), user.getOnline().toString())
                .where("id = $", user.getId().toString())
                .toString();

    }

    @Bean
    public Function<User, String> deleteUser() {
        return user -> QueryFactory
                .delete()
                .from("users")
                .where("id = $", user.getId().toString())
                .toString();
    }

}
