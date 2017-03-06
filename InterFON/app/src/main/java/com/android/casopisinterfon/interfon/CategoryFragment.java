package com.android.casopisinterfon.interfon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.casopisinterfon.interfon.internet.DownloadInterface;

import org.json.JSONObject;


public class CategoryFragment extends Fragment implements DownloadInterface{

    public static final String POSITION_ARG = "page_position";

    int number;

    public CategoryFragment() {
    }

    public static CategoryFragment getInstance(int position) {
        Bundle b = new Bundle();
        b.putInt(POSITION_ARG, position);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.content_view, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rvArticles);
        rv.setHasFixedSize(true);
        ArticlesAdapter adapter = new ArticlesAdapter();
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    @Override
    public void onDownloadSuccess(JSONObject response) {
//        List<Article> list = ArticlesParser.parseResponse(response);
    }

    @Override
    public void onDownloadFailed(String error) {
    }
}
