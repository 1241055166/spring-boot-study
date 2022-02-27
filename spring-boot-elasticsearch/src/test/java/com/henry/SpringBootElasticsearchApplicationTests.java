package com.henry;

import com.alibaba.fastjson.JSONObject;
import com.henry.dao.NBAPlayerDao;
import com.henry.model.NBAPlayer;
import com.henry.service.NBAPlayerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootElasticsearchApplicationTests {

    @Autowired(required = false)
    private NBAPlayerDao nbaPlayerDao;
    @Autowired(required = false)
    private NBAPlayerService nbaPlayerService;

    @Test
    public void selectAll() {

        System.out.println(JSONObject.toJSON(nbaPlayerDao.selectAll()));
    }

    @Test
    public void addPlayer() throws IOException {
        NBAPlayer nbaPlayer = new NBAPlayer();
        nbaPlayer.setId(999);
        nbaPlayer.setDisplayName("henry");
        nbaPlayerService.addPlayer(nbaPlayer,"999");
    }
    @Test
    public void getPlayer() throws IOException{
        Map<String,Object> player=nbaPlayerService.getPlayer("999");
        System.out.println(JSONObject.toJSON(player));
    }
    @Test
    public void updatePlayer()throws IOException{
        NBAPlayer nbaPlayer = new NBAPlayer();
        nbaPlayer.setId(999);
        nbaPlayer.setDisplayName("henry888");
        nbaPlayerService.updatePlayer(nbaPlayer,"999");
    }
    @Test
    public void deletePlayer()throws  IOException{
        nbaPlayerService.deletePlayer("999");
        nbaPlayerService.deleteAllPlayer();
    }

}
