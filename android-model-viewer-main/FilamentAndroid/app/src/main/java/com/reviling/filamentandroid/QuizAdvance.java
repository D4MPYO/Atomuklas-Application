package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizAdvance extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // ✅ Use the correct layout file
        setContentView(R.layout.quiz_advance);

        // Hide the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Enable full immersive mode
        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                        | android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        // Force portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Initialize the back button
        ImageView backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        // Initialize Take Exam button
        Button takeExamButton = findViewById(R.id.TakeExam);
        if (takeExamButton != null) {
            // Load and start animation
            Animation buttonAnimation = AnimationUtils.loadAnimation(this, R.anim.button_animation);
            takeExamButton.startAnimation(buttonAnimation);

            // Set OnClickListener for Take Exam button
            takeExamButton.setOnClickListener(v -> {
                Intent intent = new Intent(QuizAdvance.this, StartAdvanceQuiz.class);
                startActivity(intent);
            });
        }
    }
}
