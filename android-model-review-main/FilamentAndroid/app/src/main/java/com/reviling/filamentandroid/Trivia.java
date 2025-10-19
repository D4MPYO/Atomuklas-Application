package com.reviling.filamentandroid;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class Trivia extends AppCompatActivity {

    private TextView questionTextView, answerTextView, explanationTextView, questionCounterTextView;
    private Button nextButton, previousButton;
    private ImageView backButton;
    private int currentQuestionIndex = 0;
    private ArrayList<String> questions;
    private ArrayList<String> answers;
    private ArrayList<String> explanations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.trivia);

        // Enable full immersive mode
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Initialize UI elements
        questionTextView = findViewById(R.id.text_question);
        answerTextView = findViewById(R.id.Answer);
        explanationTextView = findViewById(R.id.explanation);
        questionCounterTextView = findViewById(R.id.cpt_question);
        nextButton = findViewById(R.id.btn_next);
        previousButton = findViewById(R.id.btn_previous);
        backButton = findViewById(R.id.backButton);

        questions = new ArrayList<>();
        answers = new ArrayList<>();
        explanations = new ArrayList<>();

        // Add questions, answers, and explanations
        questions.add("What is 432 + 198?");
        answers.add("Answer: 630");
        explanations.add("Explanation: Add the two numbers: 432 + 198 = 630.");

        questions.add("What is 15% of 200?");
        answers.add("Answer: 30");
        explanations.add("Explanation: To find 15% of 200, multiply 200 by 0.15 (15/100): 200×0.15=30.");

        questions.add("A recipe calls for 4 cups of water for every 2 cups of rice. What is the ratio of water to rice?");
        answers.add("Answer: 2:1");
        explanations.add("Explanation: The ratio of water to rice is calculated as 4:2, which simplifies to 2:1 by dividing both sides by 2.");

        questions.add("If a car travels 240 miles on 8 gallons of gas, what is the MPG?");
        answers.add("Answer: 30 MPG");
        explanations.add("Explanation: Miles per gallon (MPG) is calculated by dividing the distance traveled by the gallons used: 240÷8=30 MPG.");

        questions.add("What is 120 divided by 4?");
        answers.add("Answer: 30");
        explanations.add("Explanation: To divide 120 by 4, perform the calculation: 120÷4=30.");

        questions.add("If a shirt costs 40 and is on sale for 25% off, what is the sale price?");
        answers.add("Answer: 30");
        explanations.add("Explanation: Calculate the discount: 40×0.25=10. Subtract the discount from the original price: 40−10=30.");

        questions.add("What is the difference between 900 and 567?");
        answers.add("Answer: 333");
        explanations.add("Explanation: Subtract 567 from 900: 900−567=333.");

        questions.add("If a product costs 120 and the sales tax is 8%, what is the total cost?");
        answers.add("Answer: 129.60");
        explanations.add("Explanation: Calculate the sales tax: 120×0.08=9.60. Add the sales tax to the original price: 120+9.60=129.60.");

        questions.add("What is the ratio of 12 to 48?");
        answers.add("Answer: 1:4");
        explanations.add("Explanation: The ratio 12:48 simplifies by dividing both numbers by 12: 12÷12:48÷12=1:4.");

        questions.add("If a student scores 80 out of 100 on a test, what is the percentage score?");
        answers.add("Answer: 80%");
        explanations.add("Explanation: To find the percentage, divide the score by the total and multiply by 100: (80÷100) ×100=80%.");

        // Shuffle the questions, answers, and explanations
        shuffleQuestions();

        // Display the first question
        updateQuestion();

        // Next button click listener
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex < questions.size() - 1) {
                    currentQuestionIndex++;  // Move to the next question
                    updateQuestion();  // Update the displayed question
                }
            }
        });

        // Previous button click listener
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;  // Move to the previous question
                    updateQuestion();  // Update the displayed question
                }
            }
        });

        // Back button click listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Finish the current activity and return to the previous one
            }
        });
    }

    // Function to shuffle the questions, answers, and explanations while keeping them aligned
    private void shuffleQuestions() {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            indices.add(i);
        }

        // Shuffle the indices
        Collections.shuffle(indices);

        // Create new shuffled lists
        ArrayList<String> shuffledQuestions = new ArrayList<>();
        ArrayList<String> shuffledAnswers = new ArrayList<>();
        ArrayList<String> shuffledExplanations = new ArrayList<>();

        for (int index : indices) {
            shuffledQuestions.add(questions.get(index));
            shuffledAnswers.add(answers.get(index));
            shuffledExplanations.add(explanations.get(index));
        }

        // Update the original lists with shuffled content
        questions = shuffledQuestions;
        answers = shuffledAnswers;
        explanations = shuffledExplanations;
    }

    // Update the displayed question, answer, and explanation
    private void updateQuestion() {
        questionTextView.setText(questions.get(currentQuestionIndex));
        answerTextView.setText(answers.get(currentQuestionIndex));
        explanationTextView.setText(explanations.get(currentQuestionIndex));
        questionCounterTextView.setText((currentQuestionIndex + 1) + " out of " + questions.size());
    }
}
