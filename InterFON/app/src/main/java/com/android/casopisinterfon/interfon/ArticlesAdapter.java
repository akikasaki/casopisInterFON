package com.android.casopisinterfon.interfon;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class ArticlesAdapter extends RecyclerView.Adapter <ArticlesAdapter.MyViewHolder>{
    Category category;
    /**
     * Array for storing whole articles data retrieved from the server.
     */
    public static List<Article> mData;

    /**
     * List for storing appropriate data for current page fragment position that will be shown in recycle view.
     */
    private List<Article> mCurrentData;


    public ArticlesAdapter() {
       // mCurrentData = mData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticlesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article a = mCurrentData.get(position);

        holder.tvSubject.setText(a.getArticleTytle());
        holder.tvCategory.setText(a.getArticleCategory().toString());
    }

    @Override
    public int getItemCount() {
        // Lista artikla iz trenutne kategorije
//        return mCurrentData.size();

        return mCurrentData.size();
    }

    /**
     * Sets current adapter data that will be shown in recycle view.
     * @param data
     */
    public void setData(List<Article> data) {
        mCurrentData = data;
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView tvCategory;
        private TextView tvSubject;

        MyViewHolder(View v) {
            super(v);
            tvSubject = (TextView) v.findViewById(R.id.tvSubject);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            tvCategory = (TextView) v.findViewById(R.id.tvText);
        }
    }
}
