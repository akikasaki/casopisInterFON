package com.android.casopisinterfon.interfon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Aleksa on 8.3.2017.
 */

public class SingleArticle extends AppCompatActivity {
    String title,description,picture,category,date;
    TextView tvTitle,tvDescription,tvPicture,tvCategory,tvDate;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_article);
        Bundle singleArticle=new Bundle();
        getArticle(singleArticle);
        initialize();
        setArticle();
    }

    private void initialize() {
        tvTitle=(TextView) findViewById(R.id.tvSingleTitle);
        tvCategory=(TextView) findViewById(R.id.tvSingleCategory);
        tvDate=(TextView) findViewById(R.id.tvSingleDate);
        tvDescription=(TextView) findViewById(R.id.tvSingleDescription);
        tvPicture=(TextView) findViewById(R.id.tvSinglePicture);
    }

    private void getArticle(Bundle articleData) {
        articleData= getIntent().getExtras();
        title=articleData.getString(ArticlesAdapter.TITLE_KEY);
        description=articleData.getString(ArticlesAdapter.DESCRIPTION_KEY);
        picture=articleData.getString(ArticlesAdapter.PICTURE_KEY);
        category=articleData.getString(ArticlesAdapter.CATEGORY_KEY);
        date=articleData.getString(ArticlesAdapter.DATE_KEY);

    }

    private void setArticle() {
        SharedPreferences fonts = getSharedPreferences(Settings.FONTS, MODE_PRIVATE);
        tvTitle.setText(title);
        tvCategory.setText(category);
        tvDescription.setText(description);
        tvDate.setText(date);
        tvPicture.setText(picture);
        tvTitle.setTextSize(fonts.getFloat(Settings.GET_A_FONT, 10));
        tvCategory.setTextSize(fonts.getFloat(Settings.GET_A_FONT, 10));
        tvDescription.setTextSize(fonts.getFloat(Settings.GET_A_FONT, 10));
        tvDate.setTextSize(fonts.getFloat(Settings.GET_A_FONT, 10));
        tvPicture.setTextSize(fonts.getFloat(Settings.GET_A_FONT, 10));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent notificationStarter= new Intent(this,NotificationService.class);
        SharedPreferences prefs = getSharedPreferences(Settings.NOTIFICATION_TOGGLE, MODE_PRIVATE);
        if(prefs.getBoolean(Settings.NOTIFICATION_STATE, true)){
            startService(notificationStarter);}
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent notificationStarter= new Intent(this,NotificationService.class);
        SharedPreferences prefs = getSharedPreferences(Settings.NOTIFICATION_TOGGLE, MODE_PRIVATE);
        if(prefs.getBoolean(Settings.NOTIFICATION_STATE, true)){
            stopService(notificationStarter);
        }
    }
}

