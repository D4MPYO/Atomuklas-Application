package com.reviling.filamentandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;



public class Atomu_MysteryDoorGameStart extends AppCompatActivity {

    private FrameLayout rootLayout;
    private ImageView door1, door2, door3, backButton;
    private TextView questionCounterText, globalTimerText;

    private List<Theme> themes;
    private int currentThemeIndex = 0;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;

    private CountDownTimer globalTimer;
    private long startTime;

    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    private ArrayList<String> questions = new ArrayList<>();
    private ArrayList<String> userAnswers = new ArrayList<>();
    private ArrayList<String> correctChoices = new ArrayList<>();
    private HashMap<Integer, String[]> allChoices = new HashMap<>();

    private MediaPlayer bgMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (bgMusic == null) {
            bgMusic = MediaPlayer.create(this, R.raw.background_music);
            bgMusic.setLooping(true);
            bgMusic.start();
        }


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        setContentView(R.layout.mysterydoor_start_game);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        rootLayout = findViewById(R.id.root_layout);
        door1 = findViewById(R.id.door1);
        door2 = findViewById(R.id.door2);
        door3 = findViewById(R.id.door3);
        backButton = findViewById(R.id.backButton);
        questionCounterText = findViewById(R.id.question_counter);
        globalTimerText = findViewById(R.id.global_timer);

        backButton.setOnClickListener(v -> showExitConfirmationDialog());

        initializeThemes();
        initializeQuestions();

        applyTheme(themes.get(currentThemeIndex));
        questionCounterText.setText("1/10");

        startTime = System.currentTimeMillis();
        startGlobalTimer();

        View.OnClickListener doorClickListener = v -> {
            if (currentQuestionIndex < questionList.size()) {
                showQuestionOverlay(questionList.get(currentQuestionIndex));
            }
        };

