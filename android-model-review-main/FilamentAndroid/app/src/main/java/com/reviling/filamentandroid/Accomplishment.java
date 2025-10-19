package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class Accomplishment extends AppCompatActivity {

    private ImageView backButton, navProfileAvatar;
    private ImageView badgeEasy, badgeIntermediate, badgeAdvance, badgeBonus, badgeExam;
    private LottieAnimationView confettiAnim;
    private TextView progressPercentageText;
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

        setContentView(R.layout.accomplishment);

        navProfileAvatar = findViewById(R.id.navProfileAvatar);
        badgeEasy = findViewById(R.id.BadgeEasy);
        badgeIntermediate = findViewById(R.id.BadgeIntermediate);
        badgeAdvance = findViewById(R.id.BadgeAdvance);
        badgeBonus = findViewById(R.id.BadgeBonus);
        badgeExam = findViewById(R.id.BadgeExam);
        confettiAnim = findViewById(R.id.confetti_anim);
        progressPercentageText = findViewById(R.id.imageView5);

        if (confettiAnim != null) {
            confettiAnim.setVisibility(View.GONE);
        }

        loadUserAvatar();
        loadBadgeStatus();
        setupCheckboxes();

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

    private void setupCheckboxes() {
        CheckBox cb1 = findViewById(R.id.rememberCheckBox);
        CheckBox cb2 = findViewById(R.id.rememberCheckBox2);
        CheckBox cb3 = findViewById(R.id.rememberCheckBox3);
        CheckBox cb4 = findViewById(R.id.rememberCheckBox4);
        CheckBox cb5 = findViewById(R.id.rememberCheckBox5);
        CheckBox cb6 = findViewById(R.id.rememberCheckBox6);

        SharedPreferences prefs = getSharedPreferences("CheckboxPrefs", MODE_PRIVATE);

        cb1.setChecked(prefs.getBoolean("cb1", false));
        cb2.setChecked(prefs.getBoolean("cb2", false));
        cb3.setChecked(prefs.getBoolean("cb3", false));
        cb4.setChecked(prefs.getBoolean("cb4", false));
        cb5.setChecked(prefs.getBoolean("cb5", false));
        cb6.setChecked(prefs.getBoolean("cb6", false));

        cb1.setOnCheckedChangeListener((buttonView, isChecked) -> prefs.edit().putBoolean("cb1", isChecked).apply());
        cb2.setOnCheckedChangeListener((buttonView, isChecked) -> prefs.edit().putBoolean("cb2", isChecked).apply());
        cb3.setOnCheckedChangeListener((buttonView, isChecked) -> prefs.edit().putBoolean("cb3", isChecked).apply());
        cb4.setOnCheckedChangeListener((buttonView, isChecked) -> prefs.edit().putBoolean("cb4", isChecked).apply());
        cb5.setOnCheckedChangeListener((buttonView, isChecked) -> prefs.edit().putBoolean("cb5", isChecked).apply());
        cb6.setOnCheckedChangeListener((buttonView, isChecked) -> prefs.edit().putBoolean("cb6", isChecked).apply());
    }

    private void loadUserAvatar() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        selectedAvatarId = preferences.getInt("SELECTED_AVATAR", R.drawable.avatar_icon);
        navProfileAvatar.setImageResource(selectedAvatarId);
    }

    private void loadBadgeStatus() {
        SharedPreferences badgePrefs = getSharedPreferences("BadgePrefs", MODE_PRIVATE);

        boolean badgeEasyUnlocked = badgePrefs.getBoolean("badge_easy_unlocked", false);
        boolean badgeIntermediateUnlocked = badgePrefs.getBoolean("badge_intermediate_unlocked", false);
        boolean badgeAdvanceUnlocked = badgePrefs.getBoolean("badge_advance_unlocked", false);
        boolean badgeBonusUnlocked = badgePrefs.getBoolean("badge_bonus_unlocked", false);
        boolean badgeExamUnlocked = badgePrefs.getBoolean("badge_exam_unlocked", false);

        int unlockedBadgesCount = 0;

        if (badgeEasyUnlocked && badgeEasy != null) {
            badgeEasy.setImageResource(R.drawable.badge_easy);
            unlockedBadgesCount++;
        }

        if (badgeIntermediateUnlocked && badgeIntermediate != null) {
            badgeIntermediate.setImageResource(R.drawable.badge_intermediate);
            unlockedBadgesCount++;
        }

        if (badgeAdvanceUnlocked && badgeAdvance != null) {
            badgeAdvance.setImageResource(R.drawable.badge_advance);
            unlockedBadgesCount++;
        }

        if (badgeBonusUnlocked && badgeBonus != null) {
            badgeBonus.setImageResource(R.drawable.badge_bonus);
            // Do NOT include in percentage
        }

        if (badgeExamUnlocked && badgeExam != null) {
            badgeExam.setImageResource(R.drawable.badge_exam);
            // Do NOT include in percentage
        }

        // Only 3 badges are included in the progress: Easy, Intermediate, Advance
        updateProgressPercentage(unlockedBadgesCount, 3);

        if (unlockedBadgesCount > 0 && confettiAnim != null) {
            confettiAnim.setVisibility(View.VISIBLE);
            confettiAnim.playAnimation();
        }
    }

    private void updateProgressPercentage(int unlockedBadgesCount, int totalBadges) {
        int percentage = (unlockedBadgesCount * 100) / totalBadges;
        progressPercentageText.setText(percentage + "%");
    }

    private void setupBottomNavbar() {
        findViewById(R.id.navAccomplishment).setOnClickListener(v -> {
            if (!(Accomplishment.this instanceof Accomplishment)) {
                navigateToActivity(Accomplishment.class);
            }
        });
        findViewById(R.id.navHome).setOnClickListener(v -> navigateToActivity(MainActivity.class));
        findViewById(R.id.navQuiz).setOnClickListener(v -> navigateToActivity(Quizzes.class));
        findViewById(R.id.navLibrary).setOnClickListener(v -> navigateToActivity(Library.class));
        findViewById(R.id.navProfileAvatar).setOnClickListener(v -> navigateToActivity(Profile.class));
    }

    private void navigateToActivity(Class<?> targetActivity) {
        Intent intent = new Intent(Accomplishment.this, targetActivity);
        startActivity(intent);
        finish();
    }
}
