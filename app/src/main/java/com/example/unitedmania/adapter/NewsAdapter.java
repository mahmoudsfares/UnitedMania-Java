package com.example.unitedmania.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.unitedmania.model.News;
import com.example.unitedmania.R;
import com.example.unitedmania.ui.news.NewsArticleClickCallback;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    final private NewsArticleClickCallback newsArticleClickCallback;
    // holds fetched data, set by setNews() method
    private List<News> mNewsList;
    // current news item
    News currentNews;

    public NewsAdapter(NewsArticleClickCallback newsArticleClickCallback) {
        this.newsArticleClickCallback = newsArticleClickCallback;
    }

    // inflate the list item layout as a viewholder
    @NonNull
    @Override
    public NewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        int layoutIdForListItem = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new NewsAdapterViewHolder(view);
    }

    // set source and title TextViews wrt position in the data arraylist
    @Override
    public void onBindViewHolder(@NonNull NewsAdapterViewHolder holder, int position) {
        currentNews = mNewsList.get(position);
        holder.mSource.setText(currentNews.getSource().getName());
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
            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                currentNews = mNewsList.get(position);
                newsArticleClickCallback.onNewsArticleClicked(currentNews);
            });
        }
    }

    // populates data arraylist with the fetched data arraylist
    public void setNews(List<News> news){
        mNewsList = news;
        notifyDataSetChanged();
    }
}
