package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SamplePopUp extends AppCompatActivity {

    private Button btn_home;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide the Action Bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        setContentView(R.layout.activity_settings); // Inflate layout first

        // Now find views inside the inflated layout
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Set up the "View References" text (TextView, not Button)
        TextView btnReferences = findViewById(R.id.btnReferences);
        btnReferences.setText(Html.fromHtml("<u>View References</u>")); // Underlined text
        btnReferences.setOnClickListener(v -> showReferencesPopup());

        // Set up the "Home" button
        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(v -> {
            Intent intent = new Intent(SamplePopUp.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close current activity
        });
    }

    private void showReferencesPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.popup_references, null);

        TextView referencesContent = dialogView.findViewById(R.id.tvReferencesContent);
        referencesContent.setText(Html.fromHtml(getString(R.string.references_text), Html.FROM_HTML_MODE_LEGACY));
        referencesContent.setMovementMethod(LinkMovementMethod.getInstance());

        builder.setView(dialogView);
        builder.setPositiveButton("Close", null);
        builder.show();
    }
}
