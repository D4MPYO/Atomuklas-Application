package com.reviling.filamentandroid;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = findViewById(R.id.detailImageView);
        int imageResId = getIntent().getIntExtra("IMAGE_RES_ID", -1);

        if (imageResId != -1) {
            imageView.setImageResource(imageResId);
        }
    }
}
