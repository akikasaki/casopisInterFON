package com.android.casopisinterfon.interfon.data;

import android.content.Context;
import android.util.Log;

import com.android.casopisinterfon.interfon.model.Article;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
public class DataLoader {
    Context context;

    /**
     * Method for reading saved article data
     * @return List of bookmarked Articles
     */
    public  List<Article> readData(){
        String ret = "";
            try {
                InputStream inputStream = context.openFileInput("articles.txt");

                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append(receiveString);
                    }

                    inputStream.close();
                    ret = stringBuilder.toString();
                }
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("login activity", "Can not read file: " + e.toString());
            }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Article>>() {}.getType();
        List<Article> fromJson = gson.fromJson(ret,type);
        return fromJson;
    }

    public boolean isBookmarked(Article singleArticle,List<Article> bookmarks){
            Iterator<Article> iter = bookmarks.iterator();

            while (iter.hasNext()) {
                Article a = iter.next();

                if (singleArticle.getId() == a.getId()) {
                   return true;
                }
            }
        return false;
    }
    public DataLoader(Context context) {
        this.context=context;
    }


}

