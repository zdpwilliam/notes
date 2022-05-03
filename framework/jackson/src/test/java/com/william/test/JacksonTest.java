package com.william.test;

import com.william.test.domain.UserInfo;
import org.junit.Test;

import java.util.Date;

/**
 * Created by zdpwilliam on 17-3-3.
 */
public class JacksonTest {


    @Test
    public void testJackson() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(10001);
        userInfo.setUsername("william");
        userInfo.setCreatedAt(new Date());
    }

}
