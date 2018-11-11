package ru.lightsoff.database;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightsoff.database.builders.QueryBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestsDeleteQueryBuilder {

    @Test
    public void withGreaterAndLesserThanInWhere(){
        Assertions.assertThat(new QueryBuilder()
                .delete()
                .from("STALKER")
                .where("$ > 5 AND $ < 10", "guns", "artifacts")
                .toString()
        ).isEqualTo("DELETE FROM STALKER WHERE guns > 5 AND artifacts < 10;");
    }

    @Test
    public void withOneEqualInWhere(){
        Assertions.assertThat(new QueryBuilder()
                .delete()
                .from("STALKER")
                .where("$ = 5", "guns")
                .toString()
        ).isEqualTo("DELETE FROM STALKER WHERE guns = 5;");
    }

    @Test
    public void withTwoEqualsInWhere(){
        Assertions.assertThat(new QueryBuilder()
                .delete()
                .from("STALKER")
                .where("$ = 5 AND $ = 10", "guns", "artifacts")
                .toString()
        ).isEqualTo("DELETE FROM STALKER WHERE guns = 5 AND artifacts = 10;");
    }

    @Test
    public void withStringLiteralInWhere(){
        Assertions.assertThat(new QueryBuilder()
                .delete()
                .from("STALKER")
                .where("$ = 'Strelok'", "name")
                .toString()
        ).isEqualTo("DELETE FROM STALKER WHERE name = 'Strelok';");
    }

    @Test
    public void withoutFrom_shouldReturnErrorMessage() {
        Assertions.assertThat(new QueryBuilder()
                .delete()
                .where("$ = 5;", "guns")
                .toString()
        ).isEqualTo("Sosat\'");
    }

    @Test
    public void withoutWhere_shouldReturnErrorMessage() {
        Assertions.assertThat(new QueryBuilder()
                .delete()
                .from("STALKER")
                .toString()
        ).isEqualTo("Sosat\'");
    }

    public void deleteAll(){
        Assertions.assertThat(new QueryBuilder()
                .delete()
                .all()
                .from("STALKER")
                .toString()
        ).isEqualTo("DELETE * FROM STALKER;");
    }

    @Test
    public void SQLInjectionInWhere_shouldReturnErrorMessage(){
        Assertions.assertThat(new QueryBuilder()
                .delete()
                .from("STALKER")
                .where("$ = 5; SELECT hacker FROM HACKER", "guns")
                .toString()
        ).isEqualTo("Sosat\'");
    }

    @Test
    public void SQLInjectionInFrom_shouldReturnErrorMessage(){
        Assertions.assertThat(new QueryBuilder()
                .delete()
                .all()
                .from("STALKER; SELECT hacker FROM HACKER;")
                .toString()
        ).isEqualTo("Sosat\'");
    }
}