        door1.setOnClickListener(doorClickListener);
        door2.setOnClickListener(doorClickListener);
        door3.setOnClickListener(doorClickListener);
    }

    private void initializeThemes() {
        themes = new ArrayList<>();
        themes.add(new Theme(R.drawable.bg_sample_doors, new int[]{R.drawable.door_sample_1, R.drawable.door_sample_2, R.drawable.door_sample_3}));
        themes.add(new Theme(R.drawable.theme2, new int[]{R.drawable.theme2_door1, R.drawable.theme2_door2, R.drawable.theme2_door3}));
        themes.add(new Theme(R.drawable.theme3, new int[]{R.drawable.theme3_door1, R.drawable.theme3_door2, R.drawable.theme3_door3}));
        themes.add(new Theme(R.drawable.theme4, new int[]{R.drawable.theme4_door1, R.drawable.theme4_door2, R.drawable.theme4_door3}));
        themes.add(new Theme(R.drawable.theme5, new int[]{R.drawable.theme5_door1, R.drawable.theme5_door2, R.drawable.theme5_door3}));
        themes.add(new Theme(R.drawable.theme6, new int[]{R.drawable.theme6_door1, R.drawable.theme6_door2, R.drawable.theme6_door3}));
        Collections.shuffle(themes);
    }

    private void initializeQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Question("What are the three basic subatomic particles?",
                new String[]{"Proton, ion and electron", "Nucleus, proton and neutron", "Proton, neutron and electron", "Proton, neutron and hydrogen"}, 2,0));
        questionList.add(new Question("Which of the following statements about Dalton’s atomic model theory is NOT TRUE?",
                new String[]{"Everything is made up of small invisible particles.", "When atoms of different elements combine, they form chemical compounds with a definite proportion.", "Atoms of the same elements are always identical.", "Atoms may change their identity as a different particle."}, 3, R.drawable.img_mysterydoor_question_2));
        questionList.add(new Question(" How did the cathode rays behave in Joseph Thomson’s experiment?",
                new String[]{"The rays bent down toward the negative plate", "The rays bent down toward the positive plate.", "The rays moved on a straight path.", "The rays deflected back to the source."}, 1, R.drawable.img_mysterydoor_question_3));
        questionList.add(new Question("What subatomic particles constitute the mass number?",
                new String[]{"Proton and neutron", "Proton and electron", "Neutron and electron", "Proton, neutron, and electron"}, 0, 0));
        questionList.add(new Question("How many electrons does a neutral atom with 12 protons have?",
                new String[]{"0", "12", "14", "24"}, 1, 0));
        questionList.add(new Question(" Which atomic model was the first to identify energy levels?",
                new String[]{"Bohr’s atomic model", "Dalton’s atomic model", "Rutherford’s atomic model", "Thomson’s plum pudding model "}, 0, 0));
        questionList.add(new Question("Suppose the atom in number 8 has a mass number of 25, how many neutrons can be found in its nucleus?",
                new String[]{"12", "13", "25", "37"}, 1,0));
        questionList.add(new Question("Which of the following is TRUE about isotopes?",
                new String[]{"Isotopes are charged atoms.", "Isotopes are stable elements.", "Isotopes have different numbers of protons.", "Isotopes have different numbers of neutrons."}, 3, 0));
        questionList.add(new Question("What is the mass number of an element that has 18 protons, 17 electrons, and 22 neutrons?",
                new String[]{"35", "39", "40", "57"}, 3, 0));
        questionList.add(new Question("Which atomic model was the first one to include electrons?",
                new String[]{"Bohr’s atomic model", "Dalton’s atomic model", "Rutherford’s atomic model", "Thomson’s plum pudding model "}, 3, 0));
        Collections.shuffle(questionList);
    }

    private void applyTheme(Theme theme) {
        rootLayout.setBackgroundResource(theme.backgroundResId);
        door1.setImageResource(theme.doorImageResIds[0]);
        door2.setImageResource(theme.doorImageResIds[1]);
        door3.setImageResource(theme.doorImageResIds[2]);
    }

    private void showQuestionOverlay(Question question) {
        View overlay = findViewById(R.id.questionOverlay);
        TextView questionText = overlay.findViewById(R.id.question_text);
        ImageView questionImage = overlay.findViewById(R.id.question_image); // <-- Add this to XML
        Button choice1 = overlay.findViewById(R.id.choice1);
        Button choice2 = overlay.findViewById(R.id.choice2);
        Button choice3 = overlay.findViewById(R.id.choice3);
        Button choice4 = overlay.findViewById(R.id.choice4);

        questionText.setText(question.text);

        if (question.imageResId != 0) {
            questionImage.setVisibility(View.VISIBLE);
            questionImage.setImageResource(question.imageResId);
        } else {
            questionImage.setVisibility(View.GONE);
        }

        choice1.setText(question.choices[0]);
        choice2.setText(question.choices[1]);
        choice3.setText(question.choices[2]);
        choice4.setText(question.choices[3]);

        overlay.setVisibility(View.VISIBLE);

        View.OnClickListener answerClickListener = v -> {
            Button selected = (Button) v;
            String selectedAnswer = selected.getText().toString();
            String correctAnswer = question.choices[question.correctIndex];

            questions.add(question.text);
            userAnswers.add(selectedAnswer);
            correctChoices.add(correctAnswer);
            allChoices.put(currentQuestionIndex, question.choices);

            if (selectedAnswer.equals(correctAnswer)) {
                correctAnswers++;
            } else {
                wrongAnswers++;
            }

            overlay.setVisibility(View.GONE);
            currentQuestionIndex++;

            if (currentQuestionIndex < 10) {
                questionCounterText.setText((currentQuestionIndex + 1) + "/10");
                currentThemeIndex = (currentThemeIndex + 1) % themes.size();
                applyTheme(themes.get(currentThemeIndex));
            } else {
                showEndGameDialog();
            }
        };

        choice1.setOnClickListener(answerClickListener);
        choice2.setOnClickListener(answerClickListener);
        choice3.setOnClickListener(answerClickListener);
        choice4.setOnClickListener(answerClickListener);
    }

    private void startGlobalTimer() {
        globalTimer = new CountDownTimer(600000, 1000) {
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished % 60000) / 1000;
                globalTimerText.setText(String.format("%02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                globalTimerText.setText("00:00");
                showEndGameDialog();
            }
        }.start();
    }

    private void showEndGameDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Time's up or all questions answered!")
                .setPositiveButton("OK", (dialog, which) -> {
                    long timeSpent = (System.currentTimeMillis() - startTime) / 1000;

                    Intent resultIntent = new Intent(Atomu_MysteryDoorGameStart.this, AtomuMysteryDoorResult.class);
                    resultIntent.putExtra("correctAnswers", correctAnswers);
                    resultIntent.putExtra("wrongAnswers", wrongAnswers);
                    resultIntent.putExtra("timeSpent", String.valueOf(timeSpent));
                    resultIntent.putStringArrayListExtra("questions", questions);
                    resultIntent.putStringArrayListExtra("userAnswers", userAnswers);
                    resultIntent.putStringArrayListExtra("correctChoices", correctChoices);
                    resultIntent.putExtra("choices", allChoices);
                    startActivity(resultIntent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    static class Theme {
        int backgroundResId;
        int[] doorImageResIds;

        Theme(int backgroundResId, int[] doorImageResIds) {
            this.backgroundResId = backgroundResId;
            this.doorImageResIds = doorImageResIds;
        }
    }

    static class Question {
        String text;
        String[] choices;
        int correctIndex;
        int imageResId;

        Question(String text, String[] choices, int correctIndex, int imageResId) {
            this.text = text;
            this.choices = choices;
            this.correctIndex = correctIndex;
            this.imageResId = imageResId;
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

