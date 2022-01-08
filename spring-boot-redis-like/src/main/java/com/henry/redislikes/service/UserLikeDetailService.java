package com.henry.redislikes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henry.redislikes.model.entity.UserLikeDetail;

/**
 * <p>
 * 用户点赞表 服务类
 * </p>
 *
 * @author henry
 * @since 2021-02-25
 */
public interface UserLikeDetailService extends IService<UserLikeDetail> {

    void transLikedFromRedis2DB();

    void transLikedCountFromRedis2DB();
}
