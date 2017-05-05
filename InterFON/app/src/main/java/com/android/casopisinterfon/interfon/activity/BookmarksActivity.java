package com.android.casopisinterfon.interfon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.casopisinterfon.interfon.ArticlesAdapter;
import com.android.casopisinterfon.interfon.R;
import com.android.casopisinterfon.interfon.activity.article_view.ArticleViewActivity;
import com.android.casopisinterfon.interfon.data.DataLoader;

public class BookmarksActivity extends AppCompatActivity implements ArticlesAdapter.ItemClickedCallbackInterface {
    private RecyclerView mRecyclerView;
    private ArticlesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarks_activity);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ((TextView)findViewById(R.id.tvHeading)).setText(R.string.single_article_heading);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvBookmarks);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ArticlesAdapter(this, this);
        mAdapter.setIsOnlySmallItems();
        mAdapter.setData(new DataLoader(this).readData(ArticleViewActivity.ARTICLES_FILE));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(long articleId) {
        Intent intent = new Intent(this, BookmarkViewActivity.class);
        intent.putExtra(ArticleViewActivity.EXTRA_ARTICLE_ID, articleId);
        intent.putExtra(ArticleViewActivity.EXTRA_FROM_BOOKMARK, true);
        startActivity(intent);
    }
}
