
############################################################################ 1.简单队列模型
`总结` 生产者将消息发送到队列，消费者从队列中获取消息，队列是存储消息的缓冲区。

### RabbitMqConfig
`````````
package com.henry.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
* @Description:  简单队列模型----初始化队列
*/
  @Component
  @Slf4j
  public class RabbitMqConfig {
  /*队列*/
  public static final String TEST_QUEUE = "simple-amqp_queue";

  /**
    * 声明队列
    * public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete) {
    * this(name, durable, exclusive, autoDelete, (Map)null);
    * }
    * String name: 队列名
    * boolean durable: 持久化消息队列，rabbitmq 重启的时候不需要创建新的队列，默认为 true
    * boolean exclusive: 表示该消息队列是否只在当前的connection生效，默认为 false
    * boolean autoDelete: 表示消息队列在没有使用时将自动被删除，默认为 false
      */
      @Bean(TEST_QUEUE)
      public Queue testQueue() {
      return new Queue(TEST_QUEUE, true);
      }
 }
`````````

## 发送消息类
``````````````
package com.henry.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @Description:  发送消息类
 */
@Component
@Slf4j
public class RabbitMqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*发送消息到队列*/
    public String sendQueue(Object payload){
        return baseSend("", RabbitMqConfig.TEST_QUEUE, payload, null, null);
    }

    /**
     * MQ 公用发送方法
     *
     * @param exchange  交换机
     * @param routingKey  队列
     * @param payload 消息体
     * @param messageId  消息id（唯一性）
     * @param messageExpirationTime  持久化时间
     * @return 消息编号
     */
    public String baseSend(String exchange, String routingKey, Object payload, String messageId, Long messageExpirationTime) {
        /*若为空，则自动生成*/
        if (messageId == null) {
            messageId = UUID.randomUUID().toString();
        }
        String finalMessageId = messageId;
        /*设置消息属性*/
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                /*消息属性中写入消息id*/
                message.getMessageProperties().setMessageId(finalMessageId);
                /*设置消息持久化时间*/
                if (!StringUtils.isEmpty(messageExpirationTime)){
                    message.getMessageProperties().setExpiration(messageExpirationTime.toString());
                }
                /*设置消息持久化*/
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return message;
            }
        };

        /*构造消息体，转换json数据格式*/
        Message message = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(payload);
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentEncoding(MessageProperties.CONTENT_TYPE_JSON);
            message = new Message(json.getBytes(), messageProperties);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        /*表示当前消息唯一性*/
        CorrelationData correlationData = new CorrelationData(finalMessageId);

        /**
         * public void convertAndSend(String exchange, String routingKey, Object message,
         * MessagePostProcessor messagePostProcessor, @Nullable CorrelationData correlationData) throws AmqpException
         * exchange: 路由
         * routingKey: 绑定key
         * message: 消息体
         * messagePostProcessor: 消息属性处理器
         * correlationData: 表示当前消息唯一性
         */
        rabbitTemplate.convertAndSend(exchange, routingKey, message, messagePostProcessor, correlationData);

        return finalMessageId;
    }
}
``````````````

## 测试
````
@SpringBootTest
class SpringBootRabbitmqApplicationTests {

    @Autowired
    private RabbitMqService rabbitMqService;

    /**
     * 简单队列模型
     */
    @Test
    public void tt(){
        String s = "顺丰快递";
        rabbitMqService.sendQueue(s);
    }

}
````

################################################################################# 2 work消息模型
``````
@SpringBootTest
class SpringBootRabbitmqApplicationTests {

    @Autowired
    private RabbitMqService rabbitMqService;

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
}

``````

## 消费者1

```````````````
package com.henry.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
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

```````````````

## 消费者2
```````````````
package com.henry.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
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


```````````````

如果是能者多劳，那么就可以在 RecService1
在消费者添加channel.basicQos(1)即可。这就告诉RabbitMq不要一直向消费者发送消息，而是要等待消费者的确认了前一个消息
channel.basicQos(1);

启动服务后可以看到消费者1消费的多


########################################################################## 3.订阅模型-Fanout（广播模式）

说明：
在这种订阅模式中，生产者发布消息，所有消费者都可以获取所有消息。

### RabbitMqConfig
````````
@Component
@Slf4j
public class RabbitMqConfig {
/*队列*/
public static final String TEST_QUEUE = "simple-amqp_queue";

