package com.example.unitedmania.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.unitedmania.R;
import com.example.unitedmania.databinding.FragmentDetailsBinding;
import com.squareup.picasso.Picasso;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;

    private DetailsFragmentArgs args;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Details");
        binding = FragmentDetailsBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        args = DetailsFragmentArgs.fromBundle(getArguments());
        setUi();

        binding.detailsFullArticle.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(args.getUrl()))));
    }

    private void setUi(){
        Picasso.get()
                .load(args.getImageUrl())
                .fit()
                .placeholder(R.drawable.placeholder)
                .into(binding.detailsImage);
        binding.detailsSource.setText(args.getSource());
        binding.detailsTitle.setText(args.getTitle());
        binding.details.setText(formatNewsDetails(args.getDetails()));
    }

    private String formatNewsDetails(String newsDetails) {
        int detailsStopPosition = newsDetails.indexOf("[+");
        if (detailsStopPosition != -1)
            return newsDetails.substring(0, detailsStopPosition);
        else
            return newsDetails;
    }
}