package com.android.casopisinterfon.interfon;


import com.android.casopisinterfon.interfon.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates mock up data for testing.
 */
public class DummyData {


    /**
     * Method for creating list of dummy articles.
     *
     * @return filled list with articles.
     */

    public DummyData() {
    }

    public static List<Article> createDummyData() {
        List<Article> list = new ArrayList<>(50);

        for (int i = 0; i < 50; i++) {
            Article article = new Article();
            list.add(article);
        }

        return list;
    }
}

