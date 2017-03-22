package com.android.casopisinterfon.interfon.data;

import android.util.Log;

import com.android.casopisinterfon.interfon.model.Article;
import com.android.casopisinterfon.interfon.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Used for storing and accessing articles data
 */
public class DataManager {
    private static final String TAG = DataManager.class.getSimpleName();

    /**
     * Array for storing whole articles data retrieved from the server.
     */
    private List<Article> mData;
    /**
     * Contains single instance of this class.
     */
    private static DataManager mInstance;

    public synchronized static DataManager getInstance() {
        if (mInstance == null) {
            mInstance = new DataManager();
        }
        return mInstance;
    }

    private DataManager() {
        mData = new ArrayList<>();
    }

    /**
     * Sets list of articles.
     *
     * @param data list which will replace old data.
     */
    public void setData(List<Article> data) {
        if (data != null)
            mData = data;
        else
            Log.e(TAG, "Cannot pass null parameter as list of articles.");
    }

    /**
     * Method for adding list of articles retrieved from the server.
     *
     * @param data        list to be added to memory.
     * @param isFreshData boolean that indicates if this list of data is fresh and old one should be cleared first.
     */
    public void addData(List<Article> data, boolean isFreshData) {
        if (isFreshData && data.size() > 0) {
            mData.clear();
            mData.addAll(data);
        } else {
            mData.addAll(data);
        }
    }

    /**
     * Returns filtered list of articles by category based on provided position.
     *
     * @param position position of fragment in view pager.
     * @return filtered list of articles.
     */
    public List<Article> getArticlesForPosition(int position) {
        // TODO - maybe refactor this
        // this method if coupling DataManager and Category class
        return ArticlesFilter.filterArticles(Category.getCategory(position), mData);
    }

    /**
     * Return {@link Article} object from database based on provided article id.
     * <p><u>NOTE</u>: Should called this method from other thread.</p>
     *
     * @param id id of wanted article.
     * @return newly created article object if found, null otherwise.
     */
    public Article getArticle(long id) {

        for (Article a :
                mData) {
            if (a.getId()==id)
                return a;
        }

        return null;
    }
}
