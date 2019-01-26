package com.william.local.transation.dao;

import java.util.List;


import com.william.local.transation.model.LibraryInfo;
import com.william.local.transation.model.IPWhiteList;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author william.zhang
 *
 */
public interface ISoundLibraryDao {
	
	List<IPWhiteList> selectIPWhiteList(@Param("libraryId") int libraryId);
	
	void insertIPWhiteList(IPWhiteList ipWhiteList);
	
	void updateIPWhiteList(IPWhiteList ipWhiteList);

	List<LibraryInfo> selectLibraryInfos();

	void insertLibraryInfo(LibraryInfo libraryInfo);

	void updateLibraryInfo(LibraryInfo libraryInfo);
}
