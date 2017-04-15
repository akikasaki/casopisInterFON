package com.android.casopisinterfon.interfon.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
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
     * Parameters for bookmarking files
     */
    public static final String ARTICLES_FILE = "articles.txt";
    public static final String BOOKAMRKS__ID_FILE = "bookmarks_activity.txt";

    private DataManager mDataManager;
    private ProgressDialog mProgressDialog;
    private ShareActionProvider mShareActionProvider;
    private Toolbar mToolbar;
    private ImageView ivSingleArticlePicture;
    private TextView tvTitle, tvDescription, tvCategory, tvDate;
    private SharedPreferences fonts;
    private Intent sendIntent;
    boolean bookmarked;

    // Holds reference to the current viewing article
    private Article mCurArticle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_view_activity);

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
        tvTitle = (TextView) findViewById(R.id.tvArticleTitle);
        ivSingleArticlePicture = (ImageView) findViewById(R.id.ivSingleArticlePicture);
        tvCategory = (TextView) findViewById(R.id.tvArticleCategory);
        tvDate = (TextView) findViewById(R.id.tvArticleDate);
        tvDescription = (TextView) findViewById(R.id.tvSingleDescription);

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
            fonts = getSharedPreferences(SettingsActivity.FONTS, MODE_PRIVATE);
            // Load pic
            Glide.with(this).load(mCurArticle.getPictureLink()).into(ivSingleArticlePicture);
            // Set data to view
            tvTitle.setText(mCurArticle.getArticleTitle());
            tvCategory.setText(mCurArticle.getArticleCategoriesString() + " - ");
            // Format date
            tvDate.setText(mCurArticle.getArticleDateString());
            //Set font size
            tvDescription.setTextSize(fonts.getFloat(SettingsActivity.GET_A_FONT, 12));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        DataLoader loadBookmarks = new DataLoader(this);

        /**
         *  Gets a different Menu depending on if the article is bookmarked
         */
        if (loadBookmarks.isBookmarked(mCurArticle.getId(), loadBookmarks.readId(BOOKAMRKS__ID_FILE))) {
            //Toolbar with white Share star
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_single_article_bookmarked, menu);
            bookmarked=true;
        } else {
            //toolbar with yellow Share star
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_single_article, menu);
            bookmarked=false;
        }
        //Set the share button to provide a ShareAction
        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_star:
                //Saves Article into Bookmarks
                DataSaver saveSingleArticle = new DataSaver(this);
                DataLoader loadBookmarks = new DataLoader(this);
                if (bookmarked) {
                    //If the article is bookmarked remove the bookmark and set star to white
                    saveSingleArticle.removeData(mCurArticle, loadBookmarks.readData( ARTICLES_FILE), ARTICLES_FILE);
                    saveSingleArticle.removeId(mCurArticle.getId(), loadBookmarks.readId(BOOKAMRKS__ID_FILE), BOOKAMRKS__ID_FILE);
                    item.setIcon(R.drawable.ic_star_white);
                    bookmarked = false;
                } else {
                    //If the article is not bookmarked save to bookmarks and set star to yellow
                    saveSingleArticle.saveData(mCurArticle, loadBookmarks.readData( ARTICLES_FILE), ARTICLES_FILE);
                    saveSingleArticle.saveId(mCurArticle.getId(), loadBookmarks.readId(BOOKAMRKS__ID_FILE), BOOKAMRKS__ID_FILE);
                    item.setIcon(R.drawable.ic_star_yellow);
                    bookmarked = true;
                }
                return true;

            case R.id.action_share:
                //Intent for sharing an article via the share Action
                sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                //Set the article link to be sent
                sendIntent.putExtra(Intent.EXTRA_TEXT, mCurArticle.getArticleLink());
                sendIntent.setType("text/plain");
                //Choose where to share
                startActivity(Intent.createChooser(sendIntent, "Send Via"));
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
    protected void onResume() {
        super.onResume();
        //Needed for when returning from settings activity if font was changed
        tvDescription.setTextSize(fonts.getFloat(SettingsActivity.GET_A_FONT, 12));
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

