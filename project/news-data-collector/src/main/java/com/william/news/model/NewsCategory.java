package com.william.news.model;

import java.util.Date;

/**
 * Created by zdpwilliam on 2016-05-04.
 */
public class NewsCategory {

    private Integer id;
    private String categoryName;
    private String substitutedTitle;
    private String coverPath;
    private Integer modifiable;
    private Date createAt;
    private Date updateAt;

    public NewsCategory() {
    }

    public NewsCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubstitutedTitle() {
        return substitutedTitle;
    }

    public void setSubstitutedTitle(String substitutedTitle) {
        this.substitutedTitle = substitutedTitle;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Integer getModifiable() {
        return modifiable;
    }

    public void setModifiable(Integer modifiable) {
        this.modifiable = modifiable;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "NewsCategory{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", substitutedTitle='" + substitutedTitle + '\'' +
                ", coverPath='" + coverPath + '\'' +
                ", modifiable=" + modifiable +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
