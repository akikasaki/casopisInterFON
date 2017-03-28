package com.android.casopisinterfon.interfon.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.casopisinterfon.interfon.data.DataManager;
import com.android.casopisinterfon.interfon.NotificationService;
import com.android.casopisinterfon.interfon.R;
import com.android.casopisinterfon.interfon.model.Article;
import com.bumptech.glide.Glide;


public class ArticleViewActivity extends AppCompatActivity {
    private static final String TAG = ArticleViewActivity.class.getSimpleName();

    /**
     * Parameter for intent's extra that contains id of article that is being opened.
     */
    public static final String EXTRA_ARTICLE_ID = "extra_parameter_id";

    private DataManager mDataManager;
    private ProgressDialog mProgressDialog;
    Toolbar mToolbar;
    ImageView ivSingleArticlePicture;
    TextView tvTitle, tvDescription, tvCategory, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_article);

        initialize();
        getArticle();
    }

    private void initialize() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar2);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);

        mDataManager = DataManager.getInstance();
        tvTitle = (TextView) findViewById(R.id.tvSingleTitle);
        ivSingleArticlePicture = (ImageView) findViewById(R.id.ivSingleArticlePicture);
        tvCategory = (TextView) findViewById(R.id.tvSingleCategory);
        tvDate = (TextView) findViewById(R.id.tvSingleDate);
        tvDescription = (TextView) findViewById(R.id.tvSingleDescription);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(null);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading article");
        mProgressDialog.show();
    }

    private void getArticle() {
        final long id = getIntent().getLongExtra(EXTRA_ARTICLE_ID,0);

        if (id == -1) { // Should not happen
            Log.e(TAG, "No article data has been passed.");
            Toast.makeText(this, "Sorry, an error has occurred :(", Toast.LENGTH_SHORT).show();
            finish();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Article article = mDataManager.getArticle(id);
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

        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, fonts.getFloat(SettingsActivity.GET_A_FONT, 12), getResources().getDisplayMetrics());

        Glide.with(MainActivity.getAppContext()).load(a.getPictureLink()).into(ivSingleArticlePicture);
        tvTitle.setText(a.getArticleTitle());
        tvCategory.setText(a.getArticleCategories().toString());
        tvDate.setText(a.getArticleDate());
       // tvDescription.setText(a.getArticleDescription());

        tvDescription.setTextSize(size);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_single_article, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_star:
                tvDate.setText("1");
                return true;
            case R.id.action_share:
                tvDate.setText("2");
                return true;
            case R.id.action_settings:
                Intent openSettings = new Intent(this, SettingsActivity.class);
                startActivity(openSettings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

