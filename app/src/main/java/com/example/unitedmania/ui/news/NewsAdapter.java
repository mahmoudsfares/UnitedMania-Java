package com.example.unitedmania.ui.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unitedmania.databinding.NewsItemBinding;
import com.example.unitedmania.model.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    final private NewsArticleClickCallback newsArticleClickCallback;
    private List<News> mNewsList;

    public NewsAdapter(NewsArticleClickCallback newsArticleClickCallback) {
        this.newsArticleClickCallback = newsArticleClickCallback;
    }

    @NonNull
    @Override
    public NewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        NewsItemBinding binding = NewsItemBinding.inflate(inflater, parent, false);
        return new NewsAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapterViewHolder holder, int position) {
        News currentNews = mNewsList.get(position);
        holder.binding.newsSource.setText(currentNews.getSource().getName());
        holder.binding.newsTitle.setText(currentNews.getTitle());
    }

    @Override
    public int getItemCount() {
        if (mNewsList == null)
            return 0;
        return mNewsList.size();
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder {

        final NewsItemBinding binding;

        public NewsAdapterViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                newsArticleClickCallback.onNewsArticleClicked(mNewsList.get(position));
            });
        }
    }

    // populates data arraylist with the fetched data arraylist
    public void setNews(List<News> news){
        mNewsList = news;
        notifyDataSetChanged();
    }
}
