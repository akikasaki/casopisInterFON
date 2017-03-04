package com.android.casopisinterfon.interfon;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



public class ArticlesAdapter extends RecyclerView.Adapter {

    private List<String> mData;

    public ArticlesAdapter(ArrayList data){
        mData = data;
    }

    public void setData(ArrayList data){
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
