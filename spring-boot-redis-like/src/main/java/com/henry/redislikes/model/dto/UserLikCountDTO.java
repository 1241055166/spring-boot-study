package com.henry.redislikes.model.dto;

import lombok.Data;

/**
 * @className: LikedCountDTO
 * @description: 点赞dto
 * @author: henry
 */
@Data
public class UserLikCountDTO {

    private String key;
    private Integer value;

    public UserLikCountDTO(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
