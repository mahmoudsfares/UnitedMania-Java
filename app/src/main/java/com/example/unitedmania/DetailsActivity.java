package com.example.unitedmania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView titleTv = findViewById(R.id.details_title);
        TextView detailsTv = findViewById(R.id.details);

        Intent i = getIntent();

        Bundle extras = i.getExtras();

        String title = extras.getString("title");
        String details = extras.getString("details");

        titleTv.setText(title);
        detailsTv.setText(details);
    }
}
