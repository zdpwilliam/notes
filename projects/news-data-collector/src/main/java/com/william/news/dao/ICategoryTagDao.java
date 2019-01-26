package com.william.news.dao;

import com.william.news.model.NewsCategory;

import java.util.List;

/**
 * Created by zdpwilliam on 2016-05-04.
 */
public interface ICategoryTagDao {

    List<NewsCategory> selectAllNewsCategories();

    void addNewsCategory(NewsCategory newsCategory);
}
