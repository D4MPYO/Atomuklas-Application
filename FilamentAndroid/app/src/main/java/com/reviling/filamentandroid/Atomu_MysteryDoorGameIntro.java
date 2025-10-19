package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;


public class Atomu_MysteryDoorGameIntro extends AppCompatActivity {

    private ImageView backgroundImage;
    private ImageView nextIcon, backIcon;
    private Button finalButton;
    private int currentPage = 0;
    private ImageView backButton;
    private MediaPlayer bgMusic;



    private int[] backgrounds = {
            R.drawable.mystery_door_intro_1,
            R.drawable.mystery_door_intro_2,
            R.drawable.mystery_door_intro_3
    };

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

        // Hide the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.atomu_mystery_door_intro);

        // Enable full immersive mode
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

        backgroundImage = findViewById(R.id.backgroundImage);
        nextIcon = findViewById(R.id.nextIcon);
        backIcon = findViewById(R.id.backIcon);
        finalButton = findViewById(R.id.finalButton);

        updateUI();

        nextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage < backgrounds.length - 1) {
                    currentPage++;
                    updateUI();
                }
            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 0) {
                    currentPage--;
                    updateUI();
                }
            }
        });

        finalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the Atomu_MysteryDoorGameStart activity
                Intent intent = new Intent(Atomu_MysteryDoorGameIntro.this, Atomu_MysteryDoorGameStart.class);
                startActivity(intent);
            }
        });

    }

    private void updateUI() {
        // Crossfade animation
        backgroundImage.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        backgroundImage.setImageResource(backgrounds[currentPage]);
                        backgroundImage.animate()
                                .alpha(1f)
                                .setDuration(300)
                                .start();
                    }
                }).start();

        // Back icon visible only after first page
        backIcon.setVisibility(currentPage > 0 ? View.VISIBLE : View.INVISIBLE);

        // Next icon visible only until second page
        nextIcon.setVisibility(currentPage < backgrounds.length - 1 ? View.VISIBLE : View.INVISIBLE);

        // Final button visible only on last page
        finalButton.setVisibility(currentPage == backgrounds.length - 1 ? View.VISIBLE : View.GONE);
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