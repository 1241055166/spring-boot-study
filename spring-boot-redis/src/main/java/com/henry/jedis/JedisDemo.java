package com.henry.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author henry
 * @Description TODO
 * @createTime 2021年11月24日 12:45:00
 */
public class JedisDemo {
    public static void main(String[] args) {
        pool();
    }

    public static void demo() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("huang", "2673");
        System.out.println(jedis.get("huang"));
        jedis.close();
    }

    public static void pool() {
        JedisPool pool = new JedisPool("127.0.0.1", 6379);
        Jedis jedis = pool.getResource();
        jedis.set("huang", "2673");
        System.out.println(jedis.get("huang"));
        jedis.close();
    }
}
