package com.android.casopisinterfon.interfon.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import com.android.casopisinterfon.interfon.data.DataManager;
import com.android.casopisinterfon.interfon.NotificationService;
import com.android.casopisinterfon.interfon.R;
import com.android.casopisinterfon.interfon.model.Article;


public class ArticleViewActivity extends AppCompatActivity {
    private static final String TAG = ArticleViewActivity.class.getSimpleName();

    /**
     * Parameter for intent's extra that contains id of article that is being opened.
     */
    public static final String EXTRA_ARTICLE_ID = "extra_parameter_id";

    private DataManager mDataManager;
    private ProgressDialog mProgressDialog;

    private Article mCurArticle;

    TextView tvTitle, tvDescription, tvPicture, tvCategory,tvCategory2,tvCategory3, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_article);

        initialize();
        getArticle();
    }

    private void initialize() {
        mDataManager = DataManager.getInstance();
        tvTitle = (TextView) findViewById(R.id.tvSingleTitle);
        tvCategory = (TextView) findViewById(R.id.tvSingleCategory);
        tvCategory2 = (TextView) findViewById(R.id.tvSingleCategory2);
        tvCategory3 = (TextView) findViewById(R.id.tvSingleCategory3);
        tvDate = (TextView) findViewById(R.id.tvSingleDate);
        tvDescription = (TextView) findViewById(R.id.tvSingleDescription);
        tvPicture = (TextView) findViewById(R.id.tvSinglePicture);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(null);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading article");
        mProgressDialog.show();
    }

    private void getArticle() {
        final String _id = getIntent().getStringExtra(EXTRA_ARTICLE_ID);

        if (_id == null) { // Should not happen
            Log.e(TAG, "No article data has been passed.");
            Toast.makeText(this, "Sorry, an error has occurred :(", Toast.LENGTH_SHORT).show();
            finish();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Article article = mDataManager.getArticle(_id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setArticle(article);
                        mProgressDialog.dismiss();
                    }
                });
            }
        }).start();
    }

    private void setArticle(Article a) {
        SharedPreferences fonts = getSharedPreferences(SettingsActivity.FONTS, MODE_PRIVATE);

        float fontSize = fonts.getFloat(SettingsActivity.GET_A_FONT, 12);

        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics());

        tvTitle.setText(a.getArticleTitle());
        tvCategory.setText(a.getArticleCategory().name());
        tvCategory2.setText(a.getArticleCategory2().name());
        tvCategory3.setText(a.getArticleCategory3().name());
        tvDescription.setText(a.getArticleDescription());
        tvDate.setText(a.getArticleDate());
        tvPicture.setText(a.getPictureLink());
        tvTitle.setTextSize(fontSize);
        tvCategory.setTextSize(fontSize);
        tvCategory2.setTextSize(fontSize);
        tvCategory3.setTextSize(fontSize);
        tvDescription.setTextSize(fontSize);
        tvDate.setTextSize(fontSize);
        tvPicture.setTextSize(fontSize);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent notificationStarter = new Intent(this, NotificationService.class);
        SharedPreferences prefs = getSharedPreferences(SettingsActivity.NOTIFICATION_TOGGLE, MODE_PRIVATE);
        if (prefs.getBoolean(SettingsActivity.NOTIFICATION_STATE, true)) {
            startService(notificationStarter);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent notificationStarter = new Intent(this, NotificationService.class);
        SharedPreferences prefs = getSharedPreferences(SettingsActivity.NOTIFICATION_TOGGLE, MODE_PRIVATE);
        if (prefs.getBoolean(SettingsActivity.NOTIFICATION_STATE, true)) {
            stopService(notificationStarter);
        }
    }
}

