package com.android.casopisinterfon.interfon;

import android.content.Context;
import android.content.Intent;
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
    public static final String TITLE_KEY="Title";
    public static final String DESCRIPTION_KEY="description";
    public static final String DATE_KEY="Date";
    public static final String PICTURE_KEY="Picture";
    static Context context;
    /**
     * Array for storing whole articles data retrieved from the server.
     */
    public static List<Article> mData;

    /**
     * List for storing appropriate data for current page fragment position that will be shown in recycle view.
     */
    private List<Article> mCurrentData;


    public ArticlesAdapter() {}

    // Create new views (invoked by the layout manager)
    @Override
    public ArticlesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new MyViewHolder(v, new MyViewHolder.ItemClickListener(){
            @Override
            public void onItemClicked(int position) {
                Article singleArticle ;
               singleArticle= mCurrentData.get(position);
                Bundle sendSingleArticle= new Bundle();
                singleArticle.getArticleTytle();
                sendSingleArticle.putString(TITLE_KEY,singleArticle.getArticleTytle());
                sendSingleArticle.putString(DESCRIPTION_KEY,singleArticle.getArticleDescription());
                sendSingleArticle.putString(DATE_KEY,singleArticle.getArticleDate());
                sendSingleArticle.putString(PICTURE_KEY,singleArticle.getPictureLink());
                sendSingleArticle.putString(PICTURE_KEY,singleArticle.getArticleCategory().toString());
                Intent intent = new Intent(context, SingleArticle.class);
                intent.putExtras(sendSingleArticle);
                context.startActivity(intent);
            }
        });
        // set the view's size, margins, paddings and layout parameters
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
    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        @Override
        public void onClick(View v) {
            itemClickListener.onItemClicked(getAdapterPosition());
        }

        public interface ItemClickListener{
           void onItemClicked(int position);
        }

        private CardView mCardView;
        private TextView tvCategory;
        private TextView tvSubject;
        private ItemClickListener itemClickListener;

        MyViewHolder(View v, ItemClickListener itemClickListener) {
            super(v);
            context = itemView.getContext();
            this.itemClickListener = itemClickListener;

            tvSubject = (TextView) v.findViewById(R.id.tvSubject);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            tvCategory = (TextView) v.findViewById(R.id.tvText);

            v.setOnClickListener(this);
        }
    }
}
