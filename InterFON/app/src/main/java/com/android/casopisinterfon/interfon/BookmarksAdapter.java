package com.android.casopisinterfon.interfon;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.casopisinterfon.interfon.activity.MainActivity;
import com.android.casopisinterfon.interfon.data.DataLoader;
import com.android.casopisinterfon.interfon.model.Article;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksa on 3.4.2017.
 */

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.MyViewHolder> {
    View vw;
    /**
     * Used for notifying fragment that item has been clicked.
     */
    private ArticlesAdapter.ItemClickedCallbackInterface mListener;


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
    private List<Article> mBookmarkData;

    public BookmarksAdapter(ArticlesAdapter.ItemClickedCallbackInterface listener) {
        this.mListener = listener;
        mBookmarkData = new ArrayList<>();
        DataLoader dataLoader= new DataLoader(MainActivity.getAppContext());
        mBookmarkData= dataLoader.readData();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookmarksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        vw = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item2, parent, false);

        return new BookmarksAdapter.MyViewHolder(vw, new BookmarksAdapter.MyViewHolder.ViewHolderClickListener() {
            @Override
            public void onItemClicked(int position) {
                mListener.onItemClicked(mBookmarkData.get(position).getId());
            }
        });
    }

    @Override
    public void onBindViewHolder(BookmarksAdapter.MyViewHolder holder, int position) {
        Article singleBookmarkedArticle = mBookmarkData.get(position);
        holder.tvTitle2.setText(singleBookmarkedArticle.getArticleTitle());
        holder.tvCategory2.setText(singleBookmarkedArticle.getArticleCategories().toString());
        holder.tvDate2.setText(singleBookmarkedArticle.getArticleDescription());
        Glide.with(MainActivity.getAppContext()).load("https://cdn.pixabay.com/photo/2016/12/29/16/12/eiskristalle-1938842_960_720.jpg").into(holder.ivPicture2);
    }

    @Override
    public int getItemCount() {
        return mBookmarkData.size();
    }

    /**
     * Sets current adapter data that will be shown in recycle view.
     *
     * @param data list of articles used by this adapter
     */
    public void setData(List<Article> data) {
        mBookmarkData = data;
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

        private ImageView ivPicture2;
        private TextView tvCategory2,tvTitle2, tvDate2;
        private RelativeLayout rlCardView;
        private BookmarksAdapter.MyViewHolder.ViewHolderClickListener viewHolderClickListener;

        MyViewHolder(View v, BookmarksAdapter.MyViewHolder.ViewHolderClickListener viewHolderClickListener) {
            super(v);
            this.viewHolderClickListener = viewHolderClickListener;
            rlCardView = (RelativeLayout) v.findViewById(R.id.card_view);
            tvTitle2 = (TextView) v.findViewById(R.id.tvTitle2);
            tvCategory2 = (TextView) v.findViewById(R.id.tvCategory2);
            tvDate2 = (TextView) v.findViewById(R.id.tvDate2);
            ivPicture2 = (ImageView) v.findViewById(R.id.ivPicture2);

            v.setOnClickListener(this);
        }
    }
}
