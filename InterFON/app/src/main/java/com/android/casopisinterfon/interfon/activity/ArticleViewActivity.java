package com.android.casopisinterfon.interfon.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.casopisinterfon.interfon.R;
import com.android.casopisinterfon.interfon.data.DataLoader;
import com.android.casopisinterfon.interfon.data.DataManager;
import com.android.casopisinterfon.interfon.data.DataSaver;
import com.android.casopisinterfon.interfon.model.Article;
import com.bumptech.glide.Glide;


public class ArticleViewActivity extends AppCompatActivity {
    private static final String TAG = ArticleViewActivity.class.getSimpleName();
    /**
     * Parameter for intent's extra that contains id of article that is being opened.
     */
    public static final String EXTRA_ARTICLE_ID = "extra_parameter_id";
    /**
     * Parameters for boookmarking files
     */
    public static final String ARTICLES_FILE = "articles.txt";
    public static final String BOOKAMRKS__ID_FILE = "bookmarks.txt";

    private DataManager mDataManager;
    private ProgressDialog mProgressDialog;
    Toolbar mToolbar;
    ImageView ivSingleArticlePicture;
    TextView tvTitle, tvDescription, tvCategory, tvDate;
    boolean bookmarked;

    // Holds reference to the current viewing article
    private Article mCurArticle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_article);

        initialize();
        setArticle();
    }

    private void initialize() {
        // Init toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);

        // Get current article
        mDataManager = DataManager.getInstance();
        mCurArticle = getArticle();
        // Init views
        tvTitle = (TextView) findViewById(R.id.tvSingleTitle);
        ivSingleArticlePicture = (ImageView) findViewById(R.id.ivSingleArticlePicture);
        tvCategory = (TextView) findViewById(R.id.tvSingleCategory);
        tvDate = (TextView) findViewById(R.id.tvSingleDate);
        tvDescription = (TextView) findViewById(R.id.tvSingleDescription);

//        DataLoader loadBookmarks = new DataLoader(getApplicationContext());
//        bookmarked = loadBookmarks.isBookmarked(a, loadBookmarks.readData());

        // Show progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(null);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading article");
//        mProgressDialog.show();
    }

    private Article getArticle() {
        final long id = getIntent().getLongExtra(EXTRA_ARTICLE_ID, 0);

        if (id == -1) { // Should not happen
            Log.e(TAG, "No article data has been passed.");
            Toast.makeText(this, "Sorry, an error has occurred :(", Toast.LENGTH_SHORT).show();
            finish();
        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final Article article = mDataManager.getArticle(id);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        setArticle(article);
//                        mProgressDialog.dismiss();
//                    }
//                });
//            }
//        }).start();
        return mDataManager.getArticle(id);
    }

    private void setArticle() {
        if (mCurArticle != null) {
            // Get font size
            SharedPreferences fonts = getSharedPreferences(SettingsActivity.FONTS, MODE_PRIVATE);
            // Load pic
            Glide.with(this).load(mCurArticle.getPictureLink()).into(ivSingleArticlePicture);
            // Set data to view
            tvTitle.setText(mCurArticle.getArticleTitle());
            tvCategory.setText(mCurArticle.getArticleCategories().toString());
            // Format date
            tvDate.setText(mCurArticle.getArticleDateString());
            //Set font size
            tvDescription.setTextSize(fonts.getFloat(SettingsActivity.GET_A_FONT, 12));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        DataLoader loadBookmarks = new DataLoader();
        //Gets a different Menu depending on if the article is bookmarked
        if (loadBookmarks.isBookmarked(mCurArticle.getId(), loadBookmarks.readId(this, BOOKAMRKS__ID_FILE))) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_single_article_bookmarked, menu);
            bookmarked=true;
        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_single_article, menu);
            bookmarked=false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_star:
                //Saves Article into Bookmarks
                DataSaver saveSingleArticle = new DataSaver(this);
                DataLoader loadBookmarks = new DataLoader();
                if (bookmarked) {
                    saveSingleArticle.removeData(mCurArticle, loadBookmarks.readData(this, ARTICLES_FILE), ARTICLES_FILE);
                    saveSingleArticle.removeId(mCurArticle.getId(), loadBookmarks.readId(this,BOOKAMRKS__ID_FILE), BOOKAMRKS__ID_FILE);
                    item.setIcon(R.drawable.ic_star_white);
                    bookmarked = false;
                } else {
                    saveSingleArticle.saveData(mCurArticle, loadBookmarks.readData(this, ARTICLES_FILE), ARTICLES_FILE);
                    saveSingleArticle.saveId(mCurArticle.getId(), loadBookmarks.readId(this,BOOKAMRKS__ID_FILE), BOOKAMRKS__ID_FILE);
                    item.setIcon(R.drawable.ic_star_yellow);
                    bookmarked = true;
                }
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
//        Intent notificationStarter = new Intent(this, NotificationService.class);
//        SharedPreferences prefs = getSharedPreferences(SettingsActivity.NOTIFICATION_TOGGLE, MODE_PRIVATE);
//        if (prefs.getBoolean(SettingsActivity.NOTIFICATION_STATE, true)) {
//            startService(notificationStarter);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Intent notificationStarter = new Intent(this, NotificationService.class);
//        SharedPreferences prefs = getSharedPreferences(SettingsActivity.NOTIFICATION_TOGGLE, MODE_PRIVATE);
//        if (prefs.getBoolean(SettingsActivity.NOTIFICATION_STATE, true)) {
//            stopService(notificationStarter);
//        }
    }
}

