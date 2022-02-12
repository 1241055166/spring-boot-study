package com.henry.service.direct;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: huangjw-b
 * @date 2021/12/10
 * @Description: 消费者1（订阅模型-Direct（路由模式））
 */
@Component
public class RecDirectService2 {
    /*队列*/
    public static final String TEST_QUEUE = "direct_amqp_queue_2";

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
