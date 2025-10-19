package com.reviling.filamentandroid;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Scores extends AppCompatActivity {
    private ImageView backButton;

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

        setContentView(R.layout.scores);

        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                        | android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Retrieve high scores from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("QuizScores", MODE_PRIVATE);

        int highestCorrectAnswers = sharedPreferences.getInt("highestCorrectAnswersEasy", 0);
        int highestTotalQuestions = sharedPreferences.getInt("highestTotalQuestionsEasy", 10);

        int highestCorrectAnswersIntermediate = sharedPreferences.getInt("highestCorrectAnswersIntermediate", 0);
        int highestTotalQuestionsIntermediate = sharedPreferences.getInt("highestTotalQuestionsIntermediate", 10);

        int highestCorrectAnswersAdvance = sharedPreferences.getInt("highestCorrectAnswersAdvance", 0);
        int highestTotalQuestionsAdvance = sharedPreferences.getInt("highestTotalQuestionsAdvance", 10);

        int highestCorrectAnswersAtomuGuess = sharedPreferences.getInt("highestCorrectAnswersAtomuGuess", 0);
        int highestTotalQuestionsAtomuGuess = sharedPreferences.getInt("highestTotalQuestionsAtomuGuess", 10);

        int highestCorrectAnswersAtomuEscapeRoom = sharedPreferences.getInt("highestCorrectAnswersAtomuEscapeRoom", 0);
        int highestTotalQuestionsAtomuEscapeRoom = sharedPreferences.getInt("highestTotalQuestionsAtomuEscapeRoom", 10);

        int highestCorrectAnswersAtomuMysteryDoor = sharedPreferences.getInt("highestCorrectAnswersAtomuMysteryDoor", 0);
        int highestTotalQuestionsAtomuMysteryDoor = sharedPreferences.getInt("highestTotalQuestionsAtomuMysteryDoor", 10);

        // Set individual score TextViews
        TextView scoreEasyTextView = findViewById(R.id.ScoreEasy);
        TextView scoreIntermediateTextView = findViewById(R.id.ScoreIntermediate);
        TextView scoreAdvanceTextView = findViewById(R.id.ScoreAdvance);
        TextView scoreAtomuGuessTextView = findViewById(R.id.ScoreAtomuGuess);
        TextView scoreAtomuEscapeRoomTextView = findViewById(R.id.ScoreAtomuEscapeRoom);
        TextView scoreAtomuMysteryDoorTextView = findViewById(R.id.ScoreAtomuMysteryDoor);
        TextView scoreSummaryTextView = findViewById(R.id.ScoreSummary); // ✅ Keep only this one

// Set individual scores
        scoreEasyTextView.setText(highestCorrectAnswers + "/" + highestTotalQuestions);
        scoreIntermediateTextView.setText(highestCorrectAnswersIntermediate + "/" + highestTotalQuestionsIntermediate);
        scoreAdvanceTextView.setText(highestCorrectAnswersAdvance + "/" + highestTotalQuestionsAdvance);
        scoreAtomuGuessTextView.setText(highestCorrectAnswersAtomuGuess + "/" + highestTotalQuestionsAtomuGuess);
        scoreAtomuEscapeRoomTextView.setText(highestCorrectAnswersAtomuEscapeRoom + "/" + highestTotalQuestionsAtomuEscapeRoom);
        scoreAtomuMysteryDoorTextView.setText(highestCorrectAnswersAtomuMysteryDoor + "/" + highestTotalQuestionsAtomuMysteryDoor);

// ✅ Set total score summary
        int totalCorrectAnswers = highestCorrectAnswers
                + highestCorrectAnswersIntermediate
                + highestCorrectAnswersAdvance
                + highestCorrectAnswersAtomuGuess
                + highestCorrectAnswersAtomuEscapeRoom
                + highestCorrectAnswersAtomuMysteryDoor;

        int totalQuestions = highestTotalQuestions
                + highestTotalQuestionsIntermediate
                + highestTotalQuestionsAdvance
                + highestTotalQuestionsAtomuGuess
                + highestTotalQuestionsAtomuEscapeRoom
                + highestTotalQuestionsAtomuMysteryDoor;

        scoreSummaryTextView.setText(totalCorrectAnswers + "/" + totalQuestions);
    }
}
