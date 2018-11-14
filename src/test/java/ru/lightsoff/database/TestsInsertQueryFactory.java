package ru.lightsoff.database;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightsoff.database.builders.QueryFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestsInsertQueryFactory {
    @Test
    public void insertOneRow() {
        Assertions.assertThat(QueryFactory
                .insert()
                .into("STALKER")
                .withColumn("guns")
                .withColumn("artifacts")
                .withRow()
                    .withValue("1")
                    .withValue("2")
                .and()
                .toString()
        ).isEqualTo("INSERT INTO STALKER (guns, artifacts) VALUES (1, 2);");
    }

    @Test
    public void insertThreeRows() {
        Assertions.assertThat(QueryFactory
                .insert()
                .into("STALKER")
                .withColumn("guns")
                .withColumn("artifacts")
                .withRow()
                    .withValue("1")
                    .withValue("2")
                .and()
                .withRow()
                    .withValue("3")
                    .withValue("4")
                .and()
                .withRow()
                    .withValue("5")
                    .withValue("6")
                .and()
                .toString()
        ).isEqualTo("INSERT INTO STALKER (guns, artifacts) VALUES (1, 2), (3, 4), (5, 6);");
    }


    @Test
    public void noInto_shouldReturnErrorMessage(){
        Assertions.assertThat(QueryFactory
                .insert()
                .withColumn("guns")
                .withColumn("artifacts")
                .withRow()
                    .withValue("1")
                    .withValue("2")
                .and()

                .toString()
        ).isEqualTo("Error");
    }

    @Test
    public void noRows_shouldReturnErrorMessage(){
        Assertions.assertThat(QueryFactory
                .insert()
                .withColumn("guns")
                .withColumn("artifacts")
                .toString()
        ).isEqualTo("Error");
    }

    @Test
    public void SQLInjectionInInto_shouldReturnErrorMessage(){
        Assertions.assertThat(QueryFactory
                .insert()
                .into("STALKER; SELECT hacker FROM HACKER;")
                .withColumn("guns")
                .withColumn("artifacts")
                .withRow()
                    .withValue("1")
                    .withValue("2")
                .and()
                .toString()
        ).isEqualTo("Error");

    }

    @Test
    public void SQLInjectionInColumn_shouldReturnErrorMessage(){
        Assertions.assertThat(QueryFactory
                .insert()
                .into("STALKER")
                .withColumn("guns; SELECT hacker FROM HACKER;")
                .withRow()
                    .withValue("1")
                .and()
                .toString()
        ).isEqualTo("Error");

    }

    @Test
    public void SQLInjectionInRow_shouldReturnErrorMessage(){
        Assertions.assertThat(QueryFactory
                .insert()
                .into("STALKER")
                .withColumn("guns")
                .withColumn("artifacts")
                .withRow()
                    .withValue("1; SELECT hacker FROM HACKER;")
                .and()
                .toString()
        ).isEqualTo("Error");

    }

}
