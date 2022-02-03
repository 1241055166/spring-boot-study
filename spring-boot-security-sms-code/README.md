# spring_security整合手机验证码登录

思路：

1、 用户在输入用户名,账号、图片验证码后点击登陆。那么对于springSceurity首先会进入短信验证码Filter，因为在配置的时候会把它配置在
UsernamePasswordAuthenticationFilter之前,把当前的验证码的信息跟存在session的图片验证码的验证码进行校验。
2、短信验证码通过后,进入 UsernamePasswordAuthenticationFilter 中，根据输入的用户名和密码信息，构造出一个暂时没有鉴权的
UsernamePasswordAuthenticationToken，并将 UsernamePasswordAuthenticationToken 交给 AuthenticationManager 处理。
3、AuthenticationManager 本身并不做验证处理，他通过 for-each 遍历找到符合当前登录方式的一个 AuthenticationProvider，并交给它进行验证处理
，对于用户名密码登录方式，这个 Provider 就是 DaoAuthenticationProvider。
4、在这个 Provider 中进行一系列的验证处理，如果验证通过，就会重新构造一个添加了鉴权的 UsernamePasswordAuthenticationToken，并将这个
token 传回到 UsernamePasswordAuthenticationFilter 中。
5、在该 Filter 的父类 AbstractAuthenticationProcessingFilter 中，会根据上一步验证的结果，跳转到 successHandler 或者是 failureHandler。

# 短信验证码登录流程

1、用户名密码登录有个 UsernamePasswordAuthenticationFilter，我们搞一个SmsAuthenticationFilter，代码粘过来改一改。
2、用户名密码登录需要UsernamePasswordAuthenticationToken，我们搞一个SmsAuthenticationToken，代码粘过来改一改。
3、用户名密码登录需要DaoAuthenticationProvider，我们模仿它也 implenments AuthenticationProvider，叫做 SmsAuthenticationProvider。


# 测试  这里没有接短信验证平台，所以只是在控制台输出验证码
1、获取验证码
localhost:8088/code/sms?mobile=15113582825

2、调用登录接口
localhost:8088/sms/login

参数
mobile   15113582825
smsCode  短信验证码