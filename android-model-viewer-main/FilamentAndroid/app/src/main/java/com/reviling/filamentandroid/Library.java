package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Library extends AppCompatActivity {
    private ImageView backButton, navProfileAvatar;
    private Button modules_btn, atomicTrivia_btn, atomicQuest_btn, viewModels_btn, quizzes_btn, worksheet_btn;
    private int selectedAvatarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.library);

        // Initialize components
        navProfileAvatar = findViewById(R.id.navProfileAvatar);
        modules_btn = findViewById(R.id.modules_btn);
        atomicTrivia_btn = findViewById(R.id.atomicTrivia_btn);
        atomicQuest_btn = findViewById(R.id.atomicQuest_btn);
        viewModels_btn = findViewById(R.id.viewModels_btn);
        quizzes_btn = findViewById(R.id.quizzes_btn);
        worksheet_btn = findViewById(R.id.worksheet_btn);

        // Load avatar from SharedPreferences
        loadUserAvatar();

        // Button Click Listeners
        modules_btn.setOnClickListener(v -> navigateToActivity(LearningModules.class));
        atomicTrivia_btn.setOnClickListener(v -> navigateToActivity(Trivia_New.class));
        atomicQuest_btn.setOnClickListener(v -> navigateToActivity(AutomaniaGames.class));
        viewModels_btn.setOnClickListener(v -> navigateToActivity(ViewModels.class));
        quizzes_btn.setOnClickListener(v -> navigateToActivity(Quizzes.class));
        worksheet_btn.setOnClickListener(v -> navigateToActivity(Worksheets2.class));

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        setupBottomNavbar();
    }

    private void loadUserAvatar() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        selectedAvatarId = preferences.getInt("SELECTED_AVATAR", R.drawable.avatar_icon); // Default avatar
        navProfileAvatar.setImageResource(selectedAvatarId);
    }

    private void setupBottomNavbar() {
        findViewById(R.id.navLibrary).setOnClickListener(v -> {
            if (!(Library.this instanceof Library)) {
                navigateToActivity(Library.class);
            }
        });
        findViewById(R.id.navHome).setOnClickListener(v -> navigateToActivity(MainActivity.class));
        findViewById(R.id.navQuiz).setOnClickListener(v -> navigateToActivity(Quizzes.class));
        findViewById(R.id.navAccomplishment).setOnClickListener(v -> navigateToActivity(Accomplishment.class));
        findViewById(R.id.navProfileAvatar).setOnClickListener(v -> navigateToActivity(Profile.class));
    }

    private void navigateToActivity(Class<?> destination) {
        Intent intent = new Intent(this, destination);
        startActivity(intent);
    }
}
