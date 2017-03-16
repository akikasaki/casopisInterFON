package com.android.casopisinterfon.interfon;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {
    public static final String TITLE_KEY = "Title";
    public static final String DESCRIPTION_KEY = "description";
    public static final String DATE_KEY = "Date";
    public static final String PICTURE_KEY = "Picture";
    public static final String CATEGORY_KEY = "Category";
    /**
     * Used for notifying fragment that item has been clicked.
     */
    private ItemClickedCallbackInterface mListener;

    /**
     * Classes that use {@link ArticlesAdapter}, must implement this listener for item list interaction.
     */
    interface ItemClickedCallbackInterface {
        /**
         * Called when item has been clicked.
         * @param articleId id of the article
         */
        void onItemClicked(String articleId);
    }

    /**
     * List for storing appropriate data for current page fragment position that will be shown in recycle view.
     */
    private List<Article> mCurrentData;

    ArticlesAdapter(ItemClickedCallbackInterface listener) {
        this.mListener = listener;
        mCurrentData = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticlesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new MyViewHolder(v, new MyViewHolder.ViewHolderClickListener() {
            @Override
            public void onItemClicked(int position) {
               mListener.onItemClicked(mCurrentData.get(position).getId());
            }
        });
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article a = mCurrentData.get(position);

        holder.tvTitle.setText(a.getArticleTytle());
        holder.tvCategory.setText(a.getArticleCategory().toString());
        holder.tvDate.setText(a.getArticleDate());
        holder.tvDescription.setText(a.getArticleDescription());
        holder.tvPicture.setText(a.getPictureLink());
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

        private CardView mCardView;
        private TextView tvCategory,tvTitle,tvDate,tvPicture,tvDescription;
        private ViewHolderClickListener viewHolderClickListener;

        MyViewHolder(View v, ViewHolderClickListener viewHolderClickListener) {
            super(v);
            this.viewHolderClickListener = viewHolderClickListener;

            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            tvCategory = (TextView) v.findViewById(R.id.tvCategory);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            tvPicture = (TextView) v.findViewById(R.id.tvPicture);

            v.setOnClickListener(this);
        }
    }
}
