package com.william.news.pipline;

import com.william.news.dao.ICategoryTagDao;
import com.william.news.dao.INewsDao;
import com.william.news.model.NewsCategory;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zdpwilliam on 2016-05-03.
 */
public class CategoryPipline implements Pipeline {

    @Autowired
    private INewsDao newsDao;

    @Autowired
    private ICategoryTagDao categoryTagDao;

    public void process(ResultItems resultItems, Task task) {
        Map<String, Object> categoriesMap = resultItems.getAll();
        List<NewsCategory> oldCategories = categoryTagDao.selectAllNewsCategories();
        List<String> categoryNames = (List<String>) categoriesMap.get("categoryNames");
        Set<String> oldCategoryNames = extractCategoryNames(oldCategories);
        if(categoryNames != null && categoryNames.size() > 0) {
            for (String categoryName :
                    categoryNames) {
                if(oldCategories.contains(categoryName)) {
                    continue;
                }
                categoryTagDao.addNewsCategory(new NewsCategory(categoryName));
            }
        }
    }

    private Set<String> extractCategoryNames(List<NewsCategory> categories) {
        Set<String> target = new HashSet<String>();
        if(categories != null && categories.size() > 0) {
            for (NewsCategory category :
                    categories) {
                target.add(category.getCategoryName());
            }
        }
        return target;
    }
}
