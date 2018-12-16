//package ru.lightsoff.database.builders;
//
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
//public class TestsDeleteQueryBuilder {
//
//    @Test
//    public void withGreaterAndLesserThanInWhere(){
//        Assertions.assertThat(QueryFactory
//                .delete()
//                .from("STALKER")
//                .where("$ > 5 AND $ < 10", "guns", "artifacts")
//                .toString()
//        ).isEqualTo("DELETE FROM STALKER WHERE guns > 5 AND artifacts < 10;");
//    }
//
//    @Test
//    public void withOneEqualInWhere(){
//        Assertions.assertThat(QueryFactory
//                .delete()
//                .from("STALKER")
//                .where("$ = 5", "guns")
//                .toString()
//        ).isEqualTo("DELETE FROM STALKER WHERE guns = 5;");
//    }
//
//    @Test
//    public void withTwoEqualsInWhere(){
//        Assertions.assertThat(QueryFactory
//                .delete()
//                .from("STALKER")
//                .where("$ = 5 AND $ = 10", "guns", "artifacts")
//                .toString()
//        ).isEqualTo("DELETE FROM STALKER WHERE guns = 5 AND artifacts = 10;");
//    }
//
//    @Test
//    public void withStringLiteralInWhere(){
//        Assertions.assertThat(QueryFactory
//                .delete()
//                .from("STALKER")
//                .where("$ = 'Strelok'", "name")
//                .toString()
//        ).isEqualTo("DELETE FROM STALKER WHERE name = 'Strelok';");
//    }
//
//    @Profile("test")
//    @Test
//    public void withoutFrom_shouldReturnErrorMessage() {
//        Assertions.assertThat(QueryFactory
//                .delete()
//                .where("$ = 5;", "guns")
//                .toString()
//        ).isEqualTo("Error");
//    }
//
//    @Profile("test")
//    @Test
//    public void withoutWhere_shouldReturnErrorMessage() {
//        Assertions.assertThat(QueryFactory
//                .delete()
//                .from("STALKER")
//                .toString()
//        ).isEqualTo("Error");
//    }
//
//    public void deleteAll(){
//        Assertions.assertThat(QueryFactory
//                .delete()
//                .all()
//                .from("STALKER")
//                .toString()
//        ).isEqualTo("DELETE * FROM STALKER;");
//    }
//
//    @Profile("test")
//    @Test
//    public void SQLInjectionInWhere_shouldReturnErrorMessage(){
//        Assertions.assertThat(QueryFactory
//                .delete()
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
//                .delete()
//                .all()
//                .from("STALKER; SELECT hacker FROM HACKER;")
//                .toString()
//        ).isEqualTo("Error");
//    }
//}
