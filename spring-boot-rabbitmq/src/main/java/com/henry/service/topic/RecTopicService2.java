package com.henry.service.topic;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: huangjw-b
 * @date 2021/12/10 2:08 下午
 * @Description: 消费者1（订阅模型-Topic（通配符模式））
 */
@Component
public class RecTopicService2 {
    /*队列*/
    public static final String TEST_QUEUE = "topic_amqp_queue_1";

    @RabbitListener(queues = TEST_QUEUE)
    public void t2(Message message, Channel channel){
        try {
            String msg = new String(message.getBody());
            if (msg == null) {
                System.out.println("消息为空");
            }
            System.out.println("消费者2收到=-=" + msg);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
