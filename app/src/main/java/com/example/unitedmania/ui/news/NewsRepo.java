package com.example.unitedmania.ui.news;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.unitedmania.model.News;
import java.util.List;

import com.example.unitedmania.Retrofit.RetrofitClient;
import com.example.unitedmania.Retrofit.RetrofitServiceInterface;
import com.example.unitedmania.model.NewsResponse;
import com.example.unitedmania.model.state.Error;
import com.example.unitedmania.model.state.FetchState;
import com.example.unitedmania.model.state.Loading;
import com.example.unitedmania.model.state.Success;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepo {

    void fetchNews (MutableLiveData<FetchState> news) {
        news.postValue(new Loading());
        RetrofitServiceInterface serviceInterface = RetrofitClient.getClient().create(RetrofitServiceInterface.class);
        Call<NewsResponse> call = serviceInterface.getNews();
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                List<News> newsList = response.body().getArticles();
                news.postValue(new Success(newsList));
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {
                news.postValue(new Error(t.toString()));
            }
        });
    }
}
