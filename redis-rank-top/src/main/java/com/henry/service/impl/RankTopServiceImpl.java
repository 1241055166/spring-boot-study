package com.henry.service.impl;

import com.henry.service.RankTopService;
import com.henry.utils.RedisService;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Henry
 * @Date: 2021/10/18 下午10:10
 * @Description:  排行榜
 */
@Service
public class RankTopServiceImpl implements RankTopService {
    @Resource
    private RedisService redisService;

    @Override
    public Map<String, Object> incrementScore(String newTitle, double score) {
        Map<String, Object> map = new HashMap<>();
        //String key= "newsSta:"+DateUtils.dateToString(new Date(),"yyyyMMdd");
        String key = "newsSta:" + "20210123";
        Double d = redisService.incrementScore(key, newTitle, score);
        Map<String, Object> m = new HashMap<String, Object>() {
            {
                put("key", key);
                put("newTitle", newTitle);
                put("score", d);
            }
        };
        map.put("data", m);
        map.put("code", 0);
        return map;
    }

    @Override
    public Map<String, Object> findNewByNewTitle(String newTitle) {
        //String key= "newsSta:"+DateUtils.dateToString(new Date(),"yyyyMMdd");
        String key = "newsSta:" + "20210123";
        Double d = redisService.score(key, newTitle);
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> m = new HashMap<String, Object>() {
            {
                put("key", key);
                put("newTitle", newTitle);
                put("score", d);
            }
        };
        map.put("data", m);
        map.put("code", 0);
        return map;
    }

    /**
     * @param startPage
     * @param endPage
     * @return
     */
    @Override
    public Map<String, Object> createNewsTop(int startPage, int endPage) {
        Map<String, Object> map = new HashMap<>();
        //新闻统计键
        //String newsStaKey= "newsSta:"+DateUtils.dateToString(new Date(),"yyyyMMdd");
        String newsStaKey = "newsSta:" + "20210123";
        //新闻前30排名键
        //String newsTopKey= "newsSta:"+DateUtils.dateToString(new Date(),"yyyyMMdd");
        String newsTopKey = "newsTop:" + "20210123";
        //查询前30的信息（Interface Comparable<T> ：该接口对实现它的每个类的对象强加一个整体排序。）
        Set<ZSetOperations.TypedTuple<Object>> set = redisService.reverseRangeWithScores(newsStaKey, startPage, endPage);
        if (set == null || set.size() == 0) {
            map.put("data", null);
            map.put("code", 1);
            return map;
        }
        //删除旧的新闻排行榜
        redisService.del(newsTopKey);
        //添加新闻排行榜数据
        Long zsetSize = redisService.zsetAdd(newsTopKey, set);
        Map<String, Object> m = new HashMap<String, Object>() {
            {
                put("data", set);
                put("size", zsetSize);
            }
        };
        map.put("data", m);
        map.put("code", 0);
        return map;
    }

    /**
     * 查看新闻热榜（TOP30）
     *
     * @param startPage
     * @param endPage
     * @return
     */
    @Override
    public Map<String, Object> newsTop(int startPage, int endPage) {
        //新闻统计键
        //String newsStaKey= "newsSta:"+DateUtils.dateToString(new Date(),"yyyyMMdd");
        String newsStaKey = "newsSta:" + "20210123";
        //新闻前30排名键
        //String newsTopKey= "newsSta:"+DateUtils.dateToString(new Date(),"yyyyMMdd");
        String newsTopKey = "newsTop:" + "20210123";
        Set<ZSetOperations.TypedTuple<Object>> set = redisService.reverseRangeWithScores(newsTopKey, startPage, endPage);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("data", set);
        m.put("size", set.size());
        //新闻排行榜为空，也许现在正在添加数据，先查询新闻统计键。
        if (set == null || set.size() == 0) {
            //查询前30的信息（Interface Comparable<T> ：该接口对实现它的每个类的对象强加一个整体排序。）
            Set<ZSetOperations.TypedTuple<Object>> set2 = redisService.reverseRangeWithScores(newsStaKey, startPage, endPage);
            m.put("data", set);
            m.put("size", set.size());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", m);
        map.put("code", 0);
        return map;
    }
}