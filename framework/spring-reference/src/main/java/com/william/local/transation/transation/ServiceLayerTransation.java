package com.william.local.transation.transation;

import com.william.local.transation.service.ISoundLibraryService;
import com.william.local.transation.service.IUserLibraryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by william.zhang on 2016/2/28.
 */
@ContextConfiguration(locations = "classpath:application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceLayerTransation {

    @Autowired
    private IUserLibraryService userLibraryService;

    @Test
    public void test() {
        userLibraryService.createUserLibrary();
    }
}
