package com.android.casopisinterfon.interfon.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.casopisinterfon.interfon.R;
import com.android.casopisinterfon.interfon.data.DataLoader;
import com.android.casopisinterfon.interfon.data.DataManager;
import com.android.casopisinterfon.interfon.internet.events.URLImageParser;
import com.android.casopisinterfon.interfon.model.Article;
import com.android.casopisinterfon.interfon.model.Category;
import com.android.casopisinterfon.interfon.utils.FontPreferences;
import com.android.casopisinterfon.interfon.utils.Util;
import com.bumptech.glide.Glide;

public class BookmarkViewActivity extends AppCompatActivity{

    private static final String TAG = ArticleViewActivity.class.getSimpleName();

    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private ImageView ivSingleArticlePicture;
    private TextView tvTitle, tvDescription, tvCategory, tvDate;
    DataLoader dataLoader;

    // Holds reference to the current viewing article
    private Article mCurArticle = null;
    private Category mCategory = Category.ALL;
    private long mCurId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_view_activity);
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
        Intent i = getIntent();
        mCurId = i.getLongExtra(ArticleViewActivity.EXTRA_ARTICLE_ID, -1);
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

        dataLoader = new DataLoader(this);
        mCurArticle = dataLoader.loadBookmark(mCurId);
    }

    private void setUpArticle() {
        if (mCurArticle == null) return;
        // try hide dialog and set desc
        if (!mCurArticle.getArticleDescription().isEmpty()) {
            dismissProgress();

            //Implements ImageGetter to parse an Image URL when an <img> tag occurs
            URLImageParser p = new URLImageParser(tvDescription, this);
            //Creates a spannable String to work with
            //                                                                      MODE_LEGACY flag for html-s format
            Spanned htmlSpan = Html.fromHtml(mCurArticle.getArticleDescription(),Html.FROM_HTML_MODE_LEGACY, p, null);
            tvDescription.setText(htmlSpan);
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
    private void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
