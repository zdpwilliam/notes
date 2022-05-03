package com.william.news.model;

import java.util.Date;

/**
 * Created by zdpwilliam on 2016-05-04.
 */
public class News {
    private Integer id;         //新闻信息ID
    private String title;       //新闻标题
    private String subTitle;    //新闻副或人工配置标题
    private Integer categoryId; //所属分类ID
    private Date newsTime;      //新闻的时间
    private String tagId;       //所属标签ID列表
    private String shortIntro;  //新闻简介
    private String content;     //新闻内容
    private String cover;       //新闻封面图
    private String source;      //新闻出处来源
    private Date createdAt;    //新闻创建时间
    private Date updatedAt;    //新闻更新时间

    public News() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Date getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(Date newsTime) {
        this.newsTime = newsTime;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", categoryId=" + categoryId +
                ", newsTime=" + newsTime +
                ", tagId='" + tagId + '\'' +
                ", shortIntro='" + shortIntro + '\'' +
                ", content='" + content + '\'' +
                ", cover='" + cover + '\'' +
                ", source='" + source + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
