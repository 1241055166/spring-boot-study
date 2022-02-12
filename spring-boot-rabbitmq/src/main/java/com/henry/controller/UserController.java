package com.henry.controller;

import com.alibaba.fastjson.JSON;
import com.henry.config.RabbitMqConfig;
import com.henry.domain.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author: huangjw-b
 * @date 2021/12/10 6:22 上午
 * @Description:
 */
@RestController
public class UserController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 普通消息
     * http://localhost:8080/add_user
     */
    @GetMapping("/add_user")
    public Object add() {
        try {
            Long id = Long.valueOf(new Random().nextInt(1000));
            User user = User.builder().id(id).userName("TomGE").age(29).address("上海").build();
            byte[] useByte = JSON.toJSONString(user).getBytes(StandardCharsets.UTF_8);

            // 指定消息类型
            MessageProperties props = MessagePropertiesBuilder.newInstance()
                    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).build();

            rabbitTemplate.send(RabbitMqConfig.NEW_USER_TOPIC, new Message(useByte, props));

            return "消息发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "消息发送失败";
        }
    }
}
