package com.william.jdk1_8.reflact;

import sun.reflect.CallerSensitive;

public class User {
  private Integer id;
  private String  username;
  private Integer age;

  @CallerSensitive
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Permission("admin")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Deprecated
  public Integer getAge() {
    return age;
  }

  @Deprecated
  public void setAge(Integer age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", age=" + age +
        '}';
  }
}
