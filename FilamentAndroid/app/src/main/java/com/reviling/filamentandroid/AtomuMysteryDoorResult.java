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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class AtomuMysteryDoorResult extends AppCompatActivity {

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

        setContentView(R.layout.atomu_mystery_door_result);

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
//        textText = findViewById(R.id.Text_text);
//        doneExamText = findViewById(R.id.Done_Exam); // Done Exam TextView
        backHomeButton = findViewById(R.id.backHome);
//        reviewAnswer = findViewById(R.id.reviewAnswer);



        // Get score data
        int correctAnswers = getIntent().getIntExtra("correctAnswers", 0);
        int wrongAnswers = getIntent().getIntExtra("wrongAnswers", 0);
        String timeSpent = getIntent().getStringExtra("timeSpent");
        int totalQuestions = correctAnswers + wrongAnswers;

// Show ONLY the correct score (e.g. "10")
        correctAnswerValueTextView.setText(String.valueOf(correctAnswers));

// Show key as "correct / total" (e.g. "10/10")
        TextView keyValueTextView = findViewById(R.id.keyValue);
        keyValueTextView.setText(correctAnswers + "/" + totalQuestions);


        // Calculate score percentage
        double scorePercentage = ((double) correctAnswers / totalQuestions) * 100;

//        // Update Done_Exam text based on performance
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

//        // Update other UI elements based on score performance
//        if (scorePercentage >= PASSING_SCORE_PERCENT) {
//            correctAnswerValueTextView.setTextColor(Color.parseColor("#4CAF50")); // Green
//            textText.setText("Great job! You're on your way to success! 🎯");
//        } else if (scorePercentage >= 50) {
//            correctAnswerValueTextView.setTextColor(Color.parseColor("#FFA500")); // Orange
//            textText.setText("Not bad! Keep practicing to improve your score. 💪");
//        } else {
//            correctAnswerValueTextView.setTextColor(Color.parseColor("#F44336")); // Red
//            textText.setText("Don't give up! Try again and aim higher! 🚀");
//        }



        // Update highest score in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("QuizScores", MODE_PRIVATE);
        int highestCorrectAnswersAtomuMysteryDoor = sharedPreferences.getInt("highestCorrectAnswersAtomuMysteryDoor", 0);
        int highestTotalQuestionsAtomuMysteryDoor = sharedPreferences.getInt("highestTotalQuestionsAtomuMysteryDoor", 0);

// Check if the current score is higher, and only update if true
        if (correctAnswers > highestCorrectAnswersAtomuMysteryDoor ||
                (correctAnswers == highestCorrectAnswersAtomuMysteryDoor && totalQuestions > highestTotalQuestionsAtomuMysteryDoor)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highestCorrectAnswersAtomuMysteryDoor", correctAnswers);
            editor.putInt("highestTotalQuestionsAtomuMysteryDoor", totalQuestions);
            editor.apply();
        }


        setupButtonListeners(correctAnswers, wrongAnswers, timeSpent);
    }

    private void setupButtonListeners(int correctAnswers, int wrongAnswers, String timeSpent) {
        backHomeButton.setOnClickListener(v -> {
            startActivity(new Intent(AtomuMysteryDoorResult.this, MainActivity.class));
            finish();
        });

//        reviewAnswer.setOnClickListener(v -> {
//            if (getIntent().hasExtra("questions") &&
//                    getIntent().hasExtra("userAnswers") &&
//                    getIntent().hasExtra("correctChoices") &&
//                    getIntent().hasExtra("choices")) {
//
//                Intent reviewIntent = new Intent(AtomuMysteryDoorResult.this, ReviewAnswersActivityEasy.class);
//                reviewIntent.putExtra("correctAnswers", correctAnswers);
//                reviewIntent.putExtra("wrongAnswers", wrongAnswers);
//                reviewIntent.putExtra("timeSpent", timeSpent);
//                reviewIntent.putStringArrayListExtra("questions", getIntent().getStringArrayListExtra("questions"));
//                reviewIntent.putStringArrayListExtra("userAnswers", getIntent().getStringArrayListExtra("userAnswers"));
//                reviewIntent.putStringArrayListExtra("correctChoices", getIntent().getStringArrayListExtra("correctChoices"));
//                reviewIntent.putExtra("choices", getIntent().getSerializableExtra("choices"));
//                startActivity(reviewIntent);
//                finish();
//            }
//        });

    }
}
