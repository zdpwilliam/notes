package com.william.news.dao;

import com.william.news.model.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zdpwilliam on 2016-05-04.
 */
public interface INewsDao {

    void insertLatestNewsToRepo(List<News> newsList);

    List<News> selectNewsByCategoryId(@Param("categoryId") int categoryId);
}
