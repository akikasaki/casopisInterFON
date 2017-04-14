package com.android.casopisinterfon.interfon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.casopisinterfon.interfon.ArticlesAdapter;
import com.android.casopisinterfon.interfon.BookmarksAdapter;
import com.android.casopisinterfon.interfon.R;
import com.android.casopisinterfon.interfon.data.DataLoader;

public class BookmarksActivity extends AppCompatActivity implements ArticlesAdapter.ItemClickedCallbackInterface{
    private RecyclerView mRecyclerView;
    private BookmarksAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarks);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvBookmarks);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BookmarksAdapter(this, this);
        mAdapter.setData(new DataLoader(this).readData(ArticleViewActivity.ARTICLES_FILE));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(long articleId) {
        Intent intent = new Intent(getApplicationContext(), ArticleViewActivity.class);
        intent.putExtra(ArticleViewActivity.EXTRA_ARTICLE_ID, articleId);
        startActivity(intent);
    }
}
