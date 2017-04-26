package com.android.casopisinterfon.interfon.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Model representing one article
 */
public class Article {

    private long id;
    private String articleTitle;
    private String articleDescription;
    private Date articleDate;
    private String pictureLink;
    private String articleLink;
    private boolean articleIsBookmarked;
    private List<Category> articleCategories = new LinkedList<>();

//    public Article() {
//    }

    public Article(long id, String articleTitle, String articleDescription, String articleDate, String pictureLink, String articleLink) {
        this.id = id;
        this.articleTitle = articleTitle;
        this.articleDescription = articleDescription;
        this.setArticleDate(articleDate);
        this.pictureLink = pictureLink;
        this.articleLink = articleLink;
    }

    public long getId() {
        return id;
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
        if (articleCategories == null) this.articleCategories = new LinkedList<>();
        else this.articleCategories = articleCategories;
    }

    public String getArticleDateString() {
        SimpleDateFormat format = new SimpleDateFormat("dd. MMM yyyy.");

        return format.format(this.articleDate);
    }


    public String getArticleLink() {
        return articleLink;
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
        return articleDescription == null ? "" : articleDescription;
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
