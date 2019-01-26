package com.william.local.transation.classpath;import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by william on 17-7-28.
 */
public class ClassPathLoaderTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:/*.xml");
        System.out.println(ctx.getClassLoader().getResource("").getPath());
    }
}
