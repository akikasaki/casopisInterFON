package com.android.casopisinterfon.interfon;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
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

    /**
     * Method for saving all articles into a txt JSON Object
     */
    public void saveData(){

        List<Article> articles = DummyData.createDummyData();
        Gson gson = new Gson();
        String json = gson.toJson(articles);

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
