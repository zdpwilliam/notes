package com.william.boot.service;


import org.springframework.stereotype.Service;


@Service
public class AsyncCallServiceImpl implements AsyncCallService {


    @Override
    public String doSomeWork(String jobName) {
        System.out.println("-------start---------");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------end---------");
        return jobName + " has been done";
    }

    @Override
    public void makeRemoteCallAndUnknownWhenFinish(final LongTermTaskCallBack callBack) {
        System.out.println("完成此任务需要 : " + 5 + " 秒");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callBack.callback("长时间异步调用完成.");
            }
        }).start();
    }
}
