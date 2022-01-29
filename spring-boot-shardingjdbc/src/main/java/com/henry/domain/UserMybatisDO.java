package com.henry.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @Description：mybatis plus方式实体类
 * @Author：henry
 * @Date：2021/11/16 上午9:47
 * @Versiion：1.0
 */
@TableName("t_user")
public class UserMybatisDO {
    @TableId(value = "id")
    private String id;
    @TableField("user_name")
    private String userName;
    @TableField("age")
    private Integer age;
    @TableField("create_time")
    private Date createTime;
    @TableField("tags")
    private Integer tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTags() {
        return tags;
    }

    public void setTags(Integer tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "UserMybatisDO{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                ", tags=" + tags +
                '}';
    }
}
