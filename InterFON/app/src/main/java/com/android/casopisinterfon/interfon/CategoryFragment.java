package com.android.casopisinterfon.interfon;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Aleksa on 4.3.2017.
 */

public class CategoryFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    public static CategoryFragment newInstance(int page, String title) {
        CategoryFragment fragmentFirst = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle", "page");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_fragment, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvFirst);
        tvLabel.setText(page + " -- " + title);
        return view;
    }

}
