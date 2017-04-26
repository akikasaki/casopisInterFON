package com.android.casopisinterfon.interfon;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.casopisinterfon.interfon.model.Article;
import com.android.casopisinterfon.interfon.utils.Util;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

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

    private static final int LARGE_ITEM_VIEW_TYPE = 0;
    private static final int SMALL_ITEM_VIEW_TYPE = 1;
    private boolean mOnlySmallLayout = false;

    private final Context mContext;

    /**
     * Used for notifying fragment that item has been clicked.
     */
    private ItemClickedCallbackInterface mListener;

    /**
     * List for storing appropriate data for current page fragment position that will be shown in recycle view.
     */
    private List<Article> mCurrentData;

    public ArticlesAdapter(ItemClickedCallbackInterface listener, Context context) {
        this.mListener = listener;
        this.mContext = context;
        mCurrentData = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticlesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView;
        // create a new view
        if (viewType == LARGE_ITEM_VIEW_TYPE) {
            rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_item, parent, false);
        } else
            rootView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_item_small, parent, false);


        return new MyViewHolder(rootView, new MyViewHolder.ViewHolderClickListener() {
            @Override
            public void onItemClicked(int position) {
                mListener.onItemClicked(mCurrentData.get(position).getId());
            }
        });
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article a = mCurrentData.get(position);
        holder.tvTitle.setText(Util.fromHtml(a.getArticleTitle()));
//        holder.tvTitle.setText(a.getArticleTitle());
        holder.tvCategory.setText(a.getArticleCategoriesString());
        holder.tvDate.setText(a.getArticleDateString());

//        if (getItemViewType(position) == SMALL_ITEM_VIEW_TYPE)
            Glide
                    .with(mContext)
                    .load(a.getPictureLink())
                    .placeholder(R.drawable.placeholder)
                    .dontAnimate()
//                    .centerCrop()
                    .into(holder.ivThumbnail);
//        else
//            Glide
//                    .with(mContext)
//                    .load(a.getPictureLink())
//                    .placeholder(R.drawable.placeholder)
//                    .dontAnimate()
//                    .into(holder.ivThumbnail);
    }

    @Override
    public int getItemViewType(int position) {
        // Today news will be diferent from others
        // TODO - finsih method
        if (mOnlySmallLayout) return SMALL_ITEM_VIEW_TYPE;
        if (position < 5)
            return LARGE_ITEM_VIEW_TYPE;
        else
            return SMALL_ITEM_VIEW_TYPE;
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
        if (data == null) return;
        mCurrentData = data;
        notifyDataSetChanged();
    }

    /**
     * Sets the adapter so it displays only small item layout.
     *
     * @param onlySmallItems set to true if adapter should display only small layout.
     */
    public void setIsOnlySmallItems(boolean onlySmallItems) {
        this.mOnlySmallLayout = true;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mListener.onItemClicked(getAdapterPosition());
        }

        public interface ViewHolderClickListener {
            void onItemClicked(int position);
        }

        private ImageView ivThumbnail;
        private TextView tvTitle, tvDate, tvCategory;
        private ViewHolderClickListener mListener;

        MyViewHolder(View v, ViewHolderClickListener listener) {
            super(v);
            this.mListener = listener;
            tvTitle = (TextView) v.findViewById(R.id.tvArticleTitle);
            tvCategory = (TextView) v.findViewById(R.id.tvCategory);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            ivThumbnail = (ImageView) v.findViewById(R.id.ivThumbnail);

            v.setOnClickListener(this);
        }
    }
}
