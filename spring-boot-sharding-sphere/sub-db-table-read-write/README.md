# 项目说明

`场景` 在实际开发中，如果数据库压力大我们可以通过 分库分表 的基础上进行 读写分离，来减缓数据库压力。

# 数据库设计

`分库` ms单库分库分为 ms0库 和 ms1库。
`分表` tab_user单表分为tab_user0表 和 tab_user1表。
`读写分离` 数据写入ms0库 和 ms1库，数据读取 sl0库 和 sl1库。

# 主库和从库
ms0和ms1为主库
sl0和sl1为从库

# SQL语句,每个库分表创建 table_user0和table_user1
CREATE TABLE `tab_user0` (
`id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
`name` varchar(64) DEFAULT NULL COMMENT '姓名',
`sex` varchar(32) DEFAULT NULL COMMENT '性别',
`age` int(11) DEFAULT NULL COMMENT '年龄',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`update_time` datetime DEFAULT NULL COMMENT '更新时间',
`status` tinyint(1) DEFAULT NULL COMMENT '是否删除 1删除 0未删除',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 说明
说明 初始数据的时候，这边只有 sl0从库 我插入了一条数据。那是因为我们这个项目中Mysql服务器并没有实现主从部署,这四个库都在同一服务器上，所以
做不到主数据库数据自动同步到从数据库。所以这里在从数据库建一条数据。等下验证的时候，我们只需验证数据是否存入ms0和ms1，数据读取是否在sl0和sl1。


####### 1、批量插入数据
请求接口
```
localhost:8088/save-user
```
我们可以从SQL语句可以看出 master0 和 master1 库中都插入了数据。完成分库分表插入数据。

###### 2、获取数据
localhost:8088/list-user

```mysql
    select *  from tab_user 
```

`结论` 从接口返回的结果可以很明显的看出，数据存储在主库,而数据库的读取在从库。

`注意` ShardingSphere并不支持`CASE WHEN`、`HAVING`、`UNION (ALL)`，`有限支持子查询`。这个官网有详细说明。
