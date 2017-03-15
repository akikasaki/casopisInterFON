package com.android.casopisinterfon.interfon;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Used for storing and accessing articles data
 */
public class DataManager {
    Context context;
    private static final String TAG = DataManager.class.getSimpleName();

    /**
     * Array for storing whole articles data retrieved from the server.
     */
    private List<Article> mData;

    private static DataManager mInstance;

    public static DataManager getInstance(){
        if (mInstance == null) {
            mInstance = new DataManager();
        }

        return mInstance;
    }

    private DataManager(){
        mData = new ArrayList<>();
    }

    public void setData(List<Article> data){
        if (data != null)
            mData = data;
        else
            Log.e(TAG, "Cannot pass null parameter as list of articles.");
    }

    /**
     * Returns filtered list of articles by category based on provided position.
     * @param position position of fragment in view pager.
     * @return filtered list of articles.
     */
    public List<Article> getArticlesForPosition(int position) {
        return ArticlesFilter.filterArticles(Category.getCategory(position), mData);
    }

    /**
     * Return {@link Article} object from database based on provided article id.
     * <p><u>NOTE</u>: Should called this method from other thread.</p>
     * @param id id of wanted article.
     * @return newly created article object if found, null otherwise.
     */
    public Article getArticle(String id) {

        for (Article a :
                mData) {
            if (a.getId().equals(id))
                return a;
        }

        return null;
    }
}