    /**   订阅模型-Fanout（广播模式）的配置的开始 */
    /*交换机*/
    public static final String TEST_EXCHANGE = "fanout_amqp_exchange";

    /*声明一个fanout交换机*/
    @Bean(TEST_EXCHANGE)
    public Exchange testExchange() {
        // durable(true)持久化，mq重启之后，交换机还在
        return ExchangeBuilder.fanoutExchange(TEST_EXCHANGE).durable(true).build();
    }


    /*队列1*/
    public static final String TEST_QUEUE_1 = "fanout_amqp_queue_1";
    /*队列2*/
    public static final String TEST_QUEUE_2 = "fanout_amqp_queue_2";

    /**声明队列1
     * public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete) {
     *         this(name, durable, exclusive, autoDelete, (Map)null);
     *     }
     * String name: 队列名
     * boolean durable: 持久化消息队列，rabbitmq 重启的时候不需要创建新的队列，默认为 true
     * boolean exclusive: 表示该消息队列是否只在当前的connection生效，默认为 false
     * boolean autoDelete: 表示消息队列在没有使用时将自动被删除，默认为 false*/
    @Bean(TEST_QUEUE_1)
    public Queue testQueue1() {
        return new Queue(TEST_QUEUE_1, true);
    }
    /*声明队列2*/
    @Bean(TEST_QUEUE_2)
    public Queue testQueue2() {
        return new Queue(TEST_QUEUE_2, true);
    }


    /*队列1与路由进行绑定*/
    @Bean
    Binding bindingTest1(@Qualifier(TEST_QUEUE_1) Queue queue,
                         @Qualifier(TEST_EXCHANGE) Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("")
                .noargs();
    }

    /*队列2与路由进行绑定*/
    @Bean
    Binding bindingTest2(@Qualifier(TEST_QUEUE_2) Queue queue,
                         @Qualifier(TEST_EXCHANGE) Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("")
                .noargs();
    }
}
````````
### RabbitMqService添加
`````
/*发送到交换器*/
public String sendExchange(Object payload,String routingKey){
return baseSend(RabbitMqConfig.TEST_EXCHANGE, routingKey, payload, null, null);
}
`````

### 添加测试方法
`````
@Test
public void t1(){
String s = "广播快递";
rabbitMqService.sendExchange(s,"");
}
`````

### 新增RecFanoutService1，RecFanoutService2这两个消费者，监听fanout_amqp_queue_1，fanout_amqp_queue_2这两个队列
，看控制台输出

##########################3####################################### 4     订阅模型-Direct（路由模式）

说明：
在这种订阅模式中， 生产者发布消息，消费者有选择性的接收消息。队列与交换机的绑定，不能是任意绑定了，
而是要指定一个RoutingKey（路由key）。消息的发送方在向Exchange发送消息时，也必须指定消息的routing key

### RabbitMqConfig   修改RabbitMqConfig配置，主要是在交换机与这两个队列进行绑定时候指定routingkey，队列1只接收顺丰快递，队列2只接收京东快递
````````````````
/**
* @Description:  初始化队列
  */
  @Component
  @Slf4j
  public class RabbitMqConfig {
  
/*交换机*/
public static final String TEST_EXCHANGE1 = "direct_amqp_exchange";

    /*声明一个direct交换机*/
    @Bean(TEST_EXCHANGE1)
    public Exchange testExchange1() {
        // durable(true)持久化，mq重启之后，交换机还在
        return ExchangeBuilder.directExchange(TEST_EXCHANGE1).durable(true).build();
    }

    /*队列1*/
    public static final String TEST_QUEUE_3 = "direct_amqp_queue_1";
    /*队列2*/
    public static final String TEST_QUEUE_4 = "direct_amqp_queue_2";

    /**声明队列
     * public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete) {
     *         this(name, durable, exclusive, autoDelete, (Map)null);
     *     }
     * String name: 队列名
     * boolean durable: 持久化消息队列，rabbitmq 重启的时候不需要创建新的队列，默认为 true
     * boolean exclusive: 表示该消息队列是否只在当前的connection生效，默认为 false
     * boolean autoDelete: 表示消息队列在没有使用时将自动被删除，默认为 false*/
    @Bean(TEST_QUEUE_3)
    public Queue testQueue3() {
        return new Queue(TEST_QUEUE_3, true);
    }

    @Bean(TEST_QUEUE_4)
    public Queue testQueue4() {
        return new Queue(TEST_QUEUE_3, true);
    }

    /*队列1路由进行绑定*/
    @Bean
    Binding bindingTest3(@Qualifier(TEST_QUEUE_3) Queue queue,
                         @Qualifier(TEST_EXCHANGE1) Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("SF")
                .noargs();
    }

    /*队列2路由进行绑定*/
    @Bean
    Binding bindingTest4(@Qualifier(TEST_QUEUE_4) Queue queue,
                         @Qualifier(TEST_EXCHANGE1) Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("JD")
                .noargs();
    }
}

