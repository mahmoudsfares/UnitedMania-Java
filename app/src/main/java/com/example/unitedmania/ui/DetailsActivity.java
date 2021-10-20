package com.example.unitedmania.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.unitedmania.R;
import com.example.unitedmania.databinding.ActivityDetailsBinding;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailsBinding binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i = getIntent();

        Bundle extras = i.getExtras();

        String source = extras.getString("source");
        String title = extras.getString("title");
        String details = extras.getString("details");
        Uri uri = Uri.parse(extras.getString("url"));
        String imageUrl = extras.getString("imageUrl");

        int detailsStopPosition = details.indexOf("[+");
        if(detailsStopPosition != -1)
            details = details.substring(0,detailsStopPosition);

        Picasso.get()
                .load(imageUrl)
                .fit()
                .placeholder(R.drawable.placeholder)
                .into(binding.detailsImage);

        binding.detailsSource.setText(source);
        binding.detailsTitle.setText(title);
        binding.details.setText(details);

        binding.detailsFullArticle.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, uri)));
    }
}
