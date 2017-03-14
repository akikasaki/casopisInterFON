package com.android.casopisinterfon.interfon;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksa on 14.3.2017.
 */

public class DataSaver {
    Context context;
    private final static String TAG="FILE_WRITE_EXCEPTION";
public void saveData(){
    try {
        List<Article> articles = DummyData.createDummyData();
        Gson gson = new Gson();
        String json = gson.toJson(articles);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("articles.txt", Context.MODE_PRIVATE));
        outputStreamWriter.write(json);
        outputStreamWriter.close();
    }
    catch (IOException e) {
        Log.e(TAG, "File write failed: " + e.toString());
    }
}
public void readData(){
    String ret = "";

    try {
        InputStream inputStream = context.openFileInput("articles.txt");

        if ( inputStream != null ) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ( (receiveString = bufferedReader.readLine()) != null ) {
                stringBuilder.append(receiveString);
            }

            inputStream.close();
            ret = stringBuilder.toString();
        }
    }
    catch (FileNotFoundException e) {
        Log.e("login activity", "File not found: " + e.toString());
    } catch (IOException e) {
        Log.e("login activity", "Can not read file: " + e.toString());
    }

    List<Article> list = new ArrayList<Article>();
    Gson gson = new Gson();
    Type type = new TypeToken<List<Article>>() {}.getType();
    String json = gson.toJson(list, type);
    List<Article> fromJson = gson.fromJson(ret,type);

    for (Article task : fromJson) {
        System.out.println(task.getArticleCategory());
    }
}
    public DataSaver(Context context) {
            this.context=context;
    }


}
