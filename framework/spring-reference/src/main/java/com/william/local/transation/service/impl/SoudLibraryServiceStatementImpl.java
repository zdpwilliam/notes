package com.william.local.transation.service.impl;

import com.william.local.transation.dao.ISoundLibraryDao;
import com.william.local.transation.model.IPWhiteList;
import com.william.local.transation.model.LibraryInfo;
import com.william.local.transation.service.ISoundLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by william.zhang on 2016/2/28.
 */
//@Service("soundLibraryService")
public class SoudLibraryServiceStatementImpl implements ISoundLibraryService {

    @Autowired
    private ISoundLibraryDao soundLibraryDao;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void createLibrary(LibraryInfo libraryInfo, IPWhiteList ipWhiteList) {
        soundLibraryDao.insertLibraryInfo(libraryInfo);
        ipWhiteList.setLibraryId(libraryInfo.getId());
//        System.out.println(2/0);          //用于事务异常测试
        soundLibraryDao.insertIPWhiteList(ipWhiteList);
    }

    public List<LibraryInfo> getAllLibraries() {
        return soundLibraryDao.selectLibraryInfos();
    }

    public List<IPWhiteList> getIPWhiteListsByLibrary(int libraryId) {
        return soundLibraryDao.selectIPWhiteList(libraryId);
    }
}
