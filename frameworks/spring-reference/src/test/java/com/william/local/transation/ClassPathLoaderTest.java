package com.william.local.transation;import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by william on 17-7-28.
 */
public class ClassPathLoaderTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath*:/*.xml");
        System.out.println(ctx.getClassLoader().getResource("").getPath());
    }
}
