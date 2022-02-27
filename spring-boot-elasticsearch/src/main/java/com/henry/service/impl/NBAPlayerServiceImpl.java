package com.henry.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.henry.dao.NBAPlayerDao;
import com.henry.model.NBAPlayer;
import com.henry.service.NBAPlayerService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName：NBAPlayerServiceImpl
 * @Description：TODO
 * @Author：henry
 * @Date：2021/12/22 10:09 下午
 * @Versiion：1.0
 */
@Service
public class NBAPlayerServiceImpl implements NBAPlayerService {
    @Resource
    private RestHighLevelClient client;
    @Resource
    private NBAPlayerDao nbaPlayerDao;
    private static final String NBA_INDEX = "nba_latest";

    /**
     * 添加
     *
     * @param player 实体类
     * @param id     编号
     * @return
     * @throws IOException
     */
    @Override
    public boolean addPlayer(NBAPlayer player, String id) throws IOException {
        IndexRequest request = new IndexRequest(NBA_INDEX).id(id).source(beanToMap(player));
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSON(response));
        return false;
    }

    /**
     * 修改
     *
     * @param player 实体类
     * @param id     编号
     * @return
     * @throws IOException
     */
    @Override
    public boolean updatePlayer(NBAPlayer player, String id) throws IOException {
        UpdateRequest request = new UpdateRequest(NBA_INDEX, id).doc(beanToMap(player));
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSON(response));
        return true;
    }

    /**
     * 删除
     *
     * @param id 编号
     * @return
     * @throws IOException
     */
    @Override
    public boolean deletePlayer(String id) throws IOException {
        DeleteRequest request = new DeleteRequest(NBA_INDEX, id);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        return true;
    }

    /**
     * 删除全部
     *
     * @return
     * @throws IOException
     */
    @Override
    public boolean deleteAllPlayer() throws IOException {
        DeleteByQueryRequest request = new DeleteByQueryRequest(NBA_INDEX);
        BulkByScrollResponse response = client.deleteByQuery(request, RequestOptions.DEFAULT);
        return true;
    }

    /**
     * 将数据库中的数据全部导入到ES
     *
     * @return
     * @throws IOException
     */
    public boolean importAll() throws IOException {
        List<NBAPlayer> list = nbaPlayerDao.selectAll();
        for (NBAPlayer player : list) {
            addPlayer(player, String.valueOf(player.getId()));
        }
        return true;
    }

    /**
     * 通过名字查找球员
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public List<NBAPlayer> searchMatch(String key, String value) throws IOException {
        SearchRequest request = new SearchRequest(NBA_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(key, value));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(1000);
        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSON(response));
        SearchHit[] hits = response.getHits().getHits();
        List<NBAPlayer> playerList = new ArrayList<>();
        for (SearchHit hit : hits) {
            NBAPlayer player = JSONObject.parseObject(hit.getSourceAsString(), NBAPlayer.class);
            playerList.add(player);
        }
        return playerList;
    }
    /**
     * 通过国家或球队查找球员
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public List<NBAPlayer> searchTerm(String key, String value) throws IOException {
        SearchRequest request = new SearchRequest(NBA_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery(key, value));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(1000);
        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSON(response));
        SearchHit[] hits = response.getHits().getHits();
        List<NBAPlayer> playerList = new ArrayList<>();
        for (SearchHit hit : hits) {
            NBAPlayer player = JSONObject.parseObject(hit.getSourceAsString(), NBAPlayer.class);
            playerList.add(player);
        }
        return playerList;
    }
    /**
     * 通过字母查找球员
     * @param key
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public List<NBAPlayer> searchMatchPrefix(String key, String value) throws IOException {
        SearchRequest request = new SearchRequest(NBA_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.prefixQuery(key, value));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(1000);
        request.source(searchSourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(JSONObject.toJSON(response));
        SearchHit[] hits = response.getHits().getHits();
        List<NBAPlayer> playerList = new ArrayList<>();
        for (SearchHit hit : hits) {
            NBAPlayer player = JSONObject.parseObject(hit.getSourceAsString(), NBAPlayer.class);
            playerList.add(player);
        }
        return playerList;
    }
    /**
     * 根据id查询
     *
     * @param id
     * @return
     * @throws IOException
     */
    @Override
    public Map<String, Object> getPlayer(String id) throws IOException {
        GetRequest getRequest = new GetRequest(NBA_INDEX, id);
        GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
        return response.getSource();
    }

    /**
     * 对象转map
     *
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if (beanMap.get(key) != null) {
                    map.put(key + "", beanMap.get(key));
                }
            }
        }
        return map;
    }
}
