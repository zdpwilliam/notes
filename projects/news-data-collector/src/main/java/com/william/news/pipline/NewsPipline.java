package com.william.news.pipline;

import com.william.news.dao.ICategoryTagDao;
import com.william.news.dao.INewsDao;
import com.william.news.model.News;
import com.william.news.model.NewsCategory;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zdpwilliam on 2016-05-03.
 */
public class NewsPipline implements Pipeline {

    @Autowired
    private INewsDao newsDao;

    @Autowired
    private ICategoryTagDao categoryTagDao;

    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> categoriesMap = resultItems.getAll();
        List<NewsCategory> oldCategories = categoryTagDao.selectAllNewsCategories();
        News news = News.class.cast(categoriesMap.get("news"));
        List<News> newsList = new ArrayList<News>();
        newsList.add(news);
        newsDao.insertLatestNewsToRepo(newsList);
    }
}
