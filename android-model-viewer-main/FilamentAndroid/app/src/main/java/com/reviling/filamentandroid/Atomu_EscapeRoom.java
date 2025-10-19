package com.reviling.filamentandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.*;

public class Atomu_EscapeRoom extends AppCompatActivity {

    private enum Mode {
        MULTIPLE_CHOICE, TRUE_FALSE, IDENTIFICATION
    }

    private MediaPlayer bgMusic;


    private static class Question {
        String question;
        String correctAnswer;
        List<String> options;
        Mode mode;
        int imageResId; // Add image resource ID

        Question(String question, String correctAnswer, List<String> options, Mode mode) {
            this(question, correctAnswer, options, mode, -1);
        }

        Question(String question, String correctAnswer, Mode mode) {
            this(question, correctAnswer, null, mode, -1);
        }

        Question(String question, String correctAnswer, List<String> options, Mode mode, int imageResId) {
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.options = options;
            this.mode = mode;
            this.imageResId = imageResId;
        }
    }

    private TextView questionText, timerText, questionCounter;
    private LinearLayout answersLayout;
    private ConstraintLayout rootLayout;
    private ImageView questionImage; // ImageView for question image
    private final Random random = new Random();

    private int questionCount = 0;
    private static final int MAX_QUESTIONS = 10;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    private CountDownTimer countDownTimer;
    private long timeLeftMillis = 600000;

    private final List<String> questionList = new ArrayList<>();
    private final List<String> userAnswers = new ArrayList<>();
    private final List<String> correctChoices = new ArrayList<>();
    private final List<List<String>> allChoices = new ArrayList<>();

    private final List<Question> questionPool = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (bgMusic == null) {
            bgMusic = MediaPlayer.create(this, R.raw.background_music);
            bgMusic.setLooping(true);
            bgMusic.start();
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.automo_escaperoom);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        questionText = findViewById(R.id.questionText);
        timerText = findViewById(R.id.timerText);
        questionCounter = findViewById(R.id.questionCounter);
        answersLayout = findViewById(R.id.answersLayout);
        rootLayout = findViewById(R.id.rootLayout);
        questionImage = findViewById(R.id.questionImage); // Connect with your layout

