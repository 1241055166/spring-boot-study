package com.henry.redislikes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henry.redislikes.mapper.UserLikeCountMapper;
import com.henry.redislikes.mapper.UserLikeDetailMapper;
import com.henry.redislikes.model.dto.UserLikCountDTO;
import com.henry.redislikes.model.entity.UserLikeCount;
import com.henry.redislikes.model.entity.UserLikeDetail;
import com.henry.redislikes.service.RedisService;
import com.henry.redislikes.service.UserLikeDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户点赞表 服务实现类
 * </p>
 *
 * @author henry
 * @since 2021-02-25
 */
@Service
public class UserLikeDetailServiceImpl extends ServiceImpl<UserLikeDetailMapper, UserLikeDetail> implements UserLikeDetailService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserLikeDetailMapper userLikeDetailMapper;

    @Autowired
    private UserLikeCountMapper userLikeCountMapper;

    /**
     * @author henry
     * @description 同步redis的用户点赞数据到数据库
     * @date 2021/2/25 17:21
     * @Param []
     * @return void
     **/
    @Override
    @Transactional
    public void transLikedFromRedis2DB() {
        List<UserLikeDetail> list = redisService.getLikedDataFromRedis();
        list.stream().forEach(item->{
            UserLikeDetail userLikeDetail = userLikeDetailMapper.selectOne(new LambdaQueryWrapper<UserLikeDetail>()
                    .eq(UserLikeDetail::getLikedUserId, item.getLikedUserId())
                    .eq(UserLikeDetail::getLikedPostId, item.getLikedPostId()));
            if (userLikeDetail == null){
                userLikeDetail = new UserLikeDetail();
                BeanUtils.copyProperties(item, userLikeDetail);
                //没有记录，直接存入
                userLikeDetail.setCreateTime(LocalDateTime.now());
                userLikeDetailMapper.insert(userLikeDetail);
            }else{
                //有记录，需要更新
                userLikeDetail.setStatus(item.getStatus());
                userLikeDetail.setUpdateTime(LocalDateTime.now());
                userLikeDetailMapper.updateById(item);
            }

        });
    }

    /**
     * @author henry
     * @description 同步redis的用户点赞数量到数据库
     * @date 2021/2/25 17:22
     * @Param
     * @return
     **/
    @Override
    @Transactional
    public void transLikedCountFromRedis2DB() {
        List<UserLikCountDTO> list = redisService.getLikedCountFromRedis();
        list.stream().forEach(item->{
            UserLikeCount user = userLikeCountMapper.selectById(item.getKey());
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (user != null){
                Integer likeNum = user.getLikeNum() + item.getValue();
                user.setLikeNum(likeNum);
                //更新点赞数量
                userLikeCountMapper.updateById(user);
            }
        });
    }
}
