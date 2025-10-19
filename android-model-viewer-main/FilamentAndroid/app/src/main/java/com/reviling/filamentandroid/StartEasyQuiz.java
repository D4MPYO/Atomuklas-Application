package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartEasyQuiz extends AppCompatActivity {

    private TextView questionTextView, questionCounterTextView, timerTextView;
    private Button nextButton, previousButton;
    private Button choice1, choice2, choice3, choice4;
    private ImageView backButton;
    private ImageView questionImageView; // ✅ ImageView for question images

    private int currentQuestionIndex = 0;
    private ArrayList<String> questions;
    private ArrayList<ArrayList<String>> choices;
    private ArrayList<String> correctAnswers;
    private ArrayList<String> images; // ✅ List of image names
    private int score = 0;
    private int totalQuestions;
    private final int maxQuestions = 10;
    private ArrayList<String> userAnswers;
    private CountDownTimer countDownTimer;
    private static final int TIMER_DURATION_MINUTES = 10;
    private long timeLeftInMillis = TIMER_DURATION_MINUTES * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.start_easy_quiz);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> showExitConfirmationDialog());

        initUI();
        setupQuestionsAndChoices();
        shuffleQuestionsAndChoices();

        totalQuestions = Math.min(maxQuestions, questions.size());
        questions = new ArrayList<>(questions.subList(0, totalQuestions));
        choices = new ArrayList<>(choices.subList(0, totalQuestions));
        correctAnswers = new ArrayList<>(correctAnswers.subList(0, totalQuestions));
        images = new ArrayList<>(images.subList(0, totalQuestions)); // ✅ Limit images

        userAnswers = new ArrayList<>(Collections.nCopies(totalQuestions, ""));

        startTimer();
        updateQuestion();
        setupButtonListeners();
    }

    private void initUI() {
        questionTextView = findViewById(R.id.text_question);
        questionCounterTextView = findViewById(R.id.cpt_question);
        timerTextView = findViewById(R.id.Timer);
        nextButton = findViewById(R.id.btn_next);
        previousButton = findViewById(R.id.btn_previous);
        choice1 = findViewById(R.id.LetterA);
        choice2 = findViewById(R.id.LetterB);
        choice3 = findViewById(R.id.LetterC);
        choice4 = findViewById(R.id.LetterD);
        backButton = findViewById(R.id.backButton);
        questionImageView = findViewById(R.id.questionImage); // ✅ Initialize ImageView
    }

    private void setupQuestionsAndChoices() {
        questions = new ArrayList<>();
        choices = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        images = new ArrayList<>(); // List to hold the image paths

        questions.add("What is the smallest unit of an element that retains its chemical properties? ");
        choices.add(new ArrayList<>(List.of("Molecule", "Atom", "Ion", "Proton")));
        correctAnswers.add("Atom");
        images.add("new");  // Reference image file in drawable

        questions.add("Which subatomic particle is negatively charged? ");
        choices.add(new ArrayList<>(List.of("Electron ", "Neutron ", "Nucleus", "Proton ")));
        correctAnswers.add("Electron ");
        images.add("new");  // Reference image file in drawable

        questions.add("Which subatomic particle has no charge? ");
        choices.add(new ArrayList<>(List.of("Electron ", "Neutron", "Nucleus", "Proton")));
        correctAnswers.add("Neutron");
        images.add("new");

        questions.add("Who is the proponent of the “Plum Pudding Model” of an atom?");
        choices.add(new ArrayList<>(List.of("Niels Bohr", "John Dalton", "Ernest Rutherford", "Joseph John Thomson")));
        correctAnswers.add("Joseph John Thomson");
        images.add("j_j_thomsons");  // Reference image file in drawable

        questions.add("The atomic number tells the _______.");
        choices.add(new ArrayList<>(List.of("Number of electrons in the atom", "Number of protons in the atom", "Number of neurons in the atom", "Number of protons and neurons")));
        correctAnswers.add("Number of protons in the atom");
        images.add("na_na_qa");  // Reference image file in drawable

        questions.add("Which subatomic particles can be found in the nucleus of an atom?");
        choices.add(new ArrayList<>(List.of("Protons only", "Neutrons only", "Protons and Electrons", "Protons and Neutrons")));
        correctAnswers.add("Protons and Neutrons");
        images.add("new");

        questions.add("An atom of an element is electrically neutral because the number of protons is equal to the number of _____________.");
        choices.add(new ArrayList<>(List.of("Electrons", "Neutrons ", "Nucleons", "Nucleus")));
        correctAnswers.add("Electrons");
        images.add("new");

        questions.add("Why are atoms considered electrically neutral?");
        choices.add(new ArrayList<>(List.of("It is because all atoms contain neutrons", "It is because there is an equal number of protons and electrons", "It is because all subatomic particles lose their charges once they enter an atom", "It is because the number of subatomic particles in the nucleus is always an even number")));
        correctAnswers.add("It is because there is an equal number of protons and electrons");
        images.add("new");

        questions.add("What conclusion did Rutherford’s gold foil experiment lead to?");
        choices.add(new ArrayList<>(List.of("Atoms are indivisible", "Neutrons are located in the nucleus ", "Atoms contain a positively charged nucleus", "Electrons move in definite energy levels called shells")));
        correctAnswers.add("Atoms contain a positively charged nucleus");
        images.add("new");

        questions.add("What do all isotopes of the same element have in common? ");
        choices.add(new ArrayList<>(List.of("The same number of neutrons", "The same number of protons", "The same mass number", "The same atomic mass")));
        correctAnswers.add("The same number of protons");
        images.add("new");

    }

    // Shuffle questions and choices.
    private void shuffleQuestionsAndChoices() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        ArrayList<String> shuffledQuestions = new ArrayList<>();
        ArrayList<ArrayList<String>> shuffledChoices = new ArrayList<>();
        ArrayList<String> shuffledCorrectAnswers = new ArrayList<>();
        ArrayList<String> shuffledImages = new ArrayList<>(); // ✅ Add this

        for (int index : indices) {
            shuffledQuestions.add(questions.get(index));
            ArrayList<String> currentChoices = new ArrayList<>(choices.get(index));
            Collections.shuffle(currentChoices);
            shuffledChoices.add(currentChoices);
            shuffledCorrectAnswers.add(correctAnswers.get(index));
            shuffledImages.add(images.get(index)); // ✅ Maintain image order with question
        }

        questions = shuffledQuestions;
        choices = shuffledChoices;
        correctAnswers = shuffledCorrectAnswers;
        images = shuffledImages; // ✅ Apply shuffled images
    }


    // Timer methods.
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

    private void updateQuestion() {
        questionTextView.setText(questions.get(currentQuestionIndex));
        ArrayList<String> currentChoices = choices.get(currentQuestionIndex);

        choice1.setText(currentChoices.get(0));
        choice2.setText(currentChoices.get(1));
        choice3.setText(currentChoices.get(2));
        choice4.setText(currentChoices.get(3));

        questionCounterTextView.setText((currentQuestionIndex + 1) + " out of " + totalQuestions);
        nextButton.setText(currentQuestionIndex == totalQuestions - 1 ? "Submit" : "Next");

        // ✅ Set image for the question
        String imageName = images.get(currentQuestionIndex);
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (imageResId != 0) {
            questionImageView.setImageResource(imageResId);
            questionImageView.setVisibility(View.VISIBLE);
        } else {
            questionImageView.setVisibility(View.GONE); // Hide if not found
        }

        resetButtonStates();
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
            saveUserAnswer();
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                updateQuestion();
            }
        });

        setupChoiceListeners();
    }

    private void setupChoiceListeners() {
        View.OnClickListener choiceListener = v -> {
            resetButtonStates();
            selectChoice((Button) v);
            saveUserAnswer();
        };

        choice1.setOnClickListener(choiceListener);
        choice2.setOnClickListener(choiceListener);
        choice3.setOnClickListener(choiceListener);
        choice4.setOnClickListener(choiceListener);
    }

    private void saveUserAnswer() {
        Button selectedButton = getSelectedButton();
        if (selectedButton != null) {
            userAnswers.set(currentQuestionIndex, selectedButton.getText().toString());
        }
    }

    private Button getSelectedButton() {
        if (choice1.isSelected()) return choice1;
        if (choice2.isSelected()) return choice2;
        if (choice3.isSelected()) return choice3;
        if (choice4.isSelected()) return choice4;
        return null;
    }

    private void selectChoice(Button choice) {
        choice.setSelected(true);
        choice.setBackgroundColor(getResources().getColor(R.color.colorSelected));
    }

    private void restoreUserAnswer() {
        String previousAnswer = userAnswers.get(currentQuestionIndex);
        if (!previousAnswer.isEmpty()) {
            if (previousAnswer.equals(choice1.getText().toString())) selectChoice(choice1);
            else if (previousAnswer.equals(choice2.getText().toString())) selectChoice(choice2);
            else if (previousAnswer.equals(choice3.getText().toString())) selectChoice(choice3);
            else if (previousAnswer.equals(choice4.getText().toString())) selectChoice(choice4);
        }
    }

    private void resetButtonStates() {
        choice1.setSelected(false);
        choice2.setSelected(false);
        choice3.setSelected(false);
        choice4.setSelected(false);
        choice1.setBackgroundColor(getResources().getColor(R.color.colorDefault));
        choice2.setBackgroundColor(getResources().getColor(R.color.colorDefault));
        choice3.setBackgroundColor(getResources().getColor(R.color.colorDefault));
        choice4.setBackgroundColor(getResources().getColor(R.color.colorDefault));
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
                .show();
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < totalQuestions; i++) {
            if (userAnswers.get(i).equals(correctAnswers.get(i))) {
                score++;
            }
        }
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
        new AlertDialog.Builder(this)
                .setTitle("Quit Exam?")
                .setMessage("Are you sure you want to exit? All progress will be lost.")
                .setPositiveButton("YES", (dialog, which) -> super.onBackPressed())
                .setNegativeButton("NO", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showScoreDialog() {
        calculateScore();
        long timeSpentMillis = (TIMER_DURATION_MINUTES * 60 * 1000) - timeLeftInMillis;

        int hoursSpent = (int) (timeSpentMillis / 1000) / 3600;
        int minutesSpent = (int) (timeSpentMillis / 1000) % 3600 / 60;
        int secondsSpent = (int) (timeSpentMillis / 1000) % 60;
        String timeSpentFormatted = String.format("%02d hr, %02d minutes, %02d secs", hoursSpent, minutesSpent, secondsSpent);

        int correctAnswersCount = score;
        int wrongAnswersCount = totalQuestions - score;

        Intent intent = new Intent(StartEasyQuiz.this, ResultEasy.class);
        intent.putExtra("correctAnswers", correctAnswersCount);
        intent.putExtra("wrongAnswers", wrongAnswersCount);
        intent.putExtra("timeSpent", timeSpentFormatted);
        intent.putStringArrayListExtra("questions", questions);
        intent.putStringArrayListExtra("userAnswers", userAnswers);
        intent.putStringArrayListExtra("correctChoices", correctAnswers); // Using correctAnswers list as correctChoices
        intent.putExtra("choices", choices);
        intent.putStringArrayListExtra("images", images);// Passed as Serializable
        startActivity(intent);
        finish();
    }
}
