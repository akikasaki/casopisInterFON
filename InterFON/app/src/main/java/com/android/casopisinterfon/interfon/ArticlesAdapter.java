package com.android.casopisinterfon.interfon;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.casopisinterfon.interfon.activity.MainActivity;
import com.android.casopisinterfon.interfon.model.Article;
import com.android.casopisinterfon.interfon.utils.Util;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {
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
        }
        else if(viewType==1){
            vw = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_item, parent, false);
        }
        else if(viewType==2){
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
        if (holder.getItemViewType() == 0) {
           // holder.tvTitle.setText(Util.fromHtml(a.getArticleTitle()));
            holder.tvTitle.setText(a.getArticleTitle());
            holder.tvCategory.setText(a.getArticleCategories().toString());
            holder.tvDate.setText(a.getArticleDate());
            Glide.with(MainActivity.getAppContext()).load(a.getPictureLink()).into(holder.ivPicture);

        }
        else if(holder.getItemViewType()==1){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 15);
            holder.rlCardView.setLayoutParams(params);
            holder.tvTitle.setText(a.getArticleTitle());
            holder.tvCategory.setText(a.getArticleCategories().toString());
            holder.tvDate.setText(a.getArticleDate());
            Glide.with(MainActivity.getAppContext()).load(a.getPictureLink()).into(holder.ivPicture);
        }
        else if(holder.getItemViewType()==2){
            //holder.tvTitle.setText(Util.fromHtml(a.getArticleTitle()));
            holder.tvTitle2.setText(a.getArticleTitle());
            holder.tvCategory2.setText(a.getArticleCategories().toString());
            holder.tvDate2.setText(a.getArticleDate());
            Glide.with(MainActivity.getAppContext()).load(a.getPictureLink()).into(holder.ivPicture2);
        }
    }


    @Override
    public int getItemViewType(int position) {
        // Today news will be diferent from others
        // TODO - finsih method
        if (position < 3)
            return 0;
        if(position==3)
            return 1;
        else {
            return 2;
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

        private ImageView ivPicture,ivPicture2;
        private TextView tvCategory, tvTitle, tvDate;
        private TextView tvCategory2,tvTitle2, tvDate2;
        private RelativeLayout rlCardView;
        private ViewHolderClickListener viewHolderClickListener;

        MyViewHolder(View v, ViewHolderClickListener viewHolderClickListener) {
            super(v);
            this.viewHolderClickListener = viewHolderClickListener;
            rlCardView = (RelativeLayout) v.findViewById(R.id.card_view);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvCategory = (TextView) v.findViewById(R.id.tvCategory);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            ivPicture = (ImageView) v.findViewById(R.id.ivPicture);
            tvTitle2 = (TextView) v.findViewById(R.id.tvTitle2);
            tvCategory2 = (TextView) v.findViewById(R.id.tvCategory2);
            tvDate2 = (TextView) v.findViewById(R.id.tvDate2);
            ivPicture2 = (ImageView) v.findViewById(R.id.ivPicture2);

            v.setOnClickListener(this);
        }
    }
}
