package com.android.casopisinterfon.interfon;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.casopisinterfon.interfon.activity.MainActivity;
import com.android.casopisinterfon.interfon.model.Article;
import com.android.casopisinterfon.interfon.utils.Util;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {
    public static final String TITLE_KEY = "Title";
    public static final String DESCRIPTION_KEY = "description";
    public static final String DATE_KEY = "Date";
    public static final String PICTURE_KEY = "Picture";
    public static final String CATEGORY_KEY = "Category";

    int type = 0;

    View vw;
    /**
     * Used for notifying fragment that item has been clicked.
     */
    private ItemClickedCallbackInterface mListener;


    /**
     * Classes that use {@link ArticlesAdapter}, must implement this listener for item list interaction.
     */
    public interface ItemClickedCallbackInterface {
        /**
         * Called when item has been clicked.
         *
         * @param articleId id of the article
         */
        void onItemClicked(long articleId);
    }

    /**
     * List for storing appropriate data for current page fragment position that will be shown in recycle view.
     */
    private List<Article> mCurrentData;

    public ArticlesAdapter(ItemClickedCallbackInterface listener) {
        this.mListener = listener;
        mCurrentData = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticlesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        if (viewType == 0) {
            vw = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item, parent, false);
        } else {
            vw = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item2, parent, false);
        }


        return new MyViewHolder(vw, new MyViewHolder.ViewHolderClickListener() {
            @Override
            public void onItemClicked(int position) {
                mListener.onItemClicked(mCurrentData.get(position).getId());
            }
        });
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article a = mCurrentData.get(position);
        // TODO Change underline color depending on category - TRAMPA
        if (holder.getItemViewType() == 0) {
           // holder.tvTitle.setText(Util.fromHtml(a.getArticleTitle()));
            holder.tvTitle.setText(a.getArticleTitle());
            holder.tvCategory.setText(a.getArticleCategories().toString());
            holder.tvDate.setText(a.getArticleDate());
            holder.tvDescription.setText(Util.fromHtml(a.getArticleDescription()));
            Glide.with(MainActivity.getAppContext()).load(a.getPictureLink()).into(holder.ivPicture);
        } else {
            //holder.tvTitle.setText(Util.fromHtml(a.getArticleTitle()));
            holder.tvTitle2.setText(a.getArticleTitle());
            holder.tvCategory2.setText(a.getArticleCategories().toString());
            holder.tvDate2.setText(a.getArticleDate());
            holder.tvDescription2.setText(Util.fromHtml(a.getArticleDescription()));
            Glide.with(MainActivity.getAppContext()).load(a.getPictureLink()).into(holder.ivPicture2);
        }
    }


    @Override
    public int getItemViewType(int position) {
        // Today news will be diferent from others
        // TODO - finsih method
        if (position < 4)
            return 0;
        else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mCurrentData.size();
    }

    /**
     * Sets current adapter data that will be shown in recycle view.
     *
     * @param data list of articles used by this adapter
     */
    public void setData(List<Article> data) {
        mCurrentData = data;
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            viewHolderClickListener.onItemClicked(getAdapterPosition());
        }

        public interface ViewHolderClickListener {
            void onItemClicked(int position);
        }

        CardView mCardView, mCardView2;
        private ImageView ivPicture,ivPicture2;
        private TextView tvCategory, tvTitle, tvDate, tvPicture, tvDescription;
        private TextView tvCategory2,tvTitle2, tvDate2, tvPicture2, tvDescription2;
        private ViewHolderClickListener viewHolderClickListener;

        MyViewHolder(View v, ViewHolderClickListener viewHolderClickListener) {
            super(v);
            this.viewHolderClickListener = viewHolderClickListener;
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            tvCategory = (TextView) v.findViewById(R.id.tvCategory);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            ivPicture = (ImageView) v.findViewById(R.id.ivPicture);
            tvTitle2 = (TextView) v.findViewById(R.id.tvTitle2);
            mCardView2 = (CardView) v.findViewById(R.id.card_view2);
            tvCategory2 = (TextView) v.findViewById(R.id.tvCategory2);
            tvDate2 = (TextView) v.findViewById(R.id.tvDate2);
            tvDescription2 = (TextView) v.findViewById(R.id.tvDescription2);
            ivPicture2 = (ImageView) v.findViewById(R.id.ivPicture2);

            v.setOnClickListener(this);
        }
    }
}