````````````````

### RabbitMqService 新增Direct交换器
````````````````
/*发送到交换器*/
public String sendDirectExchange(Object payload,String routingKey){
return baseSend(RabbitMqConfig.TEST_EXCHANGE1, routingKey, payload, null, null);
}
````````````````
### 测试

```
@Test
public void t2(){
String s = "京东快递";
String s1 = "顺丰快递";
rabbitMqService.sendDirectExchange(s,"JD");
rabbitMqService.sendDirectExchange(s1,"SF");
}
```
### 新增相应的队列名再启动，按道理来说消费者1应该收到顺丰快递，消费者2应该收到京东快递,



##################################################################### 5. 订阅模型-Topic（通配符模式）

说明：
Topic类型的Exchange与Direct相比，都是可以根据RoutingKey把消息路由到不同的队列。只不过Topic类型Exchange可以让队列在绑定Routing key 的时候使用通配符！
Routingkey 一般都是有一个或多个单词组成，多个单词之间以”.”分割
通配符规则：
#：匹配一个或多个词
*：匹配不多不少恰好1个词
修改RabbitMqConfig与direct基本一样，只修改了一下队列名和交换机，routingkey改成队列1只接收顺丰快递，队列2任何快递都接收

### RabbitMqConfig
````````````
public class RabbitMqConfig {
/*交换机*/
public static final String TEST_TOPIC_EXCHANGE = "topic_amqp_exchange";

    /*声明一个topic交换机*/
    @Bean(TEST_TOPIC_EXCHANGE)
    public Exchange testTopicExchange() {
        // durable(true)持久化，mq重启之后，交换机还在
        return ExchangeBuilder.topicExchange(TEST_TOPIC_EXCHANGE).durable(true).build();
    }

    /*队列1*/
    public static final String TEST_QUEUE_5 = "topic_amqp_queue_1";
    /*队列2*/
    public static final String TEST_QUEUE_6 = "topic_amqp_queue_2";

    /**声明队列
     * public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete) {
     *         this(name, durable, exclusive, autoDelete, (Map)null);
     *     }
     * String name: 队列名
     * boolean durable: 持久化消息队列，rabbitmq 重启的时候不需要创建新的队列，默认为 true
     * boolean exclusive: 表示该消息队列是否只在当前的connection生效，默认为 false
     * boolean autoDelete: 表示消息队列在没有使用时将自动被删除，默认为 false*/
    @Bean(TEST_QUEUE_5)
    public Queue testQueue5() {
        return new Queue(TEST_QUEUE_5, true);
    }

    @Bean(TEST_QUEUE_6)
    public Queue testQueue6() {
        return new Queue(TEST_QUEUE_6, true);
    }

    /*队列1路由进行绑定*/
    @Bean
    Binding bindingTopicTest1(@Qualifier(TEST_QUEUE_5) Queue queue,
                         @Qualifier(TEST_TOPIC_EXCHANGE) Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("SF.kd")
                .noargs();
    }

    /*队列2路由进行绑定*/
    @Bean
    Binding bindingTopicTest2(@Qualifier(TEST_QUEUE_6) Queue queue,
                         @Qualifier(TEST_TOPIC_EXCHANGE) Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("#.kd")
                .noargs();
    }
}
````````````

### RabbitMqService新增交换器
````
/*发送到Topic交换器*/
public String sendTopicExchange(Object payload,String routingKey){
return baseSend(RabbitMqConfig.TEST_TOPIC_EXCHANGE, routingKey, payload, null, null);
}
````

### 新增topic消费者  RecTopicService1,RecTopicService1

### 测试
````
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
````











