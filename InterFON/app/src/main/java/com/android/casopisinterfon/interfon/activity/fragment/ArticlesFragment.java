package com.android.casopisinterfon.interfon.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.casopisinterfon.interfon.ArticlesAdapter;
import com.android.casopisinterfon.interfon.R;
import com.android.casopisinterfon.interfon.activity.ArticleViewActivity;
import com.android.casopisinterfon.interfon.data.DataManager;
import com.android.casopisinterfon.interfon.internet.events.ListDownloadedEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ArticlesFragment extends Fragment implements ArticlesAdapter.ItemClickedCallbackInterface{

    public static final String POSITION_ARG = "page_position";

    private DataManager mDataManager;
    private ArticlesAdapter mAdapter;
    private int mFragPosition;

    public ArticlesFragment() {
    }

    public static ArticlesFragment getInstance(int position) {
        Bundle b = new Bundle();
        b.putInt(POSITION_ARG, position);

        // Initialize new fragment
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataManager = DataManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.content_view, container, false);

        // Setup list view and it's mAdapter
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rvArticles);
        mAdapter = new ArticlesAdapter(this);
        rv.setAdapter(mAdapter);

        // Set mAdapter data
        Bundle a = getArguments();
        mFragPosition = a.getInt(POSITION_ARG);
        mAdapter.setData(mDataManager.getArticlesForPosition(mFragPosition));

        return rootView;
    }

    @Override
    public void onItemClicked(String articleId) {
        Intent intent = new Intent(getContext(), ArticleViewActivity.class);
        intent.putExtra(ArticleViewActivity.EXTRA_ARTICLE_ID, articleId);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListDownloadEvent(ListDownloadedEvent event) {
        mAdapter.setData(mDataManager.getArticlesForPosition(mFragPosition));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        // Try refreshing data if it's downloaded
        mAdapter.setData(mDataManager.getArticlesForPosition(mFragPosition));
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
