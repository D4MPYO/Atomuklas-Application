package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Concept extends AppCompatActivity {
    private ImageView backButton;
    private CardView astroCard, historyCard, cometsCard, asteroidsCard, meteorsCard, importanceCard; // Declare CardView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.concepts);

        // Enable full immersive mode
        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                        | android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        // Back Button Click Event
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Find CometsCard and set ClickListener
        astroCard = findViewById(R.id.astroCard);
        astroCard.setOnClickListener(v -> {
            Intent intent = new Intent(Concept.this,  ConceptIntroAstronomy.class); // Replace 'Concept' with your actual Java activity name
            startActivity(intent);
        });

        historyCard = findViewById(R.id.historyCard);
        historyCard.setOnClickListener(v -> {
            Intent intent = new Intent(Concept.this,  ConceptComets.class); // Replace 'Concept' with your actual Java activity name
            startActivity(intent);
        });

        cometsCard = findViewById(R.id.CometsCard);
        cometsCard.setOnClickListener(v -> {
            Intent intent = new Intent(Concept.this,  ConceptComets.class); // Replace 'Concept' with your actual Java activity name
            startActivity(intent);
        });

        asteroidsCard = findViewById(R.id.asteroidsCard);
        asteroidsCard.setOnClickListener(v -> {
            Intent intent = new Intent(Concept.this,  ConceptAsteroids.class); // Replace 'Concept' with your actual Java activity name
            startActivity(intent);
        });

        meteorsCard = findViewById(R.id.meteorsCard);
        meteorsCard.setOnClickListener(v -> {
            Intent intent = new Intent(Concept.this,  ConceptMeteors.class); // Replace 'Concept' with your actual Java activity name
            startActivity(intent);
        });

        importanceCard = findViewById(R.id.importanceCard);
        importanceCard.setOnClickListener(v -> {
            Intent intent = new Intent(Concept.this,  ConceptComets.class); // Replace 'Concept' with your actual Java activity name
            startActivity(intent);
        });
    }
}
