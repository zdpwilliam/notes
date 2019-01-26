package com.william;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by william on 17-3-30.
 */
public class UploadDemo {

    private static String endpoint = "*";
    private static String accessKeyId = "*";
    private static String accessKeySecret = "*";

    private static String bucketName = "zm-client";
    private static String key = "image_"/*+ new Date().getTime() + "_"*/;
    private static String uploadFile = "yiruma.png";

    public static void main(String[] args) throws IOException {
//        putOperation();
        getOperation();
    }

    private static void putOperation() throws IOException {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        InputStream inputStream = new FileInputStream("/home/william/图片/wallpaper/yiruma.png");
        PutObjectResult result = ossClient.putObject(bucketName, key + uploadFile, inputStream);
        System.out.println("result: " + result.getETag());
        System.out.println("result: " + result.getRequestId());
        ossClient.shutdown();
    }

    private static void getOperation() throws IOException {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        OSSObject result = ossClient.getObject(bucketName, key + uploadFile);
        System.out.println("result: " + result.getRequestId());
        System.out.println("result: " + result.getObjectMetadata().getContentType());
        ossClient.shutdown();
    }
}
