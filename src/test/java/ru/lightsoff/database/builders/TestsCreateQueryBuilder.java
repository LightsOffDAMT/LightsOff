package ru.lightsoff.database.builders;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightsoff.database.builders.ColumnType;
import ru.lightsoff.database.builders.QueryFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestsCreateQueryBuilder {

    @Test
    public void regularCreate() {
        Assertions.assertThat(QueryFactory
                .create()
                .withName("STALKER")
                .withField()
                    .name("guns")
                    .type(ColumnType.INTEGER)
                    .constraint("PRIMARY KEY")
                .and()
                .withField()
                    .name("artifacts")
                    .type(ColumnType.TEXT)
                    .constraint("NOT NULL")
                .and()
                .toString()
        ).isEqualTo("CREATE TABLE STALKER (\n" +
                "\tguns\tinteger\tPRIMARY KEY,\n" +
                "\tartifacts\ttext\tNOT NULL\n" +
                ");");
    }

    @Test
    public void withAllTypesOfFields() {
        Assertions.assertThat(QueryFactory
                .create()
                .withName("STALKER")
                .withField()
                    .name("guns1")
                    .type(ColumnType.INTEGER)
                    .constraint("PRIMARY KEY")
                .and()
                .withField()
                    .name("guns2")
                    .type(ColumnType.TEXT)
                .and()
                .withField()
                .name("guns3")
                    .type(ColumnType.SERIAL)
                .and()
                .withField()
                .name("guns4")
                    .type(ColumnType.DATE)
                .and()
                .withField()
                    .name("guns5")
                    .type(ColumnType.INTERVAL)
                .and()
                .toString()
        ).isEqualTo("CREATE TABLE STALKER (\n" +
                "\tguns1\tinteger\tPRIMARY KEY,\n" +
                "\tguns2\ttext,\n" +
                "\tguns3\tserial,\n" +
                "\tguns4\tdate,\n" +
                "\tguns5\tinterval\n" +
                ");");

    }

}
