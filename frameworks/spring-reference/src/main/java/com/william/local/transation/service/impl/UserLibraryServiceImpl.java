package com.william.local.transation.service.impl;

import com.william.local.transation.model.IPWhiteList;
import com.william.local.transation.model.LibraryInfo;
import com.william.local.transation.model.User;
import com.william.local.transation.service.ISoundLibraryService;
import com.william.local.transation.service.IUserLibraryService;
import com.william.local.transation.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by william.zhang on 2016/2/28.
 */
@Service("userLibraryService")
public class UserLibraryServiceImpl implements IUserLibraryService {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISoundLibraryService soundLibraryService;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void createUserLibrary() {
        userService.createUser(new User(122, "zhangsanfeng"));

        LibraryInfo libraryInfo = new LibraryInfo("xitan library" + System.currentTimeMillis(), "baidu" + System.currentTimeMillis());
        IPWhiteList ipList = new IPWhiteList("192.168.1.1/24:" + new Date());
        soundLibraryService.createLibrary(libraryInfo, ipList);
        System.out.print(ipList);
    }
}
