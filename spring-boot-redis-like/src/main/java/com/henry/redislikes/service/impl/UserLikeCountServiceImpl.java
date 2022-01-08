package com.henry.redislikes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.henry.redislikes.mapper.UserLikeCountMapper;
import com.henry.redislikes.model.entity.UserLikeCount;
import com.henry.redislikes.service.UserLikeCountService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author henry
 * @since 2021-02-26
 */
@Service
public class UserLikeCountServiceImpl extends ServiceImpl<UserLikeCountMapper, UserLikeCount> implements UserLikeCountService {

}
