package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Quizzes extends AppCompatActivity {
    private ImageView backButton, concept_img1, concept_img2, concept_img3, concept_img4, navProfileAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.quizzez_v2); // Ensure this is the correct layout file

        // Initialize components
        navProfileAvatar = findViewById(R.id.navProfileAvatar);
        loadUserAvatar(); // Load the selected avatar

        // Enable full immersive mode
        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                        | android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        // Initialize buttons
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish()); // Close activity when clicked

        concept_img1 = findViewById(R.id.concept_img1);
        concept_img1.setOnClickListener(v -> startActivity(new Intent(Quizzes.this, QuizEasy.class)));

        concept_img2 = findViewById(R.id.concept_img2);
        concept_img2.setOnClickListener(v -> startActivity(new Intent(Quizzes.this, QuizIntermidiate.class)));

        concept_img3 = findViewById(R.id.concept_img3);
        concept_img3.setOnClickListener(v -> startActivity(new Intent(Quizzes.this, QuizAdvance.class)));

//        concept_img4 = findViewById(R.id.concept_img4);
//        concept_img4.setOnClickListener(v -> startActivity(new Intent(Quizzes.this, QuizBonus.class)));

        // Setup bottom navigation
        setupBottomNavbar();
    }

    // Load selected avatar from SharedPreferences
    private void loadUserAvatar() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int selectedAvatarId = preferences.getInt("SELECTED_AVATAR", R.drawable.avatar_icon); // Default avatar
        navProfileAvatar.setImageResource(selectedAvatarId); // Set the avatar image
    }

    private void setupBottomNavbar() {
        findViewById(R.id.navQuiz).setOnClickListener(v -> {
            if (!(Quizzes.this instanceof Quizzes)) {
                navigateToActivity(Quizzes.class);
            }
        });
        findViewById(R.id.navHome).setOnClickListener(v -> navigateToActivity(MainActivity.class));
        findViewById(R.id.navLibrary).setOnClickListener(v -> navigateToActivity(Library.class));
        findViewById(R.id.navAccomplishment).setOnClickListener(v -> navigateToActivity(Accomplishment.class));
        findViewById(R.id.navProfileAvatar).setOnClickListener(v -> navigateToActivity(Profile.class));
    }

    private void navigateToActivity(Class<?> targetActivity) {
        Intent intent = new Intent(Quizzes.this, targetActivity);
        startActivity(intent);
        finish(); // Optional: Prevents stacking multiple activities
    }
}
