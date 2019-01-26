package com.william.test.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zdpwilliam on 17-3-2.
 */
public class UserInfo implements Serializable {

    private Integer id;       //用户（老师，学生，游客）的ID
    private String username;  //老师或学生或游客名称，默认为游客
    private String contact;   //用户联系方式
    private Date createdAt;   //创建时间

    public UserInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", contact='" + contact + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
