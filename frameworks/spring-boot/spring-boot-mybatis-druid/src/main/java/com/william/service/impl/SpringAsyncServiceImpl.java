package com.william.service.impl;

import com.william.service.SpringAsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by william on 17-6-15.
 */
@Service("springAsyncService")
public class SpringAsyncServiceImpl implements SpringAsyncService {

    public String sayHello(String who) {
        String result = who + " say hello to william!";
        System.out.println(result + " inner ...");
        return result;
    }

    @Async
    public void asyncSayHello() {
        System.out.println("asyncSayHello -------- start");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("asyncSayHello -------- end");
    }
}
