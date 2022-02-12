package com.henry;

import com.henry.service.RabbitMqService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootRabbitmqApplicationTests {

    @Autowired
    private RabbitMqService rabbitMqService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 简单队列模型
     */
    @Test
    public void tt(){
        String s = "顺丰快递";
        rabbitMqService.sendQueue(s);
    }


    /**
     * work消息模型
     */
    @Test
    public void tt1(){
        for (int i = 0;i < 10; i++){
            String s = "消息" + i;
            rabbitMqService.sendWorkQueue(s);
        }
    }

    /**
     * 订阅模型-Fanout
     */
    @Test
    public void t1(){
        String s = "广播快递";
        rabbitMqService.sendExchange(s,"");
    }

    /**
     * 订阅模型-Direct（路由模式）
     */
    @Test
    public void t2(){
        String s = "京东快递";
        String s1 = "顺丰快递";
        rabbitMqService.sendDirectExchange(s,"JD");
        rabbitMqService.sendDirectExchange(s1,"SF");
    }

    /**
     * 订阅模型-Topic（通配符模式）
     * 队列1只接收顺丰快递，队列2任何快递都接收
     */
    @Test
    public void t3(){
        String s = "EMS快递";
        String s1 = "顺丰快递";
        String s2 = "京东快递";
        rabbitMqService.sendTopicExchange(s,"EMS.kd");
        rabbitMqService.sendTopicExchange(s1,"SF.kd");
        rabbitMqService.sendTopicExchange(s2,"JD.kd");
    }

}
