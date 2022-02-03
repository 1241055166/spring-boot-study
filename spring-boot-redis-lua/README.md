# 项目用到的技术
SpringBoot2.3.0 + Maven +Redis +lombok(插件)+Stopwatch(计时工具)

# 主要目的
通过Lua脚本批量插入数据到布隆过滤器

#### 1对以下方法进行性能测试比较
1）、List的 contains 方法
2）、Map的 containsKey 方法
3）、Google布隆过滤器 mightContain 方法

#### 2、SpringBoot整合Redis布隆过滤器

实现通过Lua脚本批量插入数据到redis布隆过滤器

并判断当前key值在redis布隆过滤器中是否存在。

