package com.alltime;

import java.io.Serializable;

/**
 * @Package com.alltime
 * @Description:
 * @Author deepen.zhang
 * @Date 2019-09-26 15:47
 * @Version V1.0
 */
public class Ticket implements Serializable {

    private String  name;
    private Integer age;
    private String  phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }
}
