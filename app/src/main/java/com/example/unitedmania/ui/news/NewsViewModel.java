package com.example.unitedmania.ui.news;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.unitedmania.model.state.FetchState;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<FetchState> newsList = new MutableLiveData<>();
    private final NewsRepo repo = new NewsRepo();

    public MutableLiveData<FetchState> getNewsList() {
        return newsList;
    }

    void fetchNews () {
        repo.fetchNews(newsList);
    }
}
