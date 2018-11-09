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
                .withField("artifacts")
                .asc("artifacts")
                .toString()
        ).isEqualTo("SELECT gun, artifacts FROM STALKER ORDER BY artifacts ASC;");

    }


    @Test
    public void test2() {
        //With array of fields, descending by field
        Assertions.assertThat(new SelectQueryBuilder()
                .from("STALKER")
                .withFields(new ArrayList<>(Arrays.asList("gun", "artifacts")))
                .desc("artifacts")
                .toString()
        ).isEqualTo("SELECT gun, artifacts FROM STALKER ORDER BY artifacts DESC;");

    }


    @Test
    public void test3() {
         //With primary key, with field, descending by primary key
        Assertions.assertThat(new SelectQueryBuilder()
                .from("STALKER")
                .withPrimaryKey("id")
                .withField("gun")
                .desc()
                .toString()
        ).isEqualTo("SELECT id, gun FROM STALKER ORDER BY id DESC;");

    }


    @Test
    public void test4() {
          //With primary key, with field, ascending by primary key
        Assertions.assertThat(new SelectQueryBuilder()
                .from("STALKER")
                .withPrimaryKey("id")
                .withField("gun")
                .asc()
                .toString()
        ).isEqualTo("SELECT id, gun FROM STALKER ORDER BY id ASC;");

    }


    @Test
    public void test5() {
                //Without from
        Assertions.assertThat(new SelectQueryBuilder()
                .withPrimaryKey("id")
                .withField("gun")
                .desc()
                .toString()
        ).isEqualTo("Sosat\'");

    }


    @Test
    public void test6() {
         Assertions.assertThatExceptionOfType(SQLException.class).isThrownBy(() -> new SelectQueryBuilder()
                .withPrimaryKey("id")
                .withField("gun")
                .desc()
                .toString());

    }

    @Test
    public void test7() {
            //With field, asc() without parameters without primary key
        Assertions.assertThat(new SelectQueryBuilder()
                .from("STALKER")
                .withField("gun")
                .asc()
                .toString()
        ).isEqualTo("SELECT gun FROM STALKER;");

    }


    @Test
    public void test8() {
  //With field, desc() without parameters without primary key
        Assertions.assertThat(new SelectQueryBuilder()
                .from("STALKER")
                .withField("gun")
                .desc()
                .toString()
        ).isEqualTo("SELECT gun FROM STALKER;");
        //test
    }

}
