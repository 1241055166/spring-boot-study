package com.henry.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author: huangjw-b
 * @date 2021/12/10
 * @Description:  初始化队列
 */
@Component
@Slf4j
public class RabbitMqConfig {
    /*队列*/
    public static final String TEST_QUEUE = "simple_amqp_queue";

    /**   简单队列的配置的开始 */
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

    /**   简单队列的配置的结束 */

    /**  简单队列的配置2      */
    public static final String NEW_USER_TOPIC = "new-user";

    /**
     * 自动创建队列
     */
    @Bean
    public Queue createQueue() {
        return new Queue(NEW_USER_TOPIC, true);
    }

    /**   work消息模型的配置的开始 */

    /*队列*/
    public static final String TEST_WORK_QUEUE = "work-amqp-queue";

    /**
     * 声明队列1
     * @return
     */
    @Bean(TEST_WORK_QUEUE)
    public Queue testWorkQueue1() {
        return new Queue(TEST_WORK_QUEUE, true);
    }

    /**   work消息模型的配置的结束 */



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

    /**   订阅模型-Fanout（广播模式）的配置的结束 */


   /**    订阅模型-Direct（路由模式）的配置的开始 */

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
        return new Queue(TEST_QUEUE_4, true);
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

    /**    订阅模型-Direct（路由模式）的配置的结束 */



    /**    订阅模型-Topic（通配符模式）的配置的开始 */

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

    /**    订阅模型-Topic（通配符模式）的配置的结束 */





}
