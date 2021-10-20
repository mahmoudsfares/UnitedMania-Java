package com.example.unitedmania.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.unitedmania.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}