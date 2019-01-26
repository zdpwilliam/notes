package com.william.local.transation.transation;

import com.william.local.transation.model.IPWhiteList;
import com.william.local.transation.model.LibraryInfo;
import com.william.local.transation.service.ISoundLibraryService;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author william.zhang 2016/2/28
 */
@ContextConfiguration(locations = "classpath:application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TransationTest {

    @Autowired
    private ISoundLibraryService soundLibraryService;

    @Test
    public void test() {
        LibraryInfo libraryInfo = new LibraryInfo("xitan library" + System.currentTimeMillis(), "baidu" + System.currentTimeMillis());
        IPWhiteList ipList = new IPWhiteList("192.168.1.1/24:" + new Date());
        soundLibraryService.createLibrary(libraryInfo, ipList);

        System.out.println(libraryInfo);
        System.out.println(soundLibraryService.getIPWhiteListsByLibrary(libraryInfo.getId()));
    }

}
