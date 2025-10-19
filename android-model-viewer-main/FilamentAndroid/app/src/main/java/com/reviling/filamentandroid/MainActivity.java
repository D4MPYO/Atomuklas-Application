package com.reviling.filamentandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button examBtn1, learnmore, accomplishment_btn;
    private ImageView leftArrow, rightArrow, navProfileAvatar, settingsImg;
    private ViewPager2 discoveryCarousel;
    private TextView greetingTextView;
    private String userName;
    private int selectedAvatarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.main_home);
        enableImmersiveMode();
        initializeComponents();
        setupBottomNavbar();
    }

    private void enableImmersiveMode() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    private void initializeComponents() {
        greetingTextView = findViewById(R.id.textView);
        navProfileAvatar = findViewById(R.id.navProfileAvatar);

        examBtn1 = findViewById(R.id.exam_btn1);
        examBtn1.setOnClickListener(v -> navigateToActivity(Quizzes.class));

        accomplishment_btn = findViewById(R.id.accomplishment_btn);
        accomplishment_btn.setOnClickListener(v -> navigateToActivity(Accomplishment.class));

        learnmore = findViewById(R.id.learnmore);
        learnmore.setOnClickListener(v -> navigateToActivity(Trivia_New.class));


        settingsImg = findViewById(R.id.settingsImg);
        settingsImg.setOnClickListener(v -> navigateToActivity(SamplePopUp.class)); // Added click listener

        discoveryCarousel = findViewById(R.id.discoveryCarousel);
        leftArrow = findViewById(R.id.leftArrow);
        rightArrow = findViewById(R.id.rightArrow);

        setupDiscoveryCarousel();
        setupArrowClickListeners();
        loadUserData();
    }


    private void loadUserData() {
        // Retrieve user data from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        userName = preferences.getString("USER_NAME", null);
        selectedAvatarId = preferences.getInt("SELECTED_AVATAR", R.drawable.avatar_icon); // Default avatar

        if (userName != null) {
            greetingTextView.setText("Hello, " + userName + "!");
        } else {
            // If no username is set, redirect to avatar selection
            navigateToAvatarSelection();
        }

        navProfileAvatar.setImageResource(selectedAvatarId);

    }

    // Save user data persistently
    private void saveUserData() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (userName != null) editor.putString("USER_NAME", userName);
        editor.putInt("SELECTED_AVATAR", selectedAvatarId);
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveUserData(); // Ensure user data is saved when the activity is stopped
    }


    private void navigateToAvatarSelection() {
        Intent intent = new Intent(this, Choosing_Avatar.class);
        intent.putExtra("USER_NAME", userName);
        startActivity(intent);
    }

    private void setupBottomNavbar() {
        findViewById(R.id.navHome).setOnClickListener(v -> {
            if (!(MainActivity.this instanceof MainActivity)) {
                navigateToActivity(MainActivity.class);
            }
        });
        findViewById(R.id.navLibrary).setOnClickListener(v -> navigateToActivity(Library.class));
        findViewById(R.id.navQuiz).setOnClickListener(v -> navigateToActivity(Quizzes.class));
        findViewById(R.id.navAccomplishment).setOnClickListener(v -> navigateToActivity(Accomplishment.class));
        findViewById(R.id.navProfileAvatar).setOnClickListener(v -> navigateToActivity(Profile.class));
    }

    private void navigateToActivity(Class<?> destination) {
        Intent intent = new Intent(MainActivity.this, destination);
        startActivity(intent);
    }

    private void setupDiscoveryCarousel() {
        List<Integer> images = Arrays.asList(
                R.drawable.new_slider_1, R.drawable.new_slider_2, R.drawable.new_slider_3,
                R.drawable.new_slider_4, R.drawable.new_slider_5, R.drawable.new_slider_6
        );
        DiscoveryCarouselAdapter adapter = new DiscoveryCarouselAdapter(this, images);
        discoveryCarousel.setAdapter(adapter);
        discoveryCarousel.setPageTransformer((page, position) -> {
            float scale = 0.85f + (1 - Math.abs(position)) * 0.15f;
            page.setScaleY(scale);
        });
        discoveryCarousel.setUserInputEnabled(false);
        discoveryCarousel.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateArrowVisibility(position, adapter.getItemCount());
            }
        });
    }

    private void setupArrowClickListeners() {
        leftArrow.setOnClickListener(v -> navigateCarousel(-1));
        rightArrow.setOnClickListener(v -> navigateCarousel(1));
    }

    private void navigateCarousel(int offset) {
        int currentItem = discoveryCarousel.getCurrentItem();
        discoveryCarousel.setCurrentItem(currentItem + offset, true);
    }

    private void updateArrowVisibility(int position, int itemCount) {
        leftArrow.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        rightArrow.setVisibility(position == itemCount - 1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to close the app?")
                .setPositiveButton("Yes", (dialog, which) -> finishAffinity())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
