package com.reviling.filamentandroid;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class Game_Game extends AppCompatActivity {

    ImageView img1, img2, img3, img4;
    LinearLayout answerSlots;
    GridLayout letterGrid;
    TextView resultText;
    Button submitBtn, nextRoundBtn;
    private ImageView backButton;

    String[] words = {"FIRE", "WATER", "EARTH", "AIR"};
    int currentRound = 0;
    String correctAnswer;
    StringBuilder currentAnswer = new StringBuilder();
    List<Button> letterButtons = new ArrayList<>();
    List<TextView> slotViews = new ArrayList<>();
    Map<TextView, Button> slotButtonMap = new HashMap<>();

    // Images for each word
    int[][] wordImages = {
            {R.drawable.fire_1, R.drawable.fire_2, R.drawable.fire_3, R.drawable.fire_4}, // FIRE
            {R.drawable.water_1, R.drawable.water_2, R.drawable.water_3, R.drawable.water_4}, // WATER
            {R.drawable.earth_1, R.drawable.earth_2, R.drawable.earth_3, R.drawable.earth_4}, // EARTH
            {R.drawable.new_slider_1, R.drawable.new_slider_2, R.drawable.new_slider_3, R.drawable.new_slider_4}  // AIR
    };

    private static final String PREFS_NAME = "GamePrefs";
    private static final String KEY_CURRENT_ROUND = "currentRound";
    private static final String KEY_PROGRESS = "currentAnswer";

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

        setContentView(R.layout.game_game);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        img1 = findViewById(R.id.image1);
        img2 = findViewById(R.id.image2);
        img3 = findViewById(R.id.image3);
        img4 = findViewById(R.id.image4);
        answerSlots = findViewById(R.id.answerSlots);
        letterGrid = findViewById(R.id.letterGrid);
        resultText = findViewById(R.id.resultText);
        submitBtn = findViewById(R.id.submitBtn);
        nextRoundBtn = findViewById(R.id.nextRoundBtn);

        // Load saved game progress
        loadGameProgress();

        submitBtn.setOnClickListener(v -> {
            if (getCurrentAnswer().equalsIgnoreCase(correctAnswer)) {
                resultText.setText("Correct! 🎉");
                nextRoundBtn.setVisibility(View.VISIBLE); // Show next round button
            } else {
                resultText.setText("Try again!");
            }
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish()); // Close the activity when clicked

        nextRoundBtn.setOnClickListener(v -> {
            // Prepare for the next round
            nextRoundBtn.setVisibility(View.INVISIBLE);
            currentRound++;
            if (currentRound < words.length) {
                startRound();
            } else {
                resultText.setText("Game Over! 🎮");
            }
            saveGameProgress(); // Save progress after moving to the next round
        });
    }

    private void startRound() {
        // Get the word for the current round
        correctAnswer = words[currentRound];
        createAnswerSlots(correctAnswer.length());
        setupLetters(correctAnswer);
        loadImagesForRound(currentRound); // Load images for the current word
        resultText.setText("");
    }

    private void createAnswerSlots(int length) {
        answerSlots.removeAllViews();
        slotViews.clear();
        slotButtonMap.clear();
        currentAnswer.setLength(0);

        for (int i = 0; i < length; i++) {
            TextView slot = new TextView(this);
            slot.setText("_");
            slot.setTextSize(24f);
            slot.setPadding(8, 8, 8, 8);
            slot.setWidth(120);
            slot.setGravity(View.TEXT_ALIGNMENT_CENTER);
            slot.setBackgroundResource(android.R.drawable.edit_text);

            // Click to remove the letter
            slot.setOnClickListener(v -> {
                if (!slot.getText().toString().equals("_")) {
                    Button linkedBtn = slotButtonMap.get(slot);
                    if (linkedBtn != null) {
                        linkedBtn.setEnabled(true);
                    }

                    int index = slotViews.indexOf(slot);
                    if (index != -1) {
                        slot.setText("_");
                        slotButtonMap.remove(slot);
                        updateCurrentAnswer();
                    }
                }
            });

            answerSlots.addView(slot);
            slotViews.add(slot);
        }
    }

    private void setupLetters(String word) {
        letterGrid.removeAllViews();
        letterButtons.clear();

        String allLetters = word + generateDistractorLetters();
        List<Character> letters = new ArrayList<>();
        for (char c : allLetters.toCharArray()) {
            letters.add(c);
        }

        Collections.shuffle(letters);

        for (char c : letters) {
            Button letterBtn = new Button(this);
            letterBtn.setText(String.valueOf(c));
            letterBtn.setTextSize(18f);
            letterBtn.setAllCaps(false);
            letterBtn.setPadding(0, 8, 0, 8);
            letterBtn.setMinWidth(0);
            letterBtn.setMinHeight(0);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(8, 8, 8, 8);
            letterBtn.setLayoutParams(params);

            letterBtn.setOnClickListener(v -> {
                for (TextView slot : slotViews) {
                    if (slot.getText().toString().equals("_")) {
                        slot.setText(String.valueOf(c));
                        slotButtonMap.put(slot, letterBtn);
                        letterBtn.setEnabled(false);
                        updateCurrentAnswer();
                        break;
                    }
                }
            });

            letterGrid.addView(letterBtn);
            letterButtons.add(letterBtn);
        }
    }

    private void updateCurrentAnswer() {
        currentAnswer.setLength(0);
        for (TextView slot : slotViews) {
            String letter = slot.getText().toString();
            if (!letter.equals("_")) {
                currentAnswer.append(letter);
            }
        }
    }

    private String getCurrentAnswer() {
        StringBuilder answer = new StringBuilder();
        for (TextView slot : slotViews) {
            String letter = slot.getText().toString();
            if (!letter.equals("_")) {
                answer.append(letter);
            }
        }
        return answer.toString();
    }

    private void loadImagesForRound(int roundIndex) {
        int[] imagesForRound = wordImages[roundIndex];

        img1.setImageResource(imagesForRound[0]);
        img2.setImageResource(imagesForRound[1]);
        img3.setImageResource(imagesForRound[2]);
        img4.setImageResource(imagesForRound[3]);
    }

    private String generateDistractorLetters() {
        String distractors[] = {"XZPNCLMQ", "ABDEGIL", "OQRTUVY", "234567890"};
        return distractors[currentRound % distractors.length];
    }

    private void saveGameProgress() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_CURRENT_ROUND, currentRound);
        editor.putString(KEY_PROGRESS, currentAnswer.toString());
        editor.apply();
    }

    private void loadGameProgress() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentRound = prefs.getInt(KEY_CURRENT_ROUND, 0);
        String savedProgress = prefs.getString(KEY_PROGRESS, "");
        currentAnswer.setLength(0);
        currentAnswer.append(savedProgress);
        startRound();  // Reinitialize the game based on saved data
    }
}
