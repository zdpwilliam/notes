package com.william.jdk1_8.reflact;

import java.lang.reflect.Method;
import java.util.Arrays;
import sun.reflect.ReflectionFactory;

public class ReflectionTest {

  public static void main(String[] args) {
    ReflectionFactory factory = ReflectionFactory.getReflectionFactory();
    Class<?> clazz = User.class;
    try {
      System.out.println(clazz.newInstance());
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    try {
      Class.forName("com.william.jdk1_8.reflact.User");
      Class.forName("com.william.jdk1_8.reflact.Obj");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println(clazz.getClassLoader());
    Method[] methods = clazz.getMethods();
    for (int i = 0; i < methods.length; i++) {
      Method method = methods[i];
      System.out.print(method.getName());
      System.out.println(Arrays.toString(method.getAnnotations()));
      System.out.println(factory.getMethodAccessor(method));
    }
  }
}
