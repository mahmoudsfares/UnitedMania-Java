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

    private FragmentNewsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Latest News");

        NewsViewModel viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding.refresher.setColorSchemeResources(R.color.background);
        binding.refresher.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            viewModel.fetchNews();
            binding.refresher.setRefreshing(false);
        }, 1000));

        binding.list.setHasFixedSize(true);
        NewsAdapter newsAdapter = new NewsAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.list.setAdapter(newsAdapter);
        binding.list.setLayoutManager(layoutManager);

        if(viewModel.getNewsList().getValue() instanceof Success){
            binding.loadingSpinner.setVisibility(View.INVISIBLE);
            binding.noResults.setVisibility(View.INVISIBLE);
            binding.list.setVisibility(View.VISIBLE);
            newsAdapter.setNews((List<News>) viewModel.getNewsList().getValue().getData());
        }
        else {
            viewModel.fetchNews();
        }

        viewModel.getNewsList().observe(getViewLifecycleOwner(), news -> {
            if (news instanceof Loading) {
                binding.loadingSpinner.setVisibility(View.VISIBLE);
                binding.noResults.setVisibility(View.INVISIBLE);
                binding.list.setVisibility(View.INVISIBLE);
            } else if (news instanceof Error) {
                binding.loadingSpinner.setVisibility(View.INVISIBLE);
                binding.noResults.setVisibility(View.VISIBLE);
                binding.list.setVisibility(View.INVISIBLE);
                binding.noResults.setText(news.getError());
            } else if (news instanceof Success) {
                binding.loadingSpinner.setVisibility(View.INVISIBLE);
                binding.noResults.setVisibility(View.INVISIBLE);
                binding.list.setVisibility(View.VISIBLE);
                newsAdapter.setNews((List<News>) news.getData());
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
}