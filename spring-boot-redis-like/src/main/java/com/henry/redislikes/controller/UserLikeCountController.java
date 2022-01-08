package com.henry.redislikes.controller;


import com.henry.redislikes.model.dto.UserLikeDetailDTO;
import com.henry.redislikes.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author henry
 * @since 2021-02-26
 */
@RestController
@RequestMapping("/userInfo")
public class UserLikeCountController {

    @Autowired
    private RedisService redisService;

    @PostMapping("likes")
    public void likes(@RequestBody UserLikeDetailDTO userLikeDetailDTO){
        redisService.saveLiked2Redis(userLikeDetailDTO.getLikedUserId(), userLikeDetailDTO.getLikedPostId());
        redisService.incrementLikedCount(userLikeDetailDTO.getLikedUserId());
    }

}
