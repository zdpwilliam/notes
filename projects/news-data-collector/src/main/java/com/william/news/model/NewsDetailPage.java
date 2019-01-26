package com.william.news.model;

import java.util.List;

/**
 * Created by zdpwilliam on 2016-05-05.
 */
public class NewsDetailPage {
    private String message;
    private List<NewsDetail> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<NewsDetail> getData() {
        return data;
    }

    public void setData(List<NewsDetail> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NewsDetailPage{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class NewsDetail {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "NewsDetail{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }
}
