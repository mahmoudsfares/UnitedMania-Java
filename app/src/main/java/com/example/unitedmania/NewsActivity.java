package com.example.unitedmania;

import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
        private static final String REQUEST_URL =
        "https://newsapi.org/v2/everything?q=manchester%20united%7Cman%20utd%7Cman%20united%7Cmanchester%20utd&apiKey=47ba773d0f1147438a3d6244bc7f1e5e&sortBy=publishedAt&pageSize=100&language=en&fbclid=IwAR215STnwzrUekxittTkbK3Vn8INjsOE0Zl28uctn2lDwpOelkKVurJvWwc";


        public static final String LOG_TAG = NewsActivity.class.getName();
        //CListView for custom listItem that contains news
        private ListView newsListView;
        //Custom adapter for newsListView
        private NewsAdapter mNAdapter;
        //Appears when there's no data to be retrieved
        private TextView emptyState;
        //Loading spinner for while the connection and parsing are in progress
        private ProgressBar progressBar;
        //Refresh Button
        private Button refreshButton;
        //Counter that triggers a new loader on each click
        private int loaderTrigger = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_news);

                //Declaration & initial visibility status for different cases' views
                progressBar = findViewById(R.id.loading_spinner);
                progressBar.setVisibility(View.VISIBLE);
                emptyState = findViewById(R.id.no_results);
                emptyState.setVisibility(View.GONE);
                newsListView = findViewById(R.id.list);
                emptyState.setVisibility(View.GONE);

                refreshButton = findViewById(R.id.refresh_button);

                //If there's a valid internet connection:
                if (getNetworkInfo() != null){
                getLoaderManager().initLoader(0, null, this);
                }
                //Show the noInternet Toast if else:
                else {
                    Toast noInternet = Toast.makeText(this, "No internet, check connection then refresh.",Toast.LENGTH_LONG);
                    noInternet.show();
                    progressBar.setVisibility(View.GONE);
                    emptyState.setVisibility(View.GONE);
                }

                refreshButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                                progressBar.setVisibility(View.VISIBLE);
                                emptyState.setVisibility(View.GONE);
                                newsListView.setVisibility(View.GONE);

                                //Increment loaderTrigger by 1 each time the button is clicked
                                loaderTrigger++;

                                //Initiate a new loader if there's a valid internet connection:
                                if (getNetworkInfo() != null){
                                        getLoaderManager().initLoader(loaderTrigger, null, NewsActivity.this);
                                }
                                //Show the noInternet Toast if else:
                                else {
                                        Toast noInternet = Toast.makeText(NewsActivity.this, "No internet, check connection then refresh.",Toast.LENGTH_LONG);
                                        noInternet.show();
                                        progressBar.setVisibility(View.GONE);
                                        emptyState.setVisibility(View.GONE);
                                }
                        }
                });
        }

        //Checks the connectivity status
        private NetworkInfo getNetworkInfo(){
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                return connectivityManager.getActiveNetworkInfo();
        }


        public void updateUi(List<News> news) {
                //Showing only emptyState TextView when the news array is empty
                if (news.isEmpty()) {
                progressBar.setVisibility(View.GONE);
                emptyState.setVisibility(View.VISIBLE);
                newsListView.setVisibility(View.GONE);
                }
                //Showing only newsListView when the news array isn't empty
                //Setting the adapter to the customAdapter mAdapter to handle the requested data
                else {
                progressBar.setVisibility(View.GONE);
                emptyState.setVisibility(View.GONE);
                newsListView.setVisibility(View.VISIBLE);
                mNAdapter = new NewsAdapter(this, R.layout.news_item, news);
                newsListView.setAdapter(mNAdapter);
                }
                }

        @Override
        public Loader<ArrayList<News>> onCreateLoader(int i, Bundle bundle) {
                return new NewsLoader(NewsActivity.this, REQUEST_URL);
        }

        //Update the UI after retrieving the requested News data
        @Override
        public void onLoadFinished(android.content.Loader<ArrayList<News>> loader, ArrayList<News> news) {
                updateUi(news);
        }

        //Clear the data after the loader is reset
        @Override
        public void onLoaderReset(android.content.Loader<ArrayList<News>> loader) {
                mNAdapter.clear();
        }








}

