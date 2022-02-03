# 项目说明

`场景` 在实际开发中，如果表的数据过大我们需要把一张表拆分成多张表，也可以垂直切分把一个库拆分成多个库，这里就是通过ShardingSphere实现分库分表功能。

# 数据库设计

`分库` ds一个库分为 ds0库 和 ds1库。
`分表` tab_user一张表分为 tab_user0表 和 tab_user1表。

# SQL语句
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



####### 1、批量插入数据
请求接口
```
localhost:8088/save-user
```
我们可以从SQL语句可以看出 ds0 和 ds1 库中都插入了数据。完成分库分表插入数据。

###### 2、获取数据
localhost:8088/list-user

```mysql
  select *  from tab_user order by age  ### 根据年龄排序
```


`注意` ShardingSphere并不支持`CASE WHEN`、`HAVING`、`UNION (ALL)`，`有限支持子查询`。这个官网有详细说明。
















