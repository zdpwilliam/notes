package com.william.news.processor;

import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by zdpwilliam on 2016-04-16.
 */

public class TouTiaoCategoryTagProcessor implements PageProcessor {

    public static final String HOST_DOMAIN = "toutiao.com";
    public static final String HOST_URL = "http://toutiao.com/";

    private Site site;

    public Site getSite() {
        if (site == null) {
            site = Site.me().setDomain(HOST_DOMAIN)
                    .setSleepTime(5000)
                    .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
                    .addStartUrl(HOST_URL);
        }
        return site;
    }

    public void process(Page page) {
//        List<String> allCategories = page.getHtml().xpath("//li[@class='channel-item']/a/@href").all();
        List<String> categoryNames = page.getHtml().xpath("//li[@class='channel-item']/a/span/text()").all();
        if (CollectionUtils.isNotEmpty(categoryNames)) {
//            page.putField("categoryURL", allCategories);
            page.putField("categoryNames", categoryNames);
        } else {
            page.getResultItems().setSkip(true);
        }
    }

    public static void main(String[] args) {
        Spider.create(new TouTiaoCategoryTagProcessor())
                .thread(1)
//                .addPipeline(new MyBatisPipline())
                .run();
    }
}
