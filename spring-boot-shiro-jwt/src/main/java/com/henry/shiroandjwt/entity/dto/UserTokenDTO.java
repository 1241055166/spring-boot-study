package com.henry.shiroandjwt.entity.dto;

import lombok.Data;

@Data
public class UserTokenDTO {

    private Long id;
    private String username;
    private String password;
    private String token;
}
