package com.henry.redislikes.model.dto;

import lombok.Data;

/**
 * @className: UserLikeDTO
 * @description: 用户点赞
 * @author: henry
 */
@Data
public class UserLikeDetailDTO {

    /**
     * 被点赞的用户id
     */
    private String likedUserId;

    /**
     * 点赞的用户id
     */
    private String likedPostId;

    /**
     * 点赞状态，0取消，1点赞
     */
    private Integer status;
}
