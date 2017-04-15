package com.android.casopisinterfon.interfon.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Model representing one article
 */
public class Article {

    private String pictureLink;
    private int pictureWidth, pictureHeight;
    private String articleTitle;
    private String articleDescription;
    private Date articleDate;
    private String articleLink;
    private boolean articleIsBookmarked;
    private long id;
    private List<Category> articleCategories;

    public Article() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Category> getArticleCategories() {
        return articleCategories;
    }

    public String getArticleCategoriesString() {
        StringBuilder builder = new StringBuilder();
        int size = articleCategories.size();
        for (int i = 0; i < size; i++) {
            builder.append(articleCategories.get(i).getName());
            if (i + 1 != size)
                builder.append(", ");
        }

        return builder.toString();
    }

    public void setArticleCategories(List<Category> articleCategories) {
        this.articleCategories = articleCategories;
    }

    public String getArticleDateString() {
        SimpleDateFormat format = new SimpleDateFormat("dd. MMM yyyy.");

        return format.format(this.articleDate);
    }

    public Date getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(String articleDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            this.articleDate = format.parse(articleDate);
        } catch (ParseException e) {
            this.articleDate = new Date();
        }
    }

    public void setArticleDate(Date articleDate) {
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
//        pictureLink="https://cdn.pixabay.com/photo/2016/12/29/16/12/eiskristalle-1938842_960_720.jpg";
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
