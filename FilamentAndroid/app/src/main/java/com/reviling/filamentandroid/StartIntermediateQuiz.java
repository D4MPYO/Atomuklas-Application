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

public class StartIntermediateQuiz extends AppCompatActivity {

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
        setContentView(R.layout.start_intermediate_quiz);

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

        questions.add("Which shows the correct order of the following atomic model theories from the oldest to the newest? ");
        choices.add(new ArrayList<>(List.of("3,1,5,4,2 ", "2,4,5,1,3", "3,1,5,2,4", "3,1,4,5,2")));
        correctAnswers.add("2,4,5,1,3");
        images.add("newest");  // Reference image file in drawable

        questions.add("How to compute the number of neutrons of an atom?");
        choices.add(new ArrayList<>(List.of("Always the same with a number of protons ", "Proton is added to the electron of an atom", "The difference of atomic mass and atomic number", "The product of electron and a proton")));
        correctAnswers.add("The difference of atomic mass and atomic number");
        images.add("new");  // Reference image file in drawable

        questions.add("How does the existence of isotopes challenge Dalton’s statement that “all atoms of the same element are identical”?");
        choices.add(new ArrayList<>(List.of("Isotopes have the same chemical behavior but different physical properties due to varied neutrons", "Isotopes prove that electrons define the element", "Isotopes confirm Dalton’s theory without exception", "Isotopes always form new elements")));
        correctAnswers.add("Isotopes have the same chemical behavior but different physical properties due to varied neutrons");
        images.add("new");  // Reference image file in drawable

        questions.add("In describing the atoms of a given element, which of the following is true when the number and type of particles are being considered?");
        choices.add(new ArrayList<>(List.of("Having the same mass number", "Having the same number of protons", "Having the same number of neutrons", "Having equal number of protons and neutrons")));
        correctAnswers.add("Having the same number of protons");
        images.add("new");  // Reference image file in drawable

        questions.add("What observations in the gold foil experiment made Rutherford conclude that atoms are mostly empty space?");
        choices.add(new ArrayList<>(List.of("Some alpha particles were deflected at smaller angles", "Most alpha particles passed through the gold foil undeflected", "Few alpha particles deflected almost back towards the source", "Very few particles were deflected from their path, indicating that the positive charge of the atom occupies very large space")));
        correctAnswers.add("Most alpha particles passed through the gold foil undeflected");
        images.add("gold_foil_exp");  // Reference image file in drawable

        questions.add("When analyzing the nucleus of an atom, why is it considered dense and positively charged?");
        choices.add(new ArrayList<>(List.of("It is composed entirely of electrons", "t contains protons and neutrons tightly packed together", " It holds all the atomic mass due to the presence of electrons", "It surrounds the electron cloud")));
        correctAnswers.add("t contains protons and neutrons tightly packed together");
        images.add("new");  // Reference image file in drawable

        questions.add("The element aluminum possesses 14 neutrons and 13 protons. What will be its mass number in the form of aluminum ion, Al³⁺? ");
        choices.add(new ArrayList<>(List.of("16", "17", "27", "30")));
        correctAnswers.add("27");
        images.add("perudic_t");  // Reference image file in drawable


        questions.add("Which of the following statements CORRECTLY describes atoms, ions and isotopes?\n" +
                "\nI. Neutral atoms have an equal number of protons, neutrons and electrons.\n" +
                "II. Atoms are the smallest particles of matter that retain the characteristics of an element.\n" +
                "III. Isotopes are atoms of the same element with an unequal number of neutrons in the nucleus. \n" +
                "IV. Ions are atoms with an unequal number of protons and electrons and are formed when an atom loses or gains electrons. \n");
        choices.add(new ArrayList<>(List.of("I, II and III ", "II, III and IV", "I, III and IV", "I, II, III and IV")));
        correctAnswers.add("II, III and IV");
        images.add("new");  // Reference image file in drawable

        questions.add("In describing the atoms of a given element, which of the following is true when the number and type of particles are being considered?");
        choices.add(new ArrayList<>(List.of("Having the same mass number ", "Having the same number of protons", "Having the same number of neutrons", "Having equal number of protons and neutrons")));
        correctAnswers.add("Having the same number of protons");
        images.add("new");  // Reference image file in drawable


        questions.add("One isotope of oxygen has the atomic number 8 and the mass number 18. An atom of this isotope contains _____________.");
        choices.add(new ArrayList<>(List.of("8 protons", "9 neutrons", "10 neutrons", "18 electrons")));
        correctAnswers.add("10 neutrons");
        images.add("ques_10");  // Reference image file in drawable
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

        Intent intent = new Intent(StartIntermediateQuiz.this, ResultIntermediate.class);
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
