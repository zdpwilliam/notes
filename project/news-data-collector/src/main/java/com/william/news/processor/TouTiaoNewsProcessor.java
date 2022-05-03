package com.william.news.processor;

import com.william.news.model.NewsDetailPage;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by zdpwilliam on 2016-04-19.
 */
public class TouTiaoNewsProcessor implements PageProcessor {

    public static final String HOST_DOMAIN = "toutiao.com";
    public static final String HOST_URL = "http://toutiao.com/";

    private Site site;

    public void process(Page page) {
        List<String> all = page.getHtml().xpath("//li[@class='channel-item']/a/@href").all();
        if (CollectionUtils.isNotEmpty(all)) {
            page.addTargetRequests(all);
            page.putField("news", page.getJson().toObject(NewsDetailPage.class));
        } else {
            page.getResultItems().setSkip(true);
        }
    }

    public Site getSite() {
        if (site == null) {
            site = Site.me().setDomain(HOST_DOMAIN).addStartUrl("http://toutiao.com/api/article/recent/?source=2&count=20&category=news_hot&max_behot_time=1461941963&utm_source=toutiao&offset=0&max_create_time=1461859099&_=1462461813335").
                    setSleepTime(2000).
                    setCycleRetryTimes(5).
                    setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
        }
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new TouTiaoNewsProcessor())
                .thread(1)
                .run();
    }
}
