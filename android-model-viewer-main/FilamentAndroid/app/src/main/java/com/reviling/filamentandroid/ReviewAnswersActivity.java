package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ReviewAnswersActivity extends AppCompatActivity {

    private TextView reviewTextView;
    private Button backHomeButton;
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
        setContentView(R.layout.activity_review_answers);

        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                        | android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        // Initialize views. (Ensure these IDs exist in your XML layout.)
        reviewTextView = findViewById(R.id.UserAnswerValue);
        backHomeButton = findViewById(R.id.backHome);
        backButton = findViewById(R.id.backButton);


        // Retrieve data from Intent.
        ArrayList<String> questions = getIntent().getStringArrayListExtra("questions");
        ArrayList<String> userAnswers = getIntent().getStringArrayListExtra("userAnswers");
        ArrayList<String> correctChoices = getIntent().getStringArrayListExtra("correctChoices");
        ArrayList<ArrayList<String>> choices = (ArrayList<ArrayList<String>>)
                getIntent().getSerializableExtra("choices");

        // Build review text showing each question, its choices, the user's answer, and an indicator.
        StringBuilder reviewText = new StringBuilder();
        if (questions != null && userAnswers != null && correctChoices != null && choices != null) {
            for (int i = 0; i < questions.size(); i++) {
                reviewText.append("Q").append(i + 1).append(": ").append(questions.get(i)).append("\n");
                ArrayList<String> questionChoices = choices.get(i);
                for (int j = 0; j < questionChoices.size(); j++) {
                    char letter = (char) ('A' + j);
                    reviewText.append("   ").append(letter).append(". ").append(questionChoices.get(j)).append("\n");
                }
                String userAnswer = userAnswers.get(i);
                String correctAnswer = correctChoices.get(i);
                reviewText.append("Your Answer: ").append(userAnswer);
                if (userAnswer.equals(correctAnswer)) {
                    reviewText.append(" (Correct)\n");
                } else {
                    reviewText.append(" (Incorrect)\n");
                }
                reviewText.append("Correct Answer: ").append(correctAnswer).append("\n\n");
            }
        } else {
            reviewText.append("Some review data is missing.");
        }
        // Optionally, you could also display overall stats if desired.
        reviewTextView.setText(reviewText.toString());
        reviewTextView.setTextColor(Color.BLACK);

        // Button listener.
        backHomeButton.setOnClickListener(v -> {
            startActivity(new Intent(ReviewAnswersActivity.this, MainActivity.class));
            finish();
        });

        backButton.setOnClickListener(v -> finish());

    }
}
