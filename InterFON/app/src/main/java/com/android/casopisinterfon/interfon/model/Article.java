package com.android.casopisinterfon.interfon.model;


import java.util.List;
import java.util.Random;

/**
 * Model representing one article
 */
public class Article {

    private String pictureLink;
    private String articleTitle;
    private String articleDescription;
    private String articleDate;
    private String articleLink;
    private boolean articleIsBookmarked;
    private long id;
    private List<Category> articleCategories;

    public Article() {}

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public List<Category> getArticleCategories() {
        return articleCategories;
    }
    public void setArticleCategories(List<Category> articleCategories) {
        this.articleCategories = articleCategories;
    }

    public String getArticleDate() {
        return articleDate;
    }
    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }

    public String getArticleDescription() {
        return articleDescription;
    }
    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public String getArticleTitle() {
        return articleTitle;
    }
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    //PictureLink should be passed from ArticleParser Class
    public String getPictureLink() {
        pictureLink="https://cdn.pixabay.com/photo/2016/12/29/16/12/eiskristalle-1938842_960_720.jpg";
        return pictureLink;
    }
    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public boolean getArticleIsBookmarked() {
        return articleIsBookmarked;
    }

    public boolean setArticleIsBookmarked(boolean articleIsBookmarked) {
        return this.articleIsBookmarked = articleIsBookmarked;
    }

}
