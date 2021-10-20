package com.example.unitedmania.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.unitedmania.R;
import com.example.unitedmania.adapter.NewsAdapter;
import com.example.unitedmania.databinding.ActivityNewsBinding;
import com.example.unitedmania.model.News;
import com.example.unitedmania.model.state.Error;
import com.example.unitedmania.model.state.FetchState;
import com.example.unitedmania.model.state.Loading;
import com.example.unitedmania.model.state.Success;
import com.example.unitedmania.ui.DetailsActivity;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements NewsArticleClickCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityNewsBinding binding = ActivityNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NewsViewModel viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding.refresher.setColorSchemeResources(R.color.background);
        binding.refresher.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            viewModel.fetchNews();
            binding.refresher.setRefreshing(false);
        }, 1000));

        binding.list.setHasFixedSize(true);
        // set recyclerview's adapter and layout manager
        NewsAdapter newsAdapter = new NewsAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.list.setAdapter(newsAdapter);
        binding.list.setLayoutManager(layoutManager);

        viewModel.fetchNews();
        viewModel.getNewsList().observe(this, (Observer<FetchState>) news -> {
            if (news instanceof Loading){
                binding.loadingSpinner.setVisibility(View.VISIBLE);
                binding.noResults.setVisibility(View.INVISIBLE);
                binding.list.setVisibility(View.INVISIBLE);
            }
            else if (news instanceof Error){
                binding.loadingSpinner.setVisibility(View.INVISIBLE);
                binding.noResults.setVisibility(View.VISIBLE);
                binding.list.setVisibility(View.INVISIBLE);
                binding.noResults.setText(news.getError());
            }
            else if (news instanceof Success){
                binding.loadingSpinner.setVisibility(View.INVISIBLE);
                binding.noResults.setVisibility(View.INVISIBLE);
                binding.list.setVisibility(View.VISIBLE);
                newsAdapter.setNews((List<News>) news.getData());
            }
        });
    }

    @Override
    public void onNewsArticleClicked(News currentNews) {
        Intent toDetails = new Intent(this, DetailsActivity.class);
        toDetails.putExtra("source", currentNews.getSource().getName());
        toDetails.putExtra("title", currentNews.getTitle());
        toDetails.putExtra("details", currentNews.getDetails());
        toDetails.putExtra("url", currentNews.getUrl());
        toDetails.putExtra("imageUrl", currentNews.getImageUrl());
        startActivity(toDetails);
    }
}
