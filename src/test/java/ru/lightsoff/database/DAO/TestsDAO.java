package ru.lightsoff.database.DAO;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import com.opentable.db.postgres.junit5.SingleInstancePostgresExtension;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightsoff.database.Entities.Player;
import ru.lightsoff.database.builders.QueryFactory;
import ru.lightsoff.database.configuration.Config;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.awt.*;
import java.util.ArrayList;
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class TestsDAO {
    //Ну корчое в чем суть нужно делать бин датасурс который будет пихаться в плейер дао и автоматически по идее
    //использовать в аннотациях типа @sql

    private Player player = new Player()
                                .withId(999)
                                .withName("ddd")
                                .withInventory(new ArrayList<ArrayList<Integer>>())
                                .withPosition(new Point(1, 2))
                                .withStats(new ArrayList<Integer>())
                                .withUserID(222);

    @Autowired
    PlayerDAO playerDAO;

    @Test
    public void withOneSet()throws Exception{
        playerDAO.insert(player);
    }
}

