package com.william.local.transation.service;

import com.william.local.transation.model.LibraryInfo;
import com.william.local.transation.model.IPWhiteList;

import java.util.List;

/**
 * Created by william.zhang on 2016/2/28.
 */
public interface ISoundLibraryService {

    /**
     * 创建一个图书馆
     * @param libraryInfo
     * @param ipWhiteList
     */
    public void createLibrary(LibraryInfo libraryInfo, IPWhiteList ipWhiteList);

    /**
     *
     * @return
     */
    public List<LibraryInfo> getAllLibraries();

    /**
     *
     * @param libraryId
     * @return
     */
    public List<IPWhiteList> getIPWhiteListsByLibrary(int libraryId);
}
