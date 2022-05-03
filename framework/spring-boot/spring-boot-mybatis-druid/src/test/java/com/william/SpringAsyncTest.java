package com.william;

import com.william.service.SpringAsyncService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by william on 17-6-15.
 */
public class SpringAsyncTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-async-context.xml");
        SpringAsyncService asyncService = context.getBean(SpringAsyncService.class);
        asyncService.asyncSayHello();
        System.out.println(asyncService.sayHello("carl") + " outer...");
    }
}
