package com.android.casopisinterfon.interfon.data;

import android.content.Context;

import com.android.casopisinterfon.interfon.model.Article;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataSaver {
    private final static String TAG = "FILE_WRITE_EXCEPTION";
    private FileWrite writeToFile;

    private Gson mGson;
    private String json;

    private Context context;
    public DataSaver(Context context){
        this.context=context;
    }
    /**
     * Method for saving all articles into a txt JSON Object
     *
     * @param bookmarkedArticles List of previous bookmarks
     * @param singleArticle      Article to be saved into bookmarks List
     */
    public void saveData(Article singleArticle, List<Article> bookmarkedArticles, String file) {

        mGson = new Gson();

        //  Gets previously saved Articles and adds the one we wish to bookmark
        if(bookmarkedArticles!=null) {
            bookmarkedArticles.add(singleArticle);
            json = mGson.toJson(bookmarkedArticles);
        }
        else{
            List<Article> firstBookmark = new ArrayList<>();
            firstBookmark.add(singleArticle);
            json = mGson.toJson(firstBookmark);
        }

        System.out.println(json);

        writeToFile= new FileWrite();
        writeToFile.writeFile(context,file,json);
    }

    /**
     * Method for bookmarking an Id into a json file
     * @param articleId id of the article we are saving
     * @param bookmarkedArticlesId the list of currently bookmarked id's
     * @param file the file we are saving the bookmark to
     */
    public void saveId(long articleId, List<Long> bookmarkedArticlesId, String file){
        mGson = new Gson();

        //  Gets previously saved Articles and adds the one we wish to bookmark
        if(bookmarkedArticlesId!=null) {
            bookmarkedArticlesId.add(articleId);
            json = mGson.toJson(bookmarkedArticlesId);
        }
        else{
            List<Long> firstBookmark = new ArrayList<>();
            firstBookmark.add(articleId);
            json = mGson.toJson(firstBookmark);
        }

        System.out.println(json);

        writeToFile= new FileWrite();
        writeToFile.writeFile(context,file,json);
    }

    /**
     * Method for removing bookmarked id
     * @param articleId the article we are removing
     * @param bookmarkedArticles the list of bookmarked id's
     * @param file the json file we are removing the bookmark from
     */
    public void removeId(long articleId, List<Long> bookmarkedArticles, String file) {
        Iterator<Long> iter = bookmarkedArticles.iterator();
        
        //Check if the article is in the bookmarked articles list
        while (iter.hasNext()) {
            Long a = iter.next();

            if (articleId == a) {
                iter.remove();
                break;
            }
        }
        mGson = new Gson();
        json = mGson.toJson(bookmarkedArticles);
        System.out.println(json);

        writeToFile= new FileWrite();
        writeToFile.writeFile(context,file,json);
    }

    /**
     * Method for removing a bookmark
     *
     * @param singleArticle      the article we want to remove if present
     * @param bookmarkedArticles the List of bookmarked Articles
     */
    public void removeData(Article singleArticle, List<Article> bookmarkedArticles, String file) {
        Iterator<Article> iter = bookmarkedArticles.iterator();

        while (iter.hasNext()) {
            Article a = iter.next();

            if (singleArticle.getId() == a.getId()) {
                iter.remove();
                break;
            }
        }
        mGson = new Gson();
        json = mGson.toJson(bookmarkedArticles);
        System.out.println(json);

        writeToFile= new FileWrite();
        writeToFile.writeFile(context,file,json);
    }
}
