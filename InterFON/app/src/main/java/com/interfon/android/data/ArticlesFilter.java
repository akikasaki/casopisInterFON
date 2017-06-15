package com.interfon.android.data;


import com.interfon.android.model.Article;
import com.interfon.android.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Used for filtering articles with multiple categories and returning required ones.
 */
public class ArticlesFilter {
    /**
     * Method for filtering list of articles and returning the required ones.
     * @param articleCategory
     * @param list
     * @return
     */
    public static List<Article> filterArticles(Category articleCategory, List<Article> list) {
        List<Article> filteredList = new ArrayList<>();
        for (Article a: list) {
            List<Category> category = a.getArticleCategories();
            for(Category c : category) {
                if (articleCategory == Category.ALL)
                    return list;
                else if (c == articleCategory) {
                    filteredList.add(a);
                }
            }
        }

        return filteredList;
    }


}
