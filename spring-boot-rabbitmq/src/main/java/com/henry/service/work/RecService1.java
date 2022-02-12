package com.henry.service.work;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: huangjw-b
 * @date 2021/12/10
 * @Description:  消费者1（work消息模型）
 */
@Component
public class RecService1 {
    /*队列*/
    public static final String TEST_QUEUE = "work-amqp-queue";

    @RabbitListener(queues = TEST_QUEUE)
    public void t2(Message message, Channel channel){
        try {
            String msg = new String(message.getBody());
            if (msg == null) {
                System.out.println("消息为空");
            }
            System.out.println("消费者1收到=-=" + msg);
            // 能者多劳模式，告诉RabbitMq不要一直向消费者发送消息，而是要等待消费者的确认了前一个消息
            //channel.basicQos(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
