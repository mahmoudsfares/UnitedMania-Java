package com.example.unitedmania.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.unitedmania.DetailsActivity;
import com.example.unitedmania.News;
import com.example.unitedmania.R;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    Context mContext;
    // holds fetched data, set by setNews() method
    private ArrayList<News> mNewsList;
    // current news item
    News currentNews;
    
    // inflate the list item layout as a viewholder
    @NonNull
    @Override
    public NewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        final View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new NewsAdapterViewHolder(view);
    }

    // set source and title TextViews wrt position in the data arraylist
    @Override
    public void onBindViewHolder(@NonNull NewsAdapterViewHolder holder, int position) {
        currentNews = mNewsList.get(position);
        holder.mSource.setText(currentNews.getSource());
        holder.mTitle.setText(currentNews.getTitle());
    }

    @Override
    public int getItemCount() {
        if (mNewsList == null)
            return 0;
        return mNewsList.size();
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView mSource;
        public TextView mTitle;

        public NewsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mSource = itemView.findViewById(R.id.news_source);
            mTitle = itemView.findViewById(R.id.news_title);

            // go to the details of the news when it's clicked on
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    currentNews = mNewsList.get(position);
                    Intent toDetails = new Intent(mContext, DetailsActivity.class);
                    toDetails.putExtra("source", currentNews.getSource());
                    toDetails.putExtra("title", currentNews.getTitle());
                    toDetails.putExtra("details", currentNews.getDetails());
                    toDetails.putExtra("url", currentNews.getUrl());
                    toDetails.putExtra("imageUrl", currentNews.getImageUrl());
                    mContext.startActivity(toDetails);
                }
            });
        }
    }

    // populates data arraylist with the fetched data arraylist
    public void setNews(Context context, ArrayList<News> news){
        mNewsList = news;
        mContext = context;
        notifyDataSetChanged();
    }
}
