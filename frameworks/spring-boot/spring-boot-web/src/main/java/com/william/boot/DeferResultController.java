package com.william.boot;


import com.william.boot.service.AsyncCallService;
import com.william.boot.service.LongTermTaskCallBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;

@Controller
@EnableAutoConfiguration
@ComponentScan("com.william.boot.service")
public class DeferResultController {

    @Autowired
    private AsyncCallService asyncCallService;

    @RequestMapping("/deferred")
    @ResponseBody
    public DeferredResult<String> home(HttpServletRequest request) {
        final DeferredResult<String> deferredResult = new DeferredResult<String>();
        System.out.println("/deferred 调用！thread id is : " + Thread.currentThread().getId());
        asyncCallService.makeRemoteCallAndUnknownWhenFinish(new LongTermTaskCallBack() {
            @Override
            public void callback(Object result) {
                System.out.println("异步调用执行完成, thread id is : " + Thread.currentThread().getId());
                deferredResult.setResult(result + "deferred success!");
            }
        });
        return deferredResult;
    }

    @RequestMapping(value="/longtimetask", method = RequestMethod.GET)
    @ResponseBody
    public WebAsyncTask longTimeTask(){
        System.out.println("/longtimetask被调用 thread id is : " + Thread.currentThread().getId());
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                System.out.println("callable: start---- " + Thread.currentThread().getId());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("callable: end---- " + Thread.currentThread().getId());
                System.out.println("执行成功 thread id is : " + Thread.currentThread().getId());
                return "longtimetask";
            }
        };
        return new WebAsyncTask(callable);
    }

    @RequestMapping("/callable")
    @ResponseBody
    public Callable<String> callable(HttpServletRequest request, HttpServletResponse response) {
        return new Callable<String>() {
            @Override
            public String call() {
                System.out.println("callable: start---- " + Thread.currentThread().getId());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("callable: end---- " + Thread.currentThread().getId());
                return "hello callable";
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(DeferResultController.class, args);
    }
}
