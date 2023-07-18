package com.example.unitedmania.ui.news;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.unitedmania.R;
import com.example.unitedmania.adapter.NewsAdapter;
import com.example.unitedmania.databinding.FragmentNewsBinding;
import com.example.unitedmania.model.News;
import com.example.unitedmania.model.state.Error;
import com.example.unitedmania.model.state.Loading;
import com.example.unitedmania.model.state.Success;

import java.util.List;

public class NewsFragment extends Fragment implements NewsArticleClickCallback {

    private NewsViewModel viewModel;
    private FragmentNewsBinding binding;
    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Latest News");
        binding = FragmentNewsBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        prepareUi();
        viewModel.fetchNews();

        viewModel.getNewsList().observe(getViewLifecycleOwner(), newsState -> {
            if (newsState instanceof Loading) {
                showLoadingUi();
            } else if (newsState instanceof Error) {
                String error = newsState.getError();
                showErrorUi(error);
            } else if (newsState instanceof Success) {
                List<News> news = newsState.getData();
                showNewsUi(news);
            }
        });
    }

    @Override
    public void onNewsArticleClicked(News currentNews) {
        NavDirections action = NewsFragmentDirections.actionNewsFragmentToDetailsFragment(
                currentNews.getSource().getName(),
                currentNews.getTitle(),
                currentNews.getDetails(),
                currentNews.getUrl(),
                currentNews.getImageUrl()
                );
        Navigation.findNavController(getView()).navigate(action);
    }

    private void prepareUi(){
        prepareRefresher();
        prepareNewsList();
    }

    private void prepareRefresher(){
        binding.refresher.setColorSchemeResources(R.color.background);
        binding.refresher.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            viewModel.fetchNews();
            binding.refresher.setRefreshing(false);
        }, 1000));
    }

    private void prepareNewsList(){
        newsAdapter = new NewsAdapter(this);
        binding.list.setAdapter(newsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.list.setLayoutManager(layoutManager);
        binding.list.setHasFixedSize(true);
    }

    private void showLoadingUi(){
        binding.loadingSpinner.setVisibility(View.VISIBLE);
        binding.noResults.setVisibility(View.INVISIBLE);
        binding.list.setVisibility(View.INVISIBLE);
    }

    private void showErrorUi(String error){
        binding.loadingSpinner.setVisibility(View.INVISIBLE);
        binding.noResults.setVisibility(View.VISIBLE);
        binding.list.setVisibility(View.INVISIBLE);
        binding.noResults.setText(error);
    }

    private void showNewsUi(List<News> news){
        binding.loadingSpinner.setVisibility(View.INVISIBLE);
        binding.noResults.setVisibility(View.INVISIBLE);
        binding.list.setVisibility(View.VISIBLE);
        newsAdapter.setNews(news);
    }
}