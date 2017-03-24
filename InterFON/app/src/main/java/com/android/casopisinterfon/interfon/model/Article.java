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
    private long id;
    private String articleLink;
    private List<Category> articleCategories;

    public Article() {
        randomArticle();
    }

    private void randomArticle() {
        Random rand = new Random();
        int articleNum = rand.nextInt(35);
        boolean duplicateCategory=true;
        pictureLink = "link" + articleNum;
        articleTitle = "tytle" + articleNum;
        articleDescription = "description" + articleNum;
        articleDate = "date" + articleNum;
        articleCategory = Category.getCategory(new Random().nextInt(8) + 1);

        while(duplicateCategory) {
            articleCategory2 = Category.getCategory(new Random().nextInt(8) + 1);
            if(articleCategory2!=articleCategory)
                duplicateCategory=false;
        }
        while(!duplicateCategory) {
            articleCategory3 = Category.getCategory(new Random().nextInt(8) + 1);
            if(articleCategory3!=articleCategory && articleCategory3!=articleCategory2)
                duplicateCategory=true;
        }
        id = articleNum; // TODO - implement id
    }

    public long getId() {
        return id;
    }

    public Category getArticleCategory2() {
        return articleCategory2;
    }
    public Category getArticleCategory() {
        return articleCategory;
    }
    public Category getArticleCategory3() {
        return articleCategory3;
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

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public void setArticleCategories(List<Category> articleCategories) {
        this.articleCategories = articleCategories;
    }
}
