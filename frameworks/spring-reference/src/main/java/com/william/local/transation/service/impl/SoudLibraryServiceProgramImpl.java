package com.william.local.transation.service.impl;

import com.william.local.transation.dao.ISoundLibraryDao;
import com.william.local.transation.model.LibraryInfo;
import com.william.local.transation.service.ISoundLibraryService;
import com.william.local.transation.model.IPWhiteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * Created by william.zhang on 2016/2/28.
 */
@Service("soundLibraryService")
public class SoudLibraryServiceProgramImpl implements ISoundLibraryService {

//    @Autowired
//    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private ISoundLibraryDao soundLibraryDao;

    public void createLibrary(LibraryInfo libraryInfo, IPWhiteList ipWhiteList) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        try {
            soundLibraryDao.insertLibraryInfo(libraryInfo);
            ipWhiteList.setLibraryId(libraryInfo.getId());
            System.out.println(2/0);          //用于事务异常测试
            soundLibraryDao.insertIPWhiteList(ipWhiteList);
            txManager.commit(status);
        } catch (RuntimeException e) {
            status.setRollbackOnly();
            txManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    public List<LibraryInfo> getAllLibraries() {
        return soundLibraryDao.selectLibraryInfos();
    }

    public List<IPWhiteList> getIPWhiteListsByLibrary(int libraryId) {
        return soundLibraryDao.selectIPWhiteList(libraryId);
    }
}
