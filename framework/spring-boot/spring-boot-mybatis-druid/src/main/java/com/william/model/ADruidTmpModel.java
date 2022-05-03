package com.william.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by william on 17-7-24.
 */
public class ADruidTmpModel implements Serializable {
    private static final long serialVersionUID = -2335917943775064003L;

    private Integer id;
    private String name;
    private String role;
    private Date createdAt;

    public ADruidTmpModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ADruidTmpModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
