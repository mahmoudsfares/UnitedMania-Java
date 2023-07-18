package com.example.unitedmania.ui.news;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.unitedmania.model.News;
import com.example.unitedmania.model.state.FetchState;

import java.util.List;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<FetchState<List<News>>> newsList = new MutableLiveData<>();
    private final NewsRepo repo = new NewsRepo();

    public MutableLiveData<FetchState<List<News>>> getNewsList() {
        return newsList;
    }

    void fetchNews() { repo.fetchNews(newsList); }
}
