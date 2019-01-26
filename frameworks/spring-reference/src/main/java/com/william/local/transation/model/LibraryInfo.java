package com.william.local.transation.model;

import java.util.Date;

/**
 * Created by william.zhang on 2016/2/28.
 */
public class LibraryInfo {

    private Integer id;
    private String libraryName;
    private String libraryBanner;
    private Date createdAt;
    private Date updatedAt;

    public LibraryInfo() {
    }

    public LibraryInfo(String libraryName, String libraryBanner) {
        this.libraryName = libraryName;
        this.libraryBanner = libraryBanner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLibraryBanner() {
        return libraryBanner;
    }

    public void setLibraryBanner(String libraryBanner) {
        this.libraryBanner = libraryBanner;
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
        return "LibraryInfo{" +
                "id=" + id +
                ", libraryName='" + libraryName + '\'' +
                ", libraryBanner='" + libraryBanner + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
