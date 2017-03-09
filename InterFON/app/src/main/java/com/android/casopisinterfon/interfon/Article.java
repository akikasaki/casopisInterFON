package com.android.casopisinterfon.interfon;


import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model representing one article
 */
public class Article {

    Category category;
    private String pictureLink;
    private String articleTytle;
    private String articleDescription;
    private Category articleCategory;
    private String articleDate;

    public Article(){
        randomArticle();
    }

    private void randomArticle() {
        Random rand = new Random();
        int articleNum = rand.nextInt(35);

        pictureLink="link"+articleNum;
        articleTytle="tytle"+articleNum;
        articleDescription="description"+articleNum;
        articleDate="date"+articleNum;
        articleCategory= Category.getCategory(new Random().nextInt(8)+1);
        }

    public Category getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(Category articleCategory) {
        this.articleCategory = articleCategory;
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

    public String getArticleTytle() {
        return articleTytle;
    }

    public void setArticleTytle(String articleTytle) {
        this.articleTytle = articleTytle;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    // TODO - finish adding Fields and methods if any; Fields cannot be public. - TRAMPA

}
