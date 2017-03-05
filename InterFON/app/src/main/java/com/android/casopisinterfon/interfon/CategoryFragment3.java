package com.android.casopisinterfon.interfon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.casopisinterfon.interfon.ArticlesAdapter;
import com.android.casopisinterfon.interfon.R;


/**
 * Created by Aleksa on 5.3.2017.
 */

public class CategoryFragment3 extends Fragment {
    public CategoryFragment3() {
        // Required empty public constructor
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
        ArticlesAdapter adapter = new ArticlesAdapter(new String[]{"test one", "test two", "test three"});
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }
}