        prepareQuestionPool();
        startTimer();
        loadNextQuestion();
    }

    private void prepareQuestionPool() {
        questionPool.addAll(Arrays.asList(
                new Question("What does the term “atomos” mean?", "Indivisible", Arrays.asList("Indestructible", "Indescribable", "Indivisible", "Invisible"), Mode.MULTIPLE_CHOICE),
                new Question("What can be inferred when some of the alpha particles in the gold foil experiment deflect? ", "The atom has a positive region in it", Arrays.asList("The atom emits alpha particles", "The atom has a positive region in it", "The atom is not an empty space as it was thought to be", "The negative particles of the atom attract the alpha particles"), Mode.MULTIPLE_CHOICE, R.drawable.img_escaperoom_question_2),
                new Question("What happens when the source of energy that excites electrons is removed?", "The electrons will emit energy and go back to their ground state", Arrays.asList("The electrons will remain in its current location", "The electron will jump to a higher energy level", "The electrons will move out of the atomic system", "The electrons will emit energy and go back to their ground state"), Mode.MULTIPLE_CHOICE),
                new Question("Which is TRUE about the identity of an atomic particle?", "The number of protons is always the same as the atomic number", Arrays.asList("The number of protons is always equal to that of electrons", "The number of protons is always the same as the atomic number", "The mass number is the sum of the number of protons and electrons", "A particle with a greater number of protons than electrons is called an anion"), Mode.MULTIPLE_CHOICE),
                new Question("If an atom has an atomic number of 5 and a mass number 11, how many neutrons does it have?", "6", Arrays.asList("5", "6", "11", "16"), Mode.MULTIPLE_CHOICE),
                new Question("Who proposed the atomic model shown in the image?", "Niels Bohr", Arrays.asList("Albert Einstein", "Niels Bohr", "Ernest Rutherford", "J.J. Thomson"), Mode.MULTIPLE_CHOICE, R.drawable.question_nga_3),

                new Question("Isotopes of an atom are atoms of the same element that have different atomic masses.", "TRUE", Mode.TRUE_FALSE),
                new Question("Protons and neutrons are the two subatomic particles found in the nucleus of an atom. These particles are similar in size and mass, which allows them to contribute almost equally to the atom’s mass number.", "TRUE", Mode.TRUE_FALSE),
                new Question("Dalton’s atomic theory states that atoms are made up of protons, neutrons, and electrons", "FALSE", Mode.TRUE_FALSE),

                new Question("What is produced when the number of electrons in an atom is changed?", "Ion", Mode.IDENTIFICATION)

        ));

        Collections.shuffle(questionPool);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMillis = millisUntilFinished;
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                timerText.setText(String.format(Locale.getDefault(), "%d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                timerText.setText("Time's up!");
                submitExamAutomatically();
            }
        }.start();
    }

    private void loadNextQuestion() {
        if (questionCount >= MAX_QUESTIONS || questionCount >= questionPool.size()) {
            showSubmitDialog();
            return;
        }

        Question currentQuestion = questionPool.get(questionCount);
        answersLayout.removeAllViews();
        questionCounter.setText((questionCount + 1) + " of " + MAX_QUESTIONS);

        // Show image if present
        if (currentQuestion.imageResId != -1) {
            questionImage.setVisibility(View.VISIBLE);
            questionImage.setImageResource(currentQuestion.imageResId);
        } else {
            questionImage.setVisibility(View.GONE);
        }

        switch (currentQuestion.mode) {
            case MULTIPLE_CHOICE:
                rootLayout.setBackgroundResource(R.drawable.bg_multiple_choice);
                loadMultipleChoice(currentQuestion);
                break;
            case TRUE_FALSE:
                rootLayout.setBackgroundResource(R.drawable.bg_true_false);
                loadTrueFalse(currentQuestion);
                break;
            case IDENTIFICATION:
                rootLayout.setBackgroundResource(R.drawable.bg_identification);
                loadIdentification(currentQuestion);
                break;
        }
    }

    private void loadMultipleChoice(Question question) {
        questionText.setText(question.question);

        // Clear previous answers
        answersLayout.removeAllViews();

        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(2);
        grid.setRowCount(2);
        grid.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        grid.setUseDefaultMargins(true);
        grid.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        List<String> shuffledOptions = new ArrayList<>(question.options);
        Collections.shuffle(shuffledOptions);

        for (String option : shuffledOptions) {
            Button btn = new Button(this);
            btn.setText(option);
            btn.setTextSize(16f);
            btn.setAllCaps(false);
            btn.setBackgroundResource(R.drawable.choice_button);
            btn.setPadding(24, 24, 24, 24); // Optional: More padding
            btn.setMaxLines(3); // Allow text to wrap up to 3 lines
            btn.setEllipsize(null); // Don’t truncate text
            btn.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            btn.setOnClickListener(v -> {
                evaluateAnswer(question, option, question.options);
                loadNextQuestion();
            });

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setMargins(16, 16, 16, 16);
            params.width = 0; // Let weight distribute
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            grid.addView(btn, params);
        }

        answersLayout.setGravity(Gravity.CENTER);
        answersLayout.addView(grid);
    }


    private void loadTrueFalse(Question question) {
        questionText.setText(question.question);

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        btnParams.setMargins(32, 16, 32, 16);

        Button trueBtn = new Button(this);
        trueBtn.setText("TRUE");
        trueBtn.setBackgroundResource(R.drawable.choice_button);
        trueBtn.setLayoutParams(btnParams);
        trueBtn.setOnClickListener(v -> {
            evaluateAnswer(question, "TRUE", Arrays.asList("TRUE", "FALSE"));
            loadNextQuestion();
        });

        Button falseBtn = new Button(this);
        falseBtn.setText("FALSE");
        falseBtn.setBackgroundResource(R.drawable.choice_button);
        falseBtn.setLayoutParams(btnParams);
        falseBtn.setOnClickListener(v -> {
            evaluateAnswer(question, "FALSE", Arrays.asList("TRUE", "FALSE"));
            loadNextQuestion();
        });

        row.addView(trueBtn);
        row.addView(falseBtn);
        answersLayout.setGravity(Gravity.CENTER);
        answersLayout.addView(row);
    }

    private void loadIdentification(Question question) {
        questionText.setText(question.question);

        EditText input = new EditText(this);
        input.setHint("Type your answer here");
        input.setMaxLines(1);
        input.setSingleLine(true);
        input.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        inputParams.setMargins(16, 16, 16, 16);
        input.setLayoutParams(inputParams);
        input.setMinWidth(800);
        input.setMaxWidth(1000);

        Button submitBtn = new Button(this);
        submitBtn.setText("SUBMIT");
        submitBtn.setBackgroundResource(R.drawable.choice_button);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        btnParams.setMargins(16, 16, 16, 16);
        submitBtn.setLayoutParams(btnParams);
        submitBtn.setMinWidth(300);
        submitBtn.setMaxWidth(400);

        submitBtn.setOnClickListener(v -> {
            String userAnswer = input.getText().toString().trim();
            evaluateAnswer(question, userAnswer, null);
            loadNextQuestion();
        });

        answersLayout.setGravity(Gravity.CENTER);
        answersLayout.addView(input);
        answersLayout.addView(submitBtn);
    }

    private void evaluateAnswer(Question q, String userAnswer, List<String> choices) {
        questionList.add(q.question);
        userAnswers.add(userAnswer);
        correctChoices.add(q.correctAnswer);
        allChoices.add(choices != null ? new ArrayList<>(choices) : Arrays.asList("N/A"));

        if (userAnswer.equalsIgnoreCase(q.correctAnswer)) {
            correctAnswers++;
        } else {
            wrongAnswers++;
        }

        questionCount++;
    }

    private void submitExamAutomatically() {
        countDownTimer.cancel();

        while (questionCount < MAX_QUESTIONS && questionCount < questionPool.size()) {
            Question missed = questionPool.get(questionCount);
            questionList.add(missed.question);
            userAnswers.add("No Answer");
            correctChoices.add(missed.correctAnswer);
            allChoices.add(missed.options != null ? missed.options : Arrays.asList("N/A"));
            wrongAnswers++;
            questionCount++;
        }

        new AlertDialog.Builder(this)
                .setTitle("Time's Up!")
                .setMessage("Time has run out. The exam will be submitted automatically.")
                .setPositiveButton("Submit", (dialog, which) -> showResult())
                .setCancelable(false)
                .show();
    }

    private void showSubmitDialog() {
        countDownTimer.cancel();
        new AlertDialog.Builder(this)
                .setTitle("Submit Quiz")
                .setMessage("You have answered all questions. Do you want to submit now?")
                .setPositiveButton("Submit", (dialog, which) -> showResult())
                .setCancelable(false)
                .show();
    }

    private void showResult() {
        String timeSpent = String.format(Locale.getDefault(), "%d:%02d",
                (30000 - timeLeftMillis) / 60000, ((30000 - timeLeftMillis) / 1000) % 60);

        Intent intent = new Intent(this, AtomuEscapeResult.class);
        intent.putExtra("correctAnswers", correctAnswers);
        intent.putExtra("wrongAnswers", wrongAnswers);
        intent.putExtra("timeSpent", timeSpent);
        intent.putStringArrayListExtra("questions", new ArrayList<>(questionList));
        intent.putStringArrayListExtra("userAnswers", new ArrayList<>(userAnswers));
        intent.putStringArrayListExtra("correctChoices", new ArrayList<>(correctChoices));
        intent.putExtra("choices", (ArrayList<List<String>>) allChoices);
        startActivity(intent);
        finish();
    }

    private void showExitConfirmationDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
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


