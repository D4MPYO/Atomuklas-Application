package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AutomaniaGames extends AppCompatActivity {

    private ImageView backButton;
    private ImageButton btnAtomuGuess, btnEscapeRoom, btnMysteryDoor;

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

        setContentView(R.layout.automania_games); // Make sure this matches your XML filename

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        backButton = findViewById(R.id.backButton);
        btnAtomuGuess = findViewById(R.id.btnAtomuGuess);
        btnEscapeRoom = findViewById(R.id.btnEscapeRoom);
        btnMysteryDoor = findViewById(R.id.btnMysteryDoor);

        // Back button behavior
        backButton.setOnClickListener(view -> finish());

        // Atomu Guess button click
        btnAtomuGuess.setOnClickListener(view -> {
            Intent intent = new Intent(AutomaniaGames.this, SplashScreenAtomuGuess.class);
            startActivity(intent);
        });

        // Escape Room button click
        btnEscapeRoom.setOnClickListener(view -> {
            Intent intent = new Intent(AutomaniaGames.this, SplashScreenEscapeRoom.class);
            startActivity(intent);
        });

        // Mystery Door button click
        btnMysteryDoor.setOnClickListener(view -> {
            Intent intent = new Intent(AutomaniaGames.this, SplashScreenMysteryDoor.class);
            startActivity(intent);
        });
    }
}
