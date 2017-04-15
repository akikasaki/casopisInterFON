package com.android.casopisinterfon.interfon.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.casopisinterfon.interfon.activity.MainActivity;
import com.android.casopisinterfon.interfon.model.Article;
import com.android.casopisinterfon.interfon.model.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Used for storing and accessing articles data
 */
public class DataManager {
    private static final String TAG = DataManager.class.getSimpleName();
    /**
     * Contains single instance of this class.
     */
    private static DataManager mInstance;
    /**
     * Array for storing whole articles data retrieved from the server.
     */
    private List<Map<Long, Article>> mData;

    private DataManager() {
        mData = new ArrayList<>(MainActivity.CATEGORY_COUNT);

        for (int i = 0; i < MainActivity.CATEGORY_COUNT; i++) {
            mData.add(Collections.synchronizedMap(new LinkedHashMap<Long, Article>(12)));
        }
    }

    public synchronized static DataManager getInstance() {
        if (mInstance == null) {
            mInstance = new DataManager();
        }
        return mInstance;
    }

    /**
     * Method for adding list of articles retrieved from the server.
     *
     * @param data        list to be added to memory.
     * @param isFreshData boolean that indicates if this list of data is fresh and old one should be cleared first.
     * @param category    indicates which category of article this list contains
     */
    public void addData(@NonNull List<Article> data, boolean isFreshData, Category category) {
        // TODO - refactor adding data
        Map<Long, Article> m = mData.get(category.ordinal());
        if (isFreshData && data.size() > 0) {
            // Clear list first
            m.clear();
        }

        for (Article a : data) {
            m.put(a.getId(), a);
        }
        Log.d(TAG, String.format("%d articles added, category:%s", data.size(), category.getName()));
    }

//    /**
//     * Sets list of articles.
//     *
//     * @param data list which will replace old data.
//     * @param category data category
//     */
//    private void setData(List<Article> data, Category category) {
//        if (data != null) {
//            mData.clear();
//
//        } else
//            Log.e(TAG, "Cannot pass null parameter as list of articles.");
//    }

    /**
     * Returns filtered list of articles by category based on provided position.
     *
     * @param position position of fragment in view pager.
     * @return filtered list of articles.
     */
    public List<Article> getArticlesForPosition(int position) {
        // TODO - maybe refactor this
        // this method if coupling DataManager and Category class
//        return ArticlesFilter.filterArticles(Category.getCategory(position), new ArrayList<>(mData.values()));
        return new ArrayList<>(mData.get(position).values());
    }

    /**
     * Return {@link Article} object from database based on provided article id.
     *
     * @param id id of wanted article.
     * @param category category of wanted article.
     * @return newly created article object if found, null otherwise.
     */
    public Article getArticle(long id, Category category) {

//        for (Article a :
//                mData) {
//            if (a.getId() == id)
//                return a;
//        }
//
//        return null;
        return mData.get(category.ordinal()).get(id);
    }
}
