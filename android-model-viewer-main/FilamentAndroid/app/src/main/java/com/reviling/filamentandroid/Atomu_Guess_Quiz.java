package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Atomu_Guess_Quiz extends AppCompatActivity {

    private TextView questionCounterTextView, timerTextView;
    private EditText userInputAnswer;
    private ImageView backButton, questionImageView;
    private Button nextButton, previousButton;

    private int currentQuestionIndex = 0;
    private ArrayList<List<String>> correctAnswers;
    private ArrayList<String> images;
    private int score = 0;
    private int totalQuestions;
    private final int maxQuestions = 10;
    private ArrayList<String> userAnswers;
    private CountDownTimer countDownTimer;
    private static final int TIMER_DURATION_MINUTES = 10;
    private long timeLeftInMillis = TIMER_DURATION_MINUTES * 60 * 1000;

    private MediaPlayer bgMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        setContentView(R.layout.automu_guess_quiz);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        initUI();
        setupQuestions();
        shuffleQuestions();

        if (bgMusic == null) {
            bgMusic = MediaPlayer.create(this, R.raw.background_music);
            bgMusic.setLooping(true);
            bgMusic.start();
        }


        totalQuestions = Math.min(maxQuestions, correctAnswers.size());
        correctAnswers = new ArrayList<>(correctAnswers.subList(0, totalQuestions));
        images = new ArrayList<>(images.subList(0, totalQuestions));

        userAnswers = new ArrayList<>(Collections.nCopies(totalQuestions, ""));
        startTimer();
        updateQuestion();
        setupButtonListeners();
    }

    private void initUI() {
        questionCounterTextView = findViewById(R.id.cpt_question);
        timerTextView = findViewById(R.id.Timer);
        userInputAnswer = findViewById(R.id.edit_answer);
        questionImageView = findViewById(R.id.questionImage);
        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.btn_submit);
        previousButton = findViewById(R.id.btn_previous);

        backButton.setOnClickListener(v -> showExitConfirmationDialog());
    }

    private void setupQuestions() {
        correctAnswers = new ArrayList<>();
        images = new ArrayList<>();

        correctAnswers.add(Arrays.asList("Electron Orbits"));
        images.add("guess_question_1");

        correctAnswers.add(Arrays.asList("Mass Number"));
        images.add("guess_question_22");

        correctAnswers.add(Arrays.asList("Positive charge", "positive"));
        images.add("guess_question_3");

        correctAnswers.add(Arrays.asList("Electron"));
        images.add("guess_question_4");

        correctAnswers.add(Arrays.asList("Nucleus"));
        images.add("guess_question_5");

        correctAnswers.add(Arrays.asList("Gold Foil"));
        images.add("guess_question_66");

        correctAnswers.add(Arrays.asList("Proton"));
        images.add("guess_question_7");

        correctAnswers.add(Arrays.asList("Neutron"));
        images.add("guess_question_8");

        correctAnswers.add(Arrays.asList("Alpha particles"));
        images.add("guess_question_9");

        correctAnswers.add(Arrays.asList("Energy"));
        images.add("guess_question_10");

        // Add more questions here as needed
    }

    private void shuffleQuestions() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < correctAnswers.size(); i++) indices.add(i);
        Collections.shuffle(indices);

        ArrayList<List<String>> shuffledCorrectAnswers = new ArrayList<>();
        ArrayList<String> shuffledImages = new ArrayList<>();

        for (int index : indices) {
            shuffledCorrectAnswers.add(correctAnswers.get(index));
            shuffledImages.add(images.get(index));
        }

        correctAnswers = shuffledCorrectAnswers;
        images = shuffledImages;
    }

    private void updateQuestion() {
        questionCounterTextView.setText((currentQuestionIndex + 1) + " out of " + totalQuestions);
        nextButton.setText(currentQuestionIndex == totalQuestions - 1 ? "Submit" : "Next");

        String imageName = images.get(currentQuestionIndex);
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        questionImageView.setImageResource(imageResId);

        restoreUserAnswer();
    }

    private void setupButtonListeners() {
        nextButton.setOnClickListener(v -> {
            saveUserAnswer();
            if (currentQuestionIndex < totalQuestions - 1) {
                currentQuestionIndex++;
                updateQuestion();
            } else {
                submitExam();
            }
        });

        previousButton.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                saveUserAnswer();
                currentQuestionIndex--;
                updateQuestion();
            }
        });
    }

    private void saveUserAnswer() {
        userAnswers.set(currentQuestionIndex, userInputAnswer.getText().toString().trim());
    }

    private void restoreUserAnswer() {
        userInputAnswer.setText(userAnswers.get(currentQuestionIndex));
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                submitExamAutomatically();
            }
        }.start();
    }

    private void updateTimer() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) (timeLeftInMillis / 1000) % 3600 / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerTextView.setText("Time Left: " + timeLeftFormatted);
    }

    private void submitExam() {
        new AlertDialog.Builder(this)
                .setTitle("Submit Exam")
                .setMessage("Are you sure you want to submit the exam?")
                .setPositiveButton("Submit", (dialog, which) -> {
                    countDownTimer.cancel();
                    showScoreDialog();
                })
                .setNegativeButton("Review", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void submitExamAutomatically() {
        countDownTimer.cancel();
        new AlertDialog.Builder(this)
                .setTitle("Time's Up!")
                .setMessage("Time has run out. The exam will be submitted automatically.")
                .setPositiveButton("Submit", (dialog, which) -> showScoreDialog())
                .setCancelable(false)
                .show();
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < totalQuestions; i++) {
            String userAnswer = userAnswers.get(i).trim().toLowerCase();
            List<String> acceptableAnswers = correctAnswers.get(i);
            for (String correct : acceptableAnswers) {
                if (userAnswer.equalsIgnoreCase(correct.trim())) {
                    score++;
                    break;
                }
            }
        }
    }

    private void showScoreDialog() {
        calculateScore();
        long timeSpentMillis = (TIMER_DURATION_MINUTES * 60 * 1000) - timeLeftInMillis;
        int hoursSpent = (int) (timeSpentMillis / 1000) / 3600;
        int minutesSpent = (int) (timeSpentMillis / 1000) % 3600 / 60;
        int secondsSpent = (int) (timeSpentMillis / 1000) % 60;
        String timeSpentFormatted = String.format("%02d hr, %02d minutes, %02d secs", hoursSpent, minutesSpent, secondsSpent);

        Intent intent = new Intent(Atomu_Guess_Quiz.this, AtomuGuessResult.class);
        intent.putExtra("correctAnswers", score);
        intent.putExtra("wrongAnswers", totalQuestions - score);
        intent.putExtra("timeSpent", timeSpentFormatted);
        intent.putStringArrayListExtra("userAnswers", userAnswers);

        // Convert List<List<String>> to ArrayList<String> (e.g., join with commas)
        ArrayList<String> formattedCorrectChoices = new ArrayList<>();
        for (List<String> list : correctAnswers) {
            formattedCorrectChoices.add(String.join(", ", list));
        }

        intent.putStringArrayListExtra("correctChoices", formattedCorrectChoices);
        intent.putStringArrayListExtra("images", images);
        startActivity(intent);
        finish();
    }

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Quit Exam?")
                .setMessage("Are you sure you want to exit? All progress will be lost.")
                .setPositiveButton("YES", (dialog, which) -> finish())
                .setNegativeButton("NO", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onBackPressed() {
        showExitConfirmationDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bgMusic != null && bgMusic.isPlaying()) {
            bgMusic.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bgMusic != null && !bgMusic.isPlaying()) {
            bgMusic.start();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Optional: Only stop music if you are exiting the app
        if (isFinishing()) {
            if (bgMusic != null) {
                bgMusic.stop();
                bgMusic.release();
                bgMusic = null;
            }
        }
    }
}
