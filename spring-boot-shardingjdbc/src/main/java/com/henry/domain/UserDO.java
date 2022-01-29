package com.henry.domain;

import java.util.Date;

/**
 * @Description：mybatis方式实体类
 * @Author：henry
 * @Date：2021/11/16 上午9:47
 * @Versiion：1.0
 */
public class UserDO {
    private String id;
    private String userName;
    private Integer age;
    private Date createTime;
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
        return "UserDO{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", createTime=" + createTime +
                ", tags=" + tags +
                '}';
    }
}
