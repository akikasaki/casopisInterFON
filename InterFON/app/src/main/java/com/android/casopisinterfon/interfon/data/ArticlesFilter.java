package com.android.casopisinterfon.interfon.data;


import com.android.casopisinterfon.interfon.model.Article;
import com.android.casopisinterfon.interfon.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Used for filtering articles with multiple categories and returning required ones.
 */
public class ArticlesFilter {
    public static final String POSITION_ARG = "page_position";

    // JSON names retrieved from server
    /**
     * Total number of articles in json
     **/
    public static final String COUNT = "count";
    /**
     * Total number of articles on website
     */
    public static final String COUNT_TOTAL = "count_total";
    /**
     * Total number of pages on website
     */
    public static final String PAGES = "pages";
    /**
     * Posts json object
     */
    public static final String POSTS = "posts";
    public static final String POST_ID = "id";
    public static final String POST_TITLE = "title";
    public static final String POST_CONTENT = "content";

    /**
     * Method for filtering list of articles and returning the required ones.
     * @param articleCategory
     * @param list
     * @return
     */
    public static List<Article> filterArticles(Category articleCategory, List<Article> list) {
        List<Article> filteredList = new ArrayList<>();
        for (Article a: list) {
            if(articleCategory==Category.ALL)
                return list;
            else if(a.getArticleCategory()== articleCategory){
               filteredList.add(a);
            }
            else if(a.getArticleCategory2()== articleCategory){
                filteredList.add(a);
            }
            else if(a.getArticleCategory3()== articleCategory){
                filteredList.add(a);
            }

        }

        return filteredList;
    }


}
