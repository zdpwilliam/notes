package com.william.httpclient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientDemo {

    public static void main(String[] args) {
        HttpClient client = HttpClientBuilder.create()
                .build();
        RequestConfig config = RequestConfig.DEFAULT;
    }
}
