package com.android.casopisinterfon.interfon.activity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.android.casopisinterfon.interfon.internet.NetworkManager;
import com.android.casopisinterfon.interfon.internet.events.ItemDownloadedEvent;
import com.android.casopisinterfon.interfon.model.Article;
import com.android.casopisinterfon.interfon.model.Category;
import com.android.casopisinterfon.interfon.utils.FontPreferences;
import com.android.casopisinterfon.interfon.utils.Util;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ArticleViewActivity extends AppCompatActivity {
    private static final String TAG = ArticleViewActivity.class.getSimpleName();
    /**
     * Parameter for intent's extra that contains id of article that is being opened.
     */
    public static final String EXTRA_ARTICLE_ID = "extra_parameter_id";
    /**
     * Params for intent's extra that contains id of article category.
     */
    public static final String EXTRA_ARTICLE_CATEGORY = "extra_parameter_category_id";
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
    private Intent sendIntent;
    boolean bookmarked;

    // Holds reference to the current viewing article
    private Article mCurArticle = null;
    private Category mCategory = Category.ALL;
    private long mCurId = -1;
    private Object articleDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_view_activity);

        //Sould be used if the Activity was started via Link
       /* Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();*/

        initialize();
        setUpArticle();
    }

    private void setupTheme() {
        try {
            // Set the theme for the activity.
            getTheme().applyStyle(new FontPreferences(this).getFontStyle().getResId(), true);
            Log.e(TAG, "Theme applied");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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


        // Init views
        tvTitle = (TextView) findViewById(R.id.tvArticleTitle);
        ivSingleArticlePicture = (ImageView) findViewById(R.id.ivSingleArticlePicture);
        tvCategory = (TextView) findViewById(R.id.tvArticleCategory);
        tvDate = (TextView) findViewById(R.id.tvArticleDate);
        tvDescription = (TextView) findViewById(R.id.tvSingleDescription);

        // Show progress dialog
        showProgress();

        // Get current article
        mDataManager = DataManager.getInstance();
        mCurArticle = getArticle();
        // Download description
        getArticleDesc();
    }

    /**
     * Helper method for starting article info download process.
     *
     * @return article from data
     */
    private Article getArticle() {
        // Get intent's extra info
        getArticleInfo();
        // Return article from data
        return mDataManager.getArticle(mCurId, mCategory);
    }

    /**
     * Retrieves article id and category id from intent extra.
     */
    private void getArticleInfo() {
        mCurId = getIntent().getLongExtra(EXTRA_ARTICLE_ID, -1);

        try {
            mCategory = (Category) getIntent().getSerializableExtra(EXTRA_ARTICLE_CATEGORY);
        } catch (ClassCastException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
            Toast.makeText(this, "Sorry, an error has occurred :(", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (mCurId == -1) { // Should not happen
            Log.e(TAG, "No article data has been passed.");
            Toast.makeText(this, "Sorry, an error has occurred :(", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }

    /**
     * Helper method for downloading article description from the server.
     */
    public void getArticleDesc() {
        if (mCurArticle.getArticleDescription().isEmpty())
            NetworkManager.getInstance(this).downloadArticleDesc(mCurArticle);
    }

    /**
     * Sets the text in appropriate text views.
     */
    private void setUpArticle() {
        // try hide dialog and set desc
        if (!mCurArticle.getArticleDescription().isEmpty()) {
            dismissProgress();
            tvDescription.setText(Util.fromHtml(mCurArticle.getArticleDescription()));
        }
        // Display data
        if (mCurArticle != null) {
            // Load pic
            Glide.with(this).load(mCurArticle.getPictureLink()).into(ivSingleArticlePicture);
            // Set text
            tvTitle.setText(Util.fromHtml(mCurArticle.getArticleTitle()));
            tvCategory.setText(mCurArticle.getArticleCategoriesString() + " - ");
            // Format date
            tvDate.setText(mCurArticle.getArticleDateString());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onArticleInfoDownload(ItemDownloadedEvent event) {
        setUpArticle();
    }

    /**
     * Helper method for showing indeterminate progress dialog.
     */
    private void showProgress() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(null);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.article_view_loading_article));
        mProgressDialog.show();
    }

    /**
     * Helper method for hiding progress dialog if present.
     */
    private void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the activity if font has been changed.
        if (FontPreferences.isChanged()) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        DataLoader loadBookmarks = new DataLoader(this);

//      Gets a different Menu depending on if the article is bookmarked
        if (loadBookmarks.isBookmarked(mCurArticle.getId(), loadBookmarks.readId(BOOKAMRKS__ID_FILE))) {
            //Toolbar with white Share star
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_single_article_bookmarked, menu);
            bookmarked = true;
        } else {
            //toolbar with yellow Share star
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_single_article, menu);
            bookmarked = false;
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
                    saveSingleArticle.removeData(mCurArticle, loadBookmarks.readData(ARTICLES_FILE), ARTICLES_FILE);
                    saveSingleArticle.removeId(mCurArticle.getId(), loadBookmarks.readId(BOOKAMRKS__ID_FILE), BOOKAMRKS__ID_FILE);
                    item.setIcon(R.drawable.ic_star_white);
                    bookmarked = false;
                } else {
                    //If the article is not bookmarked save to bookmarks and set star to yellow
                    saveSingleArticle.saveData(mCurArticle, loadBookmarks.readData(ARTICLES_FILE), ARTICLES_FILE);
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
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}




