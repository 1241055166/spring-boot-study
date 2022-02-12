package com.henry.service.simple;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: huangjw-b
 * @date 2021/12/10 2:05 下午
 * @Description:  消费者
 */
@Component
public class RecService {

    /*队列*/
    public static final String TEST_QUEUE = "simple_amqp_queue";

    @RabbitListener(queues = TEST_QUEUE)
    public void t2(Message message){
        try {
            String msg = new String(message.getBody());
            if (msg == null) {
                System.out.println("消息为空");
            }
            System.out.println("我收到了=-=" + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
