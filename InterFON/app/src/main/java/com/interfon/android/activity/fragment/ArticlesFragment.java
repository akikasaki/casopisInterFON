package com.interfon.android.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.interfon.android.ArticlesAdapter;
import com.android.casopisinterfon.interfon.R;
import com.interfon.android.activity.EndlessRecyclerViewScrollListener;
import com.interfon.android.activity.MainActivity;
import com.interfon.android.activity.article_view.ArticleViewActivity;
import com.interfon.android.data.DataManager;
import com.interfon.android.internet.NetworkManager;
import com.interfon.android.internet.events.ListDownloadedEvent;
import com.interfon.android.model.Category;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ArticlesFragment extends Fragment implements ArticlesAdapter.ItemClickedCallbackInterface {

    private static final String TAG = "ArticlesFragment";

    public static final String POSITION_ARG = "page_position";


    private MainActivity mActivity;
    private DataManager mDataManager;
    private NetworkManager mNetManager;
    private ArticlesAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int mFragPosition;

    private SwipeRefreshLayout srRootView;
    private RecyclerView rvList;
    private RecyclerView.OnItemTouchListener mOnItemTouchListener;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (MainActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataManager = DataManager.getInstance();
        mNetManager = NetworkManager.getInstance(getContext());

        Bundle a = getArguments();
        mFragPosition = a.getInt(POSITION_ARG);

        initialDownload();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Update frag pos
        Bundle a = getArguments();
        mFragPosition = a.getInt(POSITION_ARG);

        // Inflate the layout for this fragment
        srRootView = (SwipeRefreshLayout) inflater.inflate(R.layout.content_view, container, false);
        srRootView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downloadArticles(NetworkManager.START_PAGE_INDEX, true);
            }
        });

        // Setup list view and it's mAdapter
        rvList = (RecyclerView) srRootView.findViewById(R.id.rvArticles);
        mAdapter = new ArticlesAdapter(this, getContext());
        rvList.setAdapter(mAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(linearLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager, Category.getCategoryByPagePos(mFragPosition)) {
            @Override
            public void onLoadMore(int page, final int totalItemsCount, RecyclerView view) {
                downloadArticles(page, false);
            }
        };
        rvList.addOnScrollListener(scrollListener);

        // Touch listener for recycle view so pages will change more easily
        mOnItemTouchListener = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN && rv.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING) {
//                    Log.d(TAG, "onInterceptTouchEvent: click performed");
                    rv.stopScroll();
//                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
//                    if (childView != null) childView.performClick();
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        };

        // Set mAdapter data
        mAdapter.setData(mDataManager.getArticlesForPosition(mFragPosition));

        // Only big layout for interviews
        if (Category.getCategoryByPagePos(mFragPosition) == Category.INTERVIEWS)
            mAdapter.setIsOnlyBigItems();

        return srRootView;
    }

    /**
     * Helper method for first time download list of articles.
     */
    private void initialDownload() {
        // Only if there is no data, download articles for the first time.
        Category category = Category.getCategoryByPagePos(mFragPosition);
        if (NetworkManager.getCategoryPageIndex(category) < NetworkManager.START_PAGE_INDEX) {
            downloadArticles(NetworkManager.incrementCatPageIndex(category), true);
        }
    }


    /**
     * Helper method for download list of articles for current fragment category based on {@link #mFragPosition}.
     *
     * @param page        page number of articles
     * @param isFreshData indicates if data should be cleared first.
     */
    private void downloadArticles(int page, boolean isFreshData) {
        if (isFreshData && scrollListener != null) scrollListener.resetState();
        if (mFragPosition != 0)
            mNetManager.downloadArticles(page, isFreshData, Category.getCategoryByPagePos(mFragPosition), getContext());
        else
            mNetManager.downloadArticles(page, isFreshData, null, getContext());
    }


    @Override
    public void onItemClicked(long articleId) {
        Intent intent = new Intent(getContext(), ArticleViewActivity.class);
        intent.putExtra(ArticleViewActivity.EXTRA_ARTICLE_ID, articleId);
        intent.putExtra(ArticleViewActivity.EXTRA_ARTICLE_CATEGORY, Category.getCategoryByPagePos(mFragPosition));
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onListDownloadEvent(ListDownloadedEvent event) {
        if (event.eventType.equals(Category.getCategoryByPagePos(mFragPosition)) && event.isSuccess) {
            mAdapter.setData(mDataManager.getArticlesForPosition(mFragPosition));
        }
        srRootView.setRefreshing(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        // Try refreshing data if it's downloaded
        mAdapter.setData(mDataManager.getArticlesForPosition(mFragPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        rvList.addOnItemTouchListener(mOnItemTouchListener);
    }

    @Override
    public void onPause() {
        rvList.removeOnItemTouchListener(mOnItemTouchListener);
        super.onPause();
    }

    //    @Override
//    public void onPause() {
////        if (scrollListener != null)
////            rvList.removeOnScrollListener(scrollListener);
//        super.onPause();
//    }

    @Override
    public void onDestroyView() {
        srRootView.removeAllViews();
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
