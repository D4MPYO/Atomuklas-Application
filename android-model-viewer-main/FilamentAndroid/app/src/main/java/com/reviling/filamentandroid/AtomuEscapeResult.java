package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AtomuEscapeResult extends AppCompatActivity {

    private TextView correctAnswerValueTextView, textText, doneExamText;
    private Button backHomeButton, reviewAnswer;

    private static final double PASSING_SCORE_PERCENT = 75.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen + portrait lock
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.atomu_escape_result);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        // Init views
        correctAnswerValueTextView = findViewById(R.id.CorrectAnswerValue);
        textText = findViewById(R.id.Text_text);
//        doneExamText = findViewById(R.id.Done_Exam); // Done Exam TextView
        backHomeButton = findViewById(R.id.backHome);
        reviewAnswer = findViewById(R.id.reviewAnswer);

        // Get score data
        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        int wrongAnswers = getIntent().getIntExtra("wrongAnswers", 0);
        String timeSpent = getIntent().getStringExtra("timeSpent");
        int totalQuestions = correctAnswers + wrongAnswers;

        // Set only the correct score, without the total number of questions
        correctAnswerValueTextView.setText(String.valueOf(correctAnswers));

        // Calculate score percentage
        double scorePercentage = ((double) correctAnswers / totalQuestions) * 100;

        // Update Done_Exam text based on performance
//        if (scorePercentage >= PASSING_SCORE_PERCENT) {
//            doneExamText.setText("Awesome Work! You Nailed It! 🏆");
//            doneExamText.setTextColor(Color.parseColor("#4CAF50")); // Green
//        } else if (scorePercentage >= 50) {
//            doneExamText.setText("You're Almost There! 🔥");
//            doneExamText.setTextColor(Color.parseColor("#FFA500")); // Orange
//        } else {
//            doneExamText.setText("Don't Give Up! Try Again! 💡");
//            doneExamText.setTextColor(Color.parseColor("#F44336")); // Red
//        }

        // Update other UI elements based on score performance
        if (scorePercentage >= PASSING_SCORE_PERCENT) {
            textText.setText("Great job! You're on your way to success! 🎯");
        } else if (scorePercentage >= 50) {
            textText.setText("Not bad! Keep practicing to improve your score. 💪");
        } else {
            textText.setText("Don't give up! Try again and aim higher! 🚀");
        }

        // Update highest score in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("QuizScores", MODE_PRIVATE);
        int highestCorrectAnswersAtomuEscapeRoom = sharedPreferences.getInt("highestCorrectAnswersAtomuEscapeRoom", 0);
        int highestTotalQuestionsAtomuEscapeRoom = sharedPreferences.getInt("highestTotalQuestionsAtomuEscapeRoom", 0);

        // Check if the current score is higher, and only update if true
        if (correctAnswers > highestCorrectAnswersAtomuEscapeRoom ||
                (correctAnswers == highestCorrectAnswersAtomuEscapeRoom && totalQuestions > highestTotalQuestionsAtomuEscapeRoom)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highestCorrectAnswersAtomuEscapeRoom", correctAnswers);
            editor.putInt("highestTotalQuestionsAtomuEscapeRoom", totalQuestions);
            editor.apply();
        }

        setupButtonListeners(correctAnswers, wrongAnswers, timeSpent);
    }

    private void setupButtonListeners(int correctAnswers, int wrongAnswers, String timeSpent) {
        backHomeButton.setOnClickListener(v -> {
            startActivity(new Intent(AtomuEscapeResult.this, MainActivity.class));
            finish();
        });

        reviewAnswer.setOnClickListener(v -> {
            if (getIntent().hasExtra("questions") &&
                    getIntent().hasExtra("userAnswers") &&
                    getIntent().hasExtra("correctChoices") &&
                    getIntent().hasExtra("choices")) {

                Intent reviewIntent = new Intent(AtomuEscapeResult.this, ReviewAnswersActivityEasy.class);
                reviewIntent.putExtra("correctAnswers", correctAnswers);
                reviewIntent.putExtra("wrongAnswers", wrongAnswers);
                reviewIntent.putExtra("timeSpent", timeSpent);
                reviewIntent.putStringArrayListExtra("questions", getIntent().getStringArrayListExtra("questions"));
                reviewIntent.putStringArrayListExtra("userAnswers", getIntent().getStringArrayListExtra("userAnswers"));
                reviewIntent.putStringArrayListExtra("correctChoices", getIntent().getStringArrayListExtra("correctChoices"));
                reviewIntent.putExtra("choices", getIntent().getSerializableExtra("choices"));
                startActivity(reviewIntent);
                finish();
            }
        });

    }
}
