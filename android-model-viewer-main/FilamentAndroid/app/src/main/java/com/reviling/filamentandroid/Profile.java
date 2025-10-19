package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Profile extends AppCompatActivity {

    private ImageView navProfileAvatar;
    private ImageView navProfileAvatar2; // New ImageView
    private TextView textView;
    private ImageView backButton;
    private CardView Badges, Scores, Downloads, Settings;

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

        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                        | android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        setContentView(R.layout.profile);

        // Initialize views
        navProfileAvatar = findViewById(R.id.navProfileAvatar);
        navProfileAvatar2 = findViewById(R.id.navProfileAvatar2); // Initialize new ImageView
        textView = findViewById(R.id.textView);
        backButton = findViewById(R.id.backButton);


        // Initialize buttons after setting the layout
        Badges = findViewById(R.id.Badges);
        Scores = findViewById(R.id.Scores);
//        Downloads = findViewById(R.id.Downloads);
        Settings = findViewById(R.id.Settings);

        // Button Click Listeners
        Badges.setOnClickListener(v -> navigateToActivity(Accomplishment.class)); // Change to correct activity
        Scores.setOnClickListener(v -> navigateToActivity(Scores.class));
//        Downloads.setOnClickListener(v -> navigateToActivity(Worksheets.class));
        Settings.setOnClickListener(v -> navigateToActivity(SamplePopUp.class)); // Change to correct activity

        // Load user data from SharedPreferences
        loadUserData();

        // Set OnClickListener for the back button
        backButton.setOnClickListener(v -> finish()); // Close this activity and return to the previous one

        setupBottomNavbar();

    }

    private void loadUserData() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userName = preferences.getString("USER_NAME", "Guest");
        int avatarId = preferences.getInt("SELECTED_AVATAR", R.drawable.avatar_icon); // Default avatar

        // Set user name and avatar in UI
        textView.setText("Hello, " + userName + "!");
        navProfileAvatar.setImageResource(avatarId);
        navProfileAvatar2.setImageResource(avatarId); // Set the same avatar for the second ImageView
    }

    private void setupBottomNavbar() {
        findViewById(R.id.navProfileAvatar2).setOnClickListener(v -> {
            if (!(Profile.this instanceof Profile)) {
                navigateToActivity(Profile.class);
            }
        });
        findViewById(R.id.navHome).setOnClickListener(v -> navigateToActivity(MainActivity.class));
        findViewById(R.id.navQuiz).setOnClickListener(v -> navigateToActivity(Quizzes.class));
        findViewById(R.id.navLibrary).setOnClickListener(v -> navigateToActivity(Library.class));
        findViewById(R.id.navAccomplishment).setOnClickListener(v -> navigateToActivity(Accomplishment.class));
    }

    private void navigateToActivity(Class<?> targetActivity) {
        Intent intent = new Intent(Profile.this, targetActivity);
        startActivity(intent);
        finish(); // Optional: Prevents stacking multiple activities
    }
}
