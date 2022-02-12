package com.henry.service.simple;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author: huangjw-b
 * @date 2021/12/10 6:17 上午
 * @Description:
 */
@Component
public class SimpleConsumer {

    public static final String NEW_USER_TOPIC = "new-user";

    @RabbitListener(queues = NEW_USER_TOPIC)
    public void onMessage(Message message, Channel channel) throws Exception {
        String s = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("消费内容 : " + s);
        // 提交消息ack
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
