package com.android.casopisinterfon.interfon;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Article> list = new ArrayList<>(100);

        for (int i = 0; i < 100; i++) {
            Article article = new Article();
            list.add(article);
        }

        return list;
    }
}

