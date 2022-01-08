package com.henry.securityjwt.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @className: JwtTest
 * @description: TODO
 * @author: henry
 */
public class JwtTest {

    private String privateKey = "/Users/huangjiawen/code/spring-boot-study/spring-boot-security-jwt/src/main/java/com/henry/jwt/id_key_rsa";

    private String publicKey = "/Users/huangjiawen/code/spring-boot-study/spring-boot-security-jwt/src/main/java/com/henry/jwt/id_key_rsa.pub";

    @Test
    public void test1() throws Exception{
        RsaUtils.generateKey(publicKey,privateKey,"dpb",1024);
    }


    /**
     * 密码加密
     * 在注册时，调用bCryptPasswordEncoder.encode方法给密码加密
     */
    @Test
    public void test2(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123456"));

        //PasswordEncoder passwordEncoder = new PasswordEncoder() ;
    }

}
