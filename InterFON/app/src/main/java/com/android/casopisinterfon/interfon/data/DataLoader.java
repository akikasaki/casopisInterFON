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
import java.util.List;

/**
 * Created by Aleksa on 14.3.2017.
 */

public class DataLoader {
    static final int READ_BLOCK_SIZE=100;
    Context context;
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
       /* for (Article task : fromJson) {
            System.out.println(task.getArticleCategory());
        }*/
    }
    public DataLoader(Context context) {
        this.context=context;
    }


}

