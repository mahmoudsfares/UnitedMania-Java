package com.example.unitedmania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static androidx.core.content.ContextCompat.startActivity;

public class DetailsActivity extends AppCompatActivity {

    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mUri = null;

        TextView sourceTv = findViewById(R.id.details_source);
        TextView titleTv = findViewById(R.id.details_title);
        TextView detailsTv = findViewById(R.id.details);
        TextView fullArticle = findViewById(R.id.details_full_article);


        Intent i = getIntent();

        Bundle extras = i.getExtras();

        String source = extras.getString("source");
        String title = extras.getString("title");
        String details = extras.getString("details");

        int detailsStopPosition = details.indexOf("[+");
        if(detailsStopPosition != -1)
            details = details.substring(0,detailsStopPosition);

        mUri = Uri.parse(extras.getString("uri"));

        sourceTv.setText(source);
        titleTv.setText(title);
        detailsTv.setText(details);

        fullArticle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, mUri);
                startActivity(i);
            }
        });


    }
}
