package com.william.jdk1_8.reflact;


public class CallerSensitive {

  public static void main(String[] args) {
    try {
      Class.forName("com.william.jdk1_8.reflact.User");
      Class.forName("com.william.jdk1_8.reflact.Obj");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
