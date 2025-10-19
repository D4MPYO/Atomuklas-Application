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

public class StartAdvanceQuiz extends AppCompatActivity {

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
        setContentView(R.layout.start_advance_quiz);

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

        questions.add("In what way is Bohr’s atomic model more accurate than Rutherford’s?");
        choices.add(new ArrayList<>(List.of("Bohr showed atoms are indivisible", "Bohr introduced the neutron", "Bohr proposed energy levels for electrons", "Bohr discovered the nucleus")));
        correctAnswers.add("Bohr proposed energy levels for electrons");
        images.add("new");  // Reference image file in drawable

        questions.add("Rutherford’s gold foil experiment most strongly supports which conclusion?");
        choices.add(new ArrayList<>(List.of("Atoms are solid spheres", "Electrons are stationary", "The nucleus is small and positively charged", "Electrons are embedded within a positive sphere")));
        correctAnswers.add("The nucleus is small and positively charged");
        images.add("new");  // Reference image file in drawable

        questions.add("Evidence for the existence of isotopes is best demonstrated by:");
        choices.add(new ArrayList<>(List.of("The difference in atomic numbers of elements", "The identical chemical behavior of atoms with different masses", "The changing number of protons in atoms", "The constant number of electrons in all atoms")));
        correctAnswers.add("The identical chemical behavior of atoms with different masses");
        images.add("new");  // Reference image file in drawable

        questions.add("Two atoms are best classified as isotopes when they___________.");
        choices.add(new ArrayList<>(List.of("Have the same atomic number but different mass numbers", "Have different atomic numbers and mass numbers", "Have different numbers of protons", " Have the same mass number and atomic number")));
        correctAnswers.add("Have the same atomic number but different mass numbers");
        images.add("new");  // Reference image file in drawable

        questions.add("The discovery of isotopes most directly led to which revision of Dalton’s theory?");
        choices.add(new ArrayList<>(List.of("It confirmed that atoms are indivisible", "It proved that all atoms of an element must have the same mass", "It showed Dalton’s theory was completely inaccurate", "It required a revision of Dalton’s idea that all atoms of an element are identical")));
        correctAnswers.add("It required a revision of Dalton’s idea that all atoms of an element are identical");
        images.add("new");  // Reference image file in drawable

        questions.add("Why is Dalton’s atomic theory considered limited in light of isotope discoveries?");
        choices.add(new ArrayList<>(List.of("Because it confirmed that all atoms are solid and indivisible", "Because it showed atoms of the same element can have varying masses", "Because it replaced Dalton’s theory with Bohr’s model", "Because it introduced the concept of electrons in energy levels")));
        correctAnswers.add("Because it showed atoms of the same element can have varying masses");
        images.add("new");  // Reference image file in drawable

        questions.add("The proton is identified as the particle responsible for atomic identity because:");
        choices.add(new ArrayList<>(List.of("The proton count changes during chemical reactions", "Proton number remains constant for each element", "Proton mass is equal to that of an electron", "Protons are neutral particles in the nucleus")));
        correctAnswers.add("Proton number remains constant for each element");
        images.add("new");  // Reference image file in drawable

        questions.add("The role of the neutron in the nucleus is best described by which statement?");
        choices.add(new ArrayList<>(List.of("Neutrons provide charge balance within atoms", "Neutrons form the electron cloud surrounding the nucleus", "Neutrons help stabilize the nucleus by minimizing repulsion between protons", "Neutrons identify the type of element")));
        correctAnswers.add("Neutrons help stabilize the nucleus by minimizing repulsion between protons");
        images.add("ewasd");  // Reference image file in drawable

        questions.add("How is the atomic nucleus best described in terms of composition and function?");
        choices.add(new ArrayList<>(List.of("The nucleus contains all three subatomic particles", "The nucleus is negatively charged and holds electrons", "The nucleus is dense and consists of protons and neutrons", "The nucleus occupies most of the atom’s volume")));
        correctAnswers.add("The nucleus is dense and consists of protons and neutrons");
        images.add("new");  // Reference image file in drawable

        questions.add("The discovery of isotopes led scientists to conclude that___________.");
        choices.add(new ArrayList<>(List.of("Dalton's theory was entirely wrong and irrelevant", "Atoms of the same element can differ in neutron number, so his theory was incomplete", "Dalton’s theory is supported by the discovery of isotopes", "Isotopes confirm that atoms of different elements are identical")));
        correctAnswers.add("Atoms of the same element can differ in neutron number, so his theory was incomplete");
        images.add("new");  // Reference image file in drawable
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

        Intent intent = new Intent(StartAdvanceQuiz.this, ResultAdvance.class);
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
