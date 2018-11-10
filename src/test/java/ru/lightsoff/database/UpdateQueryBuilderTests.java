package ru.lightsoff.database;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightsoff.database.builders.QueryBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateQueryBuilderTests {

    @Test
    public void withOneSet(){
        Assertions.assertThat(new QueryBuilder()
                .update()
                .from("STALKER")
                .set("$ = 5", "guns")
                .where("$ = 10", "artifacts")
                .toString()
        ).isEqualTo("UPDATE STALKER SET guns = 5 WHERE artifacts = 10;");
    }

    @Test
    public void withTwoSets(){
        Assertions.assertThat(new QueryBuilder()
                .update()
                .from("STALKER")
                .set("$ = 5, $ = 7", "guns", "bottles")
                .where("$ = 10", "artifacts")
                .toString()
        ).isEqualTo("UPDATE STALKER SET guns = 5, bottles = 7 WHERE artifacts = 10;");
    } @Test
    
    public void withGreaterAndLesserThanInWhere(){
        Assertions.assertThat(new QueryBuilder()
                .update()
                .from("STALKER")
                .set("$ = 5", "guns")
                .where("$ < 10", "artifacts")
                .toString()
        ).isEqualTo("UPDATE STALKER SET guns = 5 WHERE artifacts < 10;");
    }

    @Test
    public void withOneEqualInWhere(){
        Assertions.assertThat(new QueryBuilder()
                .update()
                .set("$ = 5", "guns")
                .from("STALKER")
                .where("$ = 10", "artifacts")
                .toString()
        ).isEqualTo("UPDATE STALKER SET guns = 5 WHERE artifacts = 10;");
    }

    @Test
    public void withTwoEqualsInWhere(){
        Assertions.assertThat(new QueryBuilder()
                .update()
                .from("STALKER")
                .set("$ = 5", "guns")
                .where("$ = 5 AND $ = 10", "guns", "artifacts")
                .toString()
        ).isEqualTo("UPDATE STALKER SET guns = 5 WHERE guns = 5 AND artifacts = 10;");
    }

    @Test
    public void withoutFrom_shouldReturnErrorMessage() {
        Assertions.assertThat(new QueryBuilder()
                .update()
                .set("$ = 5", "guns")
                .where("$ = 5", "guns")
                .toString()
        ).isEqualTo("Sosat\'");
    }

    @Test
    public void withoutWhere_shouldReturnErrorMessage() {
        Assertions.assertThat(new QueryBuilder()
                .update()
                .from("STALKER")
                .set("$ = 5", "guns")
                .toString()
        ).isEqualTo("Sosat\'");
    }

    @Test
    public void withoutSet_shouldReturnErrorMessage() {
        Assertions.assertThat(new QueryBuilder()
                .update()
                .from("STALKER")
                .where("$ = 5", "guns")
                .toString()
        ).isEqualTo("Sosat\'");
    }

    @Test
    public void updateAll(){
        Assertions.assertThat(new QueryBuilder()
                .update()
                .all()
                .from("STALKER")
                .set("$ = 5", "guns")
                .toString()
        ).isEqualTo("UPDATE STALKER SET guns = 5;");
    }

    @Test
    public void withStringLiteralInSet(){
        Assertions.assertThat(new QueryBuilder()
                .update()
                .all()
                .from("STALKER")
                .set("$ = 'Strelok'", "name")
                .toString()
        ).isEqualTo("UPDATE STALKER SET name = 'Strelok';");
    }

    @Test
    public void withStringLiteralInWhere(){
        Assertions.assertThat(new QueryBuilder()
                .update()
                .from("STALKER")
                .set("$ = 5", "guns")
                .where("$ = 'Strelok'", "name")
                .toString()
        ).isEqualTo("UPDATE STALKER SET guns = 5 WHERE name = 'Strelok';");
    }

    @Test
    public void SQLInjectionInWhere_shouldReturnErrorMessage(){
        Assertions.assertThat(new QueryBuilder()
                .update()
                .from("STALKER")
                .where("$ = 5; SELECT hacker FROM HACKER", "guns")
                .toString()
        ).isEqualTo("Sosat\'");
    }

    @Test
    public void SQLInjectionInFrom_shouldReturnErrorMessage(){
        Assertions.assertThat(new QueryBuilder()
                .update()
                .all()
                .from("STALKER; SELECT hacker FROM HACKER;")
                .toString()
        ).isEqualTo("Sosat\'");
    }

}

