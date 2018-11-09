package ru.lightsoff.database;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightsoff.database.builders.SelectQueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseApplicationTests {

    // Maybe the order of the fields should not matter
    @Test
    public void contextLoads() {
        //With multiple single fields, ascending by field
        Assertions.assertThat(new SelectQueryBuilder()
                .from("STALKER")
                .withField("gun")
                .withField("artefacts")
                .asc("artefacts")
                .toString()
        ).isEqualTo("SELECT gun, artefacts FROM STALKER ORDER BY artefacts ASC;");

        //With array of fields, descending by field
        Assertions.assertThat(new SelectQueryBuilder()
                .from("STALKER")
                .withFields(new ArrayList<>(Arrays.asList("gun", "artefacts")))
                .desc("artefacts")
                .toString()
        ).isEqualTo("SELECT gun, artefacts FROM STALKER ORDER BY artefacts DESC;");

        //With primary key, with field, descending by primary key
        Assertions.assertThat(new SelectQueryBuilder()
                .from("STALKER")
                .withPrimaryKey("id")
                .withField("gun")
                .desc()
                .toString()
        ).isEqualTo("SELECT id, gun FROM STALKER ORDER BY id DESC;");

        //With primary key, with field, ascending by primary key
        Assertions.assertThat(new SelectQueryBuilder()
                .from("STALKER")
                .withPrimaryKey("id")
                .withField("gun")
                .asc()
                .toString()
        ).isEqualTo("SELECT id, gun FROM STALKER ORDER BY id ASC;");

        //Without from
        Assertions.assertThatExceptionOfType(SQLException.class).isThrownBy(() -> new SelectQueryBuilder()
                .withPrimaryKey("id")
                .withField("gun")
                .desc()
                .toString());

        //With field, asc() without parameters without primary key
        Assertions.assertThat(new SelectQueryBuilder()
                .from("STALKER")
                .withField("gun")
                .asc()
                .toString()
        ).isEqualTo("SELECT gun FROM STALKER;");


        //With field, desc() without parameters without primary key
        Assertions.assertThat(new SelectQueryBuilder()
                .from("STALKER")
                .withField("gun")
                .desc()
                .toString()
        ).isEqualTo("SELECT gun FROM STALKER;");


}

}
