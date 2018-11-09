package ru.lightsoff.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightsoff.database.builders.SelectQueryBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(new SelectQueryBuilder()
                .withField("names")
                .from("siski")
                .asc("names")
                .getRaw());
    }

}
