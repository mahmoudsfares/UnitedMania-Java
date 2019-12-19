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

import com.example.unitedmania.News;
import com.example.unitedmania.NewsAdapter;
import com.example.unitedmania.NewsLoader;
import com.example.unitedmania.R;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    private static final String REQUEST_URL =
            "https://newsapi.org/v2/everything?q=manchester%20united%7Cman%20utd%7Cman%20united%7Cmanchester%20utd&apiKey=47ba773d0f1147438a3d6244bc7f1e5e&sortBy=publishedAt&pageSize=100&language=en&fbclid=IwAR215STnwzrUekxittTkbK3Vn8INjsOE0Zl28uctn2lDwpOelkKVurJvWwc";


    public static final String LOG_TAG = com.example.unitedmania.DetailsActivity.class.getName();
    private TextView source;
    private TextView title;
    private TextView details;
    //Appears when there's no data to be retrieved
    private TextView emptyState;
    //Loading spinner for while the connection and parsing are in progress
    private ProgressBar progressBar;
    //Refresh Button in case of network failure
    private Button refreshButton;
    //Trigger counter
    private int loaderTrigger = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Declaration & initial visibility status for different cases' views
        source = findViewById(R.id.details_source);
        source.setVisibility(View.GONE);
        title = findViewById(R.id.details_title);
        title.setVisibility(View.GONE);
        details = findViewById(R.id.details);
        details.setVisibility(View.GONE);
        progressBar = findViewById(R.id.details_loading_spinner);
        progressBar.setVisibility(View.VISIBLE);
        emptyState = findViewById(R.id.details_no_results);
        emptyState.setVisibility(View.GONE);
        refreshButton = findViewById(R.id.details_refresh_button);

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
                loaderTrigger ++;
                //Initiate a new loader if there's a valid internet connection:
                if (getNetworkInfo() != null){
                    getLoaderManager().initLoader(loaderTrigger, null, com.example.unitedmania.DetailsActivity.this);
                }
                //Show the noInternet Toast if else:
                else {
                    Toast noInternet = Toast.makeText(com.example.unitedmania.DetailsActivity.this, "No internet, check connection then refresh.",Toast.LENGTH_LONG);
                    noInternet.show();
                    progressBar.setVisibility(View.GONE);
                    emptyState.setVisibility(View.GONE);
                    source.setVisibility(View.GONE);
                    title.setVisibility(View.GONE);
                    details.setVisibility(View.GONE);
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

        if (news.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            emptyState.setVisibility(View.VISIBLE);
            source.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            details.setVisibility(View.GONE);
        }

        else {
            progressBar.setVisibility(View.GONE);
            emptyState.setVisibility(View.GONE);
            source.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            details.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int i, Bundle bundle) {
        return new DetailsLoader(com.example.unitedmania.DetailsActivity.this, REQUEST_URL);
    }

    //Update the UI after retrieving the requested News data
    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<News>> loader, ArrayList<News> news) {
        updateUi(news);
    }

    //Clear the data after the loader is reset
    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<News>> loader) {

    }
}
