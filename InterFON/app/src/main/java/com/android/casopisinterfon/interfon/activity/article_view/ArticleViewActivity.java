package com.android.casopisinterfon.interfon.activity.article_view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.casopisinterfon.interfon.R;
import com.android.casopisinterfon.interfon.activity.SettingsActivity;
import com.android.casopisinterfon.interfon.data.DataLoader;
import com.android.casopisinterfon.interfon.data.DataManager;
import com.android.casopisinterfon.interfon.data.DataSaver;
import com.android.casopisinterfon.interfon.internet.NetworkManager;
import com.android.casopisinterfon.interfon.internet.URLImageParser;
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
     * Params for intent's extra that indicates if this article is from bookmarks.
     */
    public static final String EXTRA_FROM_BOOKMARK = "extra_from_bookmark";
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

    // Indicates if this article is loaded from bookmarks
    private boolean mIsFromBookmark = false;

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
        tvDescription.setMovementMethod(LinkMovementMethod.getInstance());

        // Show progress dialog
        showProgress();

        // Get current article
        mDataManager = DataManager.getInstance();
        getArticle();
    }

    /**
     * Helper method for starting article info download process.
     *
     * @return article from data
     */
    private void getArticle() {
        // Get intent's extra info
        try {
            getArticleInfo();
        } catch (Exception e) {
            Log.e(TAG, "No article data has been passed.");
            Toast.makeText(this, "Sorry, an error has occurred :(", Toast.LENGTH_SHORT).show();
//            finish();
            return;
        }

        if(mIsFromBookmark){
            // Return article from file disk
            return;
//            mCurArticle = mDataManager.getArticleFromDisk(mCurId);
        } else {
            // Return article from data
            mCurArticle = mDataManager.getArticle(mCurId, mCategory);
        }
        // Download description
        getArticleDesc();
    }

    /**
     * Retrieves article id and category id from intent extra.
     */
    private void getArticleInfo() throws RuntimeException {
        Intent i = getIntent();
        mCurId = i.getLongExtra(EXTRA_ARTICLE_ID, -1);

        if (i.hasExtra(EXTRA_FROM_BOOKMARK)) {
            mIsFromBookmark = true;
            return;
        }
        if (i.hasExtra(EXTRA_ARTICLE_CATEGORY))
            mCategory = (Category) i.getSerializableExtra(EXTRA_ARTICLE_CATEGORY);
        else
            throw new RuntimeException();

        if (mCurId == -1) { // Should not happen
            throw new RuntimeException();
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
        if (mCurArticle == null) return;
        setPic();
        setDesc();
        setInfo();
    }

    private void setPic() {
        if (mCurArticle != null)
            Glide.with(this).load(mCurArticle.getPictureLink()).into(ivSingleArticlePicture);
    }

    private void setInfo() {
        // Display data
        if (mCurArticle != null) {
            // Set text
            tvTitle.setText(Util.fromHtml(mCurArticle.getArticleTitle()));
            tvCategory.setText(mCurArticle.getArticleCategoriesString() + " - ");
            // Format date
            tvDate.setText(mCurArticle.getArticleDateString());
        }
    }

    private void setDesc() {
        // Try hide dialog and set desc
        if (!mCurArticle.getArticleDescription().isEmpty()) {
            // Dismiss progress bar
            dismissProgress();
            // Parse text and images
            URLImageParser p = new URLImageParser(tvDescription, this);
            ArticleTextStyle style = new ArticleTextStyle(mCurArticle.getArticleDescription(), p);
            // Display parsed data
            tvDescription.setText(style.format());
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onArticleInfoDownload(ItemDownloadedEvent event) {
        setDesc();
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
        if (mCurArticle != null && loadBookmarks.isBookmarked(mCurArticle.getId(), loadBookmarks.readId(BOOKAMRKS__ID_FILE))) {
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
                if (mCurArticle != null)
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




