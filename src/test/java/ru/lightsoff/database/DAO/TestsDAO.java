package ru.lightsoff.database.DAO;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lightsoff.database.Entities.Player;
import ru.lightsoff.database.builders.QueryFactory;

import javax.sql.DataSource;
import java.awt.*;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestsDAO {

    private Player player = new Player().withId(999).withName("ddd").withInventory(new ArrayList<ArrayList<Integer>>()).withPosition(new Point(1, 2)).withStats(new ArrayList<Integer>()).withUserID(222);

    @Autowired
    PlayerDAO playerDAO;

    @Test
    public void withOneSet(){
        playerDAO.insert(player);
    }
}
