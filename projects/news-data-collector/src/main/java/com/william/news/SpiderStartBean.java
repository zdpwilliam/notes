package com.william.news;

import com.william.news.pipline.CategoryPipline;
import com.william.news.processor.TouTiaoCategoryTagProcessor;
import com.william.news.processor.TouTiaoNewsProcessor;
import org.springframework.beans.factory.InitializingBean;
import us.codecraft.webmagic.Spider;

/**
 * Created by zdpwilliam on 2016-05-05.
 */
public class SpiderStartBean implements InitializingBean {

    public void afterPropertiesSet() throws Exception {
        Spider.create(new TouTiaoNewsProcessor())
                .thread(1)
                .addPipeline(new CategoryPipline())
                .run();

        Spider.create(new TouTiaoCategoryTagProcessor())
                .thread(1)
                .addPipeline(new CategoryPipline())
                .run();
    }
}
