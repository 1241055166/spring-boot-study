package com.henry.lettuce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class LettuceController {

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/lettuce/get")
    public ResponseEntity get() {
        redisTemplate.opsForValue().set("name", "huang");
        String name = (String) redisTemplate.opsForValue().get("name");
        return ResponseEntity.ok(name);
    }
}