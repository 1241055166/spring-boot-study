package com.henry.service.work;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: huangjw-b
 * @date 2021/12/10
 * @Description: 消费者2（work消息模型）
 */
@Component
public class RecService2 {
    /*队列*/
    public static final String TEST_QUEUE = "work-amqp-queue";

    @RabbitListener(queues = TEST_QUEUE)
    public void t2(Message message){
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
