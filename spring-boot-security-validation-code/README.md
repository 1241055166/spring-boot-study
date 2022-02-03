springboot整合spring-security实现验证码登录以及记住我的功能

我整理下springSceurity整合图形验证码的大致思路：

1、首先对于验证码本身而言,应该有三部分组成 1、存放验证码的背景图片 2、验证码 3、验证码的有效时间。
2、对于springSceurity而言,验证码的执行校验顺序肯定是在UsernamePasswordAuthenticationFilter之前的，因为如果验证码都不对，那么
根本都不需要验证账号密码。所以我们需要自定义一个验证码过滤器，并且配置在UsernamePasswordAuthenticationFilter之前执行。
3、对于获取验证码的接口，肯定是不需要进行认证限制的。
4、对于获取验证码的接口的时候，需要把该验证码信息+当前浏览器的SessonId绑定在一起存在Seesion中,为了后面校验的时候通过SessonId
去取这个验证码信息。
5、登陆请求接口，除了带上用户名和密码之外，还需要带上验证码信息。在进入验证码过滤器的时候,首先通过SessonId获取存在Sesson中的
验证码信息，拿到验证码信息之后首先还要校验该验证码是否在有效期内。之后再和当前登陆接口带来的验证码进行对比，如果一致，那么当前
验证码这一关就过了，就开始验证下一步账号和密码是否正确了。
# 验证码登录

1、先获取验证码
localhost:8088/code/image

2、调用登录接口
localhost:8088/login

参数 username   小小
    password   123456
    remember-me  true
    imageCode   验证码

# 记住我功能
1、先获取验证码
localhost:8088/code/image

2、调用登录接口
localhost:8088/login

参数 username   小小
password   123456
remember-me  true
imageCode   验证码

3、关闭服务，然后重启
localhost:8088/no-authorize