package com.android.casopisinterfon.interfon.data;

import android.content.Context;
import android.util.Log;

import com.android.casopisinterfon.interfon.DummyData;
import com.android.casopisinterfon.interfon.model.Article;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksa on 14.3.2017.
 */

public class DataSaver {
    Context context; // TODO - use dependency injection
    private final static String TAG="FILE_WRITE_EXCEPTION";

    /**
     * Method for saving all articles into a txt JSON Object
     * @param bookmarkedArticles List of previous bookmarks
     * @param singleArticle Article to be saved into bookmarks List
     */
    public void saveData(Article singleArticle,List<Article> bookmarkedArticles){

    //  Gets previously saved Articles and adds the one we wish to bookmark
        bookmarkedArticles.add(singleArticle);
        Gson gson = new Gson();
        String json = gson.toJson(bookmarkedArticles);

    try {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("articles.txt", Context.MODE_PRIVATE));
        outputStreamWriter.write(json);
        outputStreamWriter.close();
    }
    catch (IOException e) {
        Log.e(TAG, "File write failed: " + e.toString());
    }}

    public DataSaver(Context context) {
            this.context=context;
    }

}
