//package ru.lightsoff.database.builders;
//
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Profile;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.lightsoff.database.builders.QueryFactory;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Profile("test")
//public class TestsUpdateQueryBuilder {
//
//    @Test
//    public void withOneSet(){
//        Assertions.assertThat(QueryFactory
//                .update()
//                .from("STALKER")
//                .set("$ = 5", "guns")
//                .where("$ = 10", "artifacts")
//                .toString()
//        ).isEqualTo("UPDATE STALKER SET guns = 5 WHERE artifacts = 10;");
//    }
//
//    @Test
//    public void withTwoSets(){
//        Assertions.assertThat(QueryFactory
//                .update()
//                .from("STALKER")
//                .set("$ = 5, $ = 7", "guns", "bottles")
//                .where("$ = 10", "artifacts")
//                .toString()
//        ).isEqualTo("UPDATE STALKER SET guns = 5, bottles = 7 WHERE artifacts = 10;");
//    } @Test
//
//    public void withGreaterAndLesserThanInWhere(){
//        Assertions.assertThat(QueryFactory
//                .update()
//                .from("STALKER")
//                .set("$ = 5", "guns")
//                .where("$ < 10", "artifacts")
//                .toString()
//        ).isEqualTo("UPDATE STALKER SET guns = 5 WHERE artifacts < 10;");
//    }
//
//    @Test
//    public void withOneEqualInWhere(){
//        Assertions.assertThat(QueryFactory
//                .update()
//                .set("$ = 5", "guns")
//                .from("STALKER")
//                .where("$ = 10", "artifacts")
//                .toString()
//        ).isEqualTo("UPDATE STALKER SET guns = 5 WHERE artifacts = 10;");
//    }
//
//    @Test
//    public void withTwoEqualsInWhere(){
//        Assertions.assertThat(QueryFactory
//                .update()
//                .from("STALKER")
//                .set("$ = 5", "guns")
//                .where("$ = 5 AND $ = 10", "guns", "artifacts")
//                .toString()
//        ).isEqualTo("UPDATE STALKER SET guns = 5 WHERE guns = 5 AND artifacts = 10;");
//    }
//
//    @Profile("test")
//    @Test
//    public void withoutFrom_shouldReturnErrorMessage() {
//        Assertions.assertThat(QueryFactory
//                .update()
//                .set("$ = 5", "guns")
//                .where("$ = 5", "guns")
//                .toString()
//        ).isEqualTo("Error");
//    }
//
//    @Profile("test")
//    @Test
//    public void withoutWhere_shouldReturnErrorMessage() {
//        Assertions.assertThat(QueryFactory
//                .update()
//                .from("STALKER")
//                .set("$ = 5", "guns")
//                .toString()
//        ).isEqualTo("Error");
//    }
//
//    @Profile("test")
//    @Test
//    public void withoutSet_shouldReturnErrorMessage() {
//        Assertions.assertThat(QueryFactory
//                .update()
//                .from("STALKER")
//                .where("$ = 5", "guns")
//                .toString()
//        ).isEqualTo("Error");
//    }
//
//    @Test
//    public void updateAll(){
//        Assertions.assertThat(QueryFactory
//                .update()
//                .all()
//                .from("STALKER")
//                .set("$ = 5", "guns")
//                .toString()
//        ).isEqualTo("UPDATE STALKER SET guns = 5;");
//    }
//
//    @Test
//    public void withStringLiteralInSet(){
//        Assertions.assertThat(QueryFactory
//                .update()
//                .all()
//                .from("STALKER")
//                .set("$ = 'Strelok'", "name")
//                .toString()
//        ).isEqualTo("UPDATE STALKER SET name = 'Strelok';");
//    }
//
//    @Test
//    public void withStringLiteralInWhere(){
//        Assertions.assertThat(QueryFactory
//                .update()
//                .from("STALKER")
//                .set("$ = 5", "guns")
//                .where("$ = 'Strelok'", "name")
//                .toString()
//        ).isEqualTo("UPDATE STALKER SET guns = 5 WHERE name = 'Strelok';");
//    }
//
//    @Profile("test")
//    @Test
//    public void SQLInjectionInWhere_shouldReturnErrorMessage(){
//        Assertions.assertThat(QueryFactory
//                .update()
//                .from("STALKER")
//                .where("$ = 5; SELECT hacker FROM HACKER", "guns")
//                .toString()
//        ).isEqualTo("Error");
//    }
//
//    @Profile("test")
//    @Test
//    public void SQLInjectionInFrom_shouldReturnErrorMessage(){
//        Assertions.assertThat(QueryFactory
//                .update()
//                .all()
//                .from("STALKER; SELECT hacker FROM HACKER;")
//                .toString()
//        ).isEqualTo("Error");
//    }
//
//}
//
