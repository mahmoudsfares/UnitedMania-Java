package com.example.unitedmania;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.unitedmania.adapter.NewsAdapter;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity{

    // refreshes data by swiping
    SwipeRefreshLayout swipeRefreshLayout;

    // list that shows news
    private RecyclerView mRecyclerView;

    // custom adapter for the recyclerview's views
    private NewsAdapter mNewsAdapter;

    //Appears when there's no data to be retrieved
    private TextView mErrorMessageDisplay;

    //Loading spinner for while the connection and parsing are in progress
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_spinner);
        mErrorMessageDisplay = (TextView) findViewById(R.id.no_results);

        swipeRefreshLayout = findViewById(R.id.refresher);
        swipeRefreshLayout.setColorSchemeResources(R.color.background);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNewsData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        // set views visibility then fetches data
        loadNewsData();

        // set recyclerview's adapter and layout manager
        mNewsAdapter = new NewsAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mNewsAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void loadNewsData() {
        showNewsDataView();
        new FetchNewsTask().execute();
    }

    private void showNewsDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    // show error message when there's a problem with retrieving data
    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    public class FetchNewsTask extends AsyncTask<String, Void, ArrayList<com.example.unitedmania.News>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressbar is shown while the api data is being fetched
            mLoadingIndicator.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected ArrayList<com.example.unitedmania.News> doInBackground(String... params) {

            try {
                ArrayList<com.example.unitedmania.News> simpleJsonNewsData = com.example.unitedmania.QueryUtils.extractNews();
                return simpleJsonNewsData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<com.example.unitedmania.News> news) {

            //after fetching, hide the progressbar
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);

            if (news != null) {
                Log.v("logmsg", news.size() + "");
                showNewsDataView();
                // use the fetched data to notify the recyclerview about the data changes
                mNewsAdapter.setNews(NewsActivity.this, news);
            } else {
                showErrorMessage();
            }
        }
    }
}

/*




*/
