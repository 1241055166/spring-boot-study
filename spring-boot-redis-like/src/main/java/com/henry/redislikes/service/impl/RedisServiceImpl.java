package com.henry.redislikes.service.impl;


import com.henry.redislikes.config.LikedStatusEnum;
import com.henry.redislikes.model.dto.UserLikCountDTO;
import com.henry.redislikes.model.entity.UserLikeDetail;
import com.henry.redislikes.service.RedisService;
import com.henry.redislikes.util.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @className: RedisServiceImpl
 * @description: Redis服务实现类
 * @author: henry
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     *  (1）某用户被其他用户点赞的详细记录：MAP_USER_LIKED为键值，被点赞用户id::点赞用户id为filed，1或者0为value
     * （2）某用户被点赞的数量统计：MAP_USER_LIKED_COUNT为键值，被点赞用户id为filed，count为value
     */
    /**
     * 将用户被其他用户点赞的数据存到redis
     */
    @Override
    public void saveLiked2Redis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key, LikedStatusEnum.LIKE.getCode());
    }

    //取消点赞
    @Override
    public void unlikeFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key,LikedStatusEnum.UNLIKE.getCode());
    }


    @Override
    public void deleteLikedFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
    }

    /**
     * 将被点赞用户的数量+1
     */
    @Override
    public void incrementLikedCount(String likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,likedUserId,1);
    }

    /**
     * 将被点赞用户的数量-1
     */
    @Override
    public void decrementLikedCount(String likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedUserId, -1);
    }

    /**
     * 获取Redis中的用户点赞详情记录
     */
    @Override
    public List<UserLikeDetail> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object,Object>> scan = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<UserLikeDetail> list = new ArrayList<>();
        while (scan.hasNext()){
            Map.Entry<Object, Object> entry = scan.next();
            String key = (String) entry.getKey();
            String[] split = key.split("::");
            String likedUserId = split[0];
            String likedPostId = split[1];
            Integer value = (Integer) entry.getValue();
            //组装成 UserLike 对象
            UserLikeDetail userLikeDetail = new UserLikeDetail(likedUserId, likedPostId, value);
            list.add(userLikeDetail);
            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        }
        return list;
    }

    /**
     * 获取Redis中的用户被点赞数量
     */
    @Override
    public List<UserLikCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        List<UserLikCountDTO> list = new ArrayList<>();
        while(cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            String key = (String) map.getKey();
            Integer value = (Integer) map.getValue();
            UserLikCountDTO userLikCountDTO = new UserLikCountDTO(key,value);
            list.add(userLikCountDTO);
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,key);
        }
        return list;
    }
}
