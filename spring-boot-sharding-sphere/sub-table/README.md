# 项目说明

`场景` 在实际开发中，如果表的数据过大，我们可能需要把一张表拆分成多张表，这里就是通过ShardingSphere实现分表功能，但不分库。

# 数据库设计

这里有个sub_table库，里面的`tab_user`表由一张拆分成3张，分别是`tab_user0`、`tab_user1`、`tab_user2`。

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
我们可以从SQL语句可以看出 tab_user1 和 tab_user2 表插入了两条数据，而 tab_user0 表中插入一条数据。

###### 2、获取数据
localhost:8088/list-user

```mysql
  select *  from tab_user order by id
```


`注意` ShardingSphere并不支持`CASE WHEN`、`HAVING`、`UNION (ALL)`，`有限支持子查询`。这个官网有详细说明。
















