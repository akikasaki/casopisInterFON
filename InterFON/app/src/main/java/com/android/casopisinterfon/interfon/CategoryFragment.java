package com.android.casopisinterfon.interfon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.casopisinterfon.interfon.internet.DownloadInterface;

import org.json.JSONObject;


public class CategoryFragment extends Fragment implements DownloadInterface, ArticlesAdapter.ItemClickedCallbackInterface{

    private DataManager mDataManager;
    public static final String POSITION_ARG = "page_position";

    public CategoryFragment() {
    }

    public static CategoryFragment getInstance(int position) {
        Bundle b = new Bundle();
        b.putInt(POSITION_ARG, position);

        // Initialize new fragment
        CategoryFragment fragment = new CategoryFragment();
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

        // Setup list view and it's adapter
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rvArticles);
        ArticlesAdapter adapter = new ArticlesAdapter(this);
        rv.setAdapter(adapter);

        // Set adapter data
        Bundle a = getArguments();
        int position= a.getInt(POSITION_ARG);
        adapter.setData(mDataManager.getArticlesForPosition(position));

        return rootView;
    }

    @Override
    public void onItemClicked(String articleId) {
        Intent intent = new Intent(getContext(), SingleArticle.class);
        intent.putExtra(SingleArticle.EXTRA_ARTICLE_ID, articleId);
        startActivity(intent);
    }

    @Override
    public void onDownloadSuccess(JSONObject response) {
//        List<Article> list = ArticlesParser.parseResponse(response);
    }

    @Override
    public void onDownloadFailed(String error) {
    }


}
