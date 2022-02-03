/*创建用户表*/
CREATE TABLE `persistent_logins` (
                                     `username` varchar(64) NOT NULL,
                                     `series` varchar(64) NOT NULL,
                                     `token` varchar(64) NOT NULL,
                                     `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*创建j角色表*/
CREATE TABLE `roles` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `name` varchar(32) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*创建用户关联角色表*/
CREATE TABLE `roles_user` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `rid` int DEFAULT '2',
                              `uid` int DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;


/*这里密码对应的明文 还是123456*/
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `enabled`)
VALUES
    (1, '小小', '小小', 'e10adc3949ba59abbe56e057f20f883e', 1);

/*三种角色*/
INSERT INTO `roles` (`id`, `name`)
VALUES
    (1, '校长'),
    (2, '教师'),
    (3, '学生');

/*小小用户关联了 教师和校长角色*/
INSERT INTO `roles_user` (`id`, `rid`, `uid`)
VALUES
    (1, 2, 1),
    (2, 3, 1);
