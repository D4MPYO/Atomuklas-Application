package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Worksheets extends AppCompatActivity {
    private ImageView backButton;
    private CardView DaltonsModel, PLumPuddingModel, GoldFoilExperimentModel, PlanetaryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.learning_worksheets); // Update to your correct XML file for Concept screen

        // Initialize buttons after setting the layout
        DaltonsModel = findViewById(R.id.DaltonsModel);
        PLumPuddingModel = findViewById(R.id.PLumPuddingModel);
        GoldFoilExperimentModel = findViewById(R.id.GoldFoilExperimentModel);
        PlanetaryModel = findViewById(R.id.PlanetaryModel);

        // Button Click Listeners
        DaltonsModel.setOnClickListener(v -> navigateToActivity(Worksheet_Module_1.class)); // Change to correct activity
        PLumPuddingModel.setOnClickListener(v -> navigateToActivity(Worksheet_Module_2.class));
        GoldFoilExperimentModel.setOnClickListener(v -> navigateToActivity(Worksheet_Module_3.class));
        PlanetaryModel.setOnClickListener(v -> navigateToActivity(Worksheet_Module_4.class)); // Change to correct activity

        // Enable full immersive mode
        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                        | android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        // Initialize the back button if present
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish()); // Close the activity when clicked

        TextView titleText = findViewById(R.id.titleText);
        Shader shader = new LinearGradient(0, 0, 0, titleText.getTextSize(),
                new int[]{Color.parseColor("#8B0000"), Color.parseColor("#FF4500")}, // Dark Red to Orange
                null, Shader.TileMode.CLAMP);
        titleText.getPaint().setShader(shader);
    }

    private void navigateToActivity(Class<?> destination) {
        Intent intent = new Intent(this, destination);
        startActivity(intent);
    }

}
