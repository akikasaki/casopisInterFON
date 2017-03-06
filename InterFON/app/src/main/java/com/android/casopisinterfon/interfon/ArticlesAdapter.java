package com.android.casopisinterfon.interfon;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.casopisinterfon.interfon.model.Article;

import java.util.List;


public class ArticlesAdapter extends RecyclerView.Adapter <ArticlesAdapter.MyViewHolder>{

    /**
     * Array for storing whole articles data retrieved from the server.
     */
    public static List<Article> mData;

    /**
     * List for storing appropriate data for current page fragment position that will be shown in recycle view.
     */
    private List<Article> mCurrentData;

    public ArticlesAdapter(List<Article> data) {
        mData = data;
    }

    public ArticlesAdapter() {
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
//        holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        // Lista artikla iz trenutne kategorije
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
        private TextView mTextView;

        MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.tv_text);
        }
    }
}
