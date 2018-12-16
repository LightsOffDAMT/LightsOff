//package ru.lightsoff.database.builders;
//
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.lightsoff.database.builders.QueryFactory;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TestsSelectQueryBuilder {
//
//    // Maybe the order of the fields should not matter
//    @Test
//    public void regularRequest() {
//        Assertions.assertThat(QueryFactory
//                .select()
//                .withField("gun")
//                .withField("artifacts")
//                .from("STALKER")
//                .asc("artifacts")
//                .toString()
//        ).isEqualTo("SELECT gun, artifacts FROM STALKER ORDER BY artifacts ASC;");
//
//    }
//
//
//    @Test
//    public void requestWithArrayOfFields() {
//        Assertions.assertThat(QueryFactory
//                .select()
//                .withFields(new ArrayList<>(Arrays.asList("gun", "artifacts")))
//                .from("STALKER")
//                .desc("artifacts")
//                .toString()
//        ).isEqualTo("SELECT gun, artifacts FROM STALKER ORDER BY artifacts DESC;");
//
//    }
//
//    @Test
//    public void withoutFields_shouldReturnErrorMessage(){
//        Assertions.assertThat(QueryFactory
//                .select()
//                .from("STALKER")
//                .toString()
//        ).isEqualTo("Error");
//
//    }
//
//    @Test
//    public void selectAll(){
//        Assertions.assertThat(QueryFactory
//                .select()
//                .all()
//                .from("STALKER")
//                .toString()
//        ).isEqualTo("SELECT * FROM STALKER;");
//
//    }
//
//    @Test
//    public void descendingByPrimaryKey() {
//        Assertions.assertThat(QueryFactory
//                .select()
//                .withPrimaryKey("id")
//                .withField("gun")
//                .from("STALKER")
//                .desc()
//                .toString()
//        ).isEqualTo("SELECT id, gun FROM STALKER ORDER BY id DESC;");
//
//    }
//
//
//    @Test
//    public void ascendingByPrimaryKey() {
//        Assertions.assertThat(QueryFactory
//                .select()
//                .withPrimaryKey("id")
//                .withField("gun")
//                .from("STALKER")
//                .asc()
//                .toString()
//        ).isEqualTo("SELECT id, gun FROM STALKER ORDER BY id ASC;");
//
//    }
//
//
//    @Test
//    public void withoutFrom_shouldReturnErrorMessage() {
//        Assertions.assertThat(QueryFactory
//                .select()
//                .withPrimaryKey("id")
//                .withField("gun")
//                .desc()
//                .toString()
//        ).isEqualTo("Error");
//
//    }
//
//    @Test
//    public void ascWithoutPrimarykey_shouldIgnoreAsc() {
//        Assertions.assertThat(QueryFactory
//                .select()
//                .withField("gun")
//                .from("STALKER")
//                .asc()
//                .toString()
//        ).isEqualTo("SELECT gun FROM STALKER;");
//
//    }
//
//
//    @Test
//    public void descWithoutPrimarykey_shouldIgnoreDesc() {
//        Assertions.assertThat(QueryFactory
//                .select()
//                .withField("gun")
//                .from("STALKER")
//                .desc()
//                .toString()
//        ).isEqualTo("SELECT gun FROM STALKER;");
//    }
//
//    @Test
//    public void SQLInjectionInFrom_shouldReturnErrorMessage(){
//        Assertions.assertThat(QueryFactory
//                .select()
//                .withField("gun")
//                .from("STALKER; SELECT hack FROM HACKER")
//                .desc()
//                .toString()
//        ).isEqualTo("Error");
//
//    }
//
//}
