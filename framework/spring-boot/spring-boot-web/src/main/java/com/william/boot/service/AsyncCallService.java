package com.william.boot.service;

public interface AsyncCallService {

    String doSomeWork(String jobName);

    void makeRemoteCallAndUnknownWhenFinish(LongTermTaskCallBack callBack);

}
