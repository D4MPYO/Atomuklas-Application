package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Worksheets2 extends AppCompatActivity {
    private ImageView backButton;
    private Button btn_download, btn_download2, btn_download3, btn_download4, btn_download5, btn_download6;

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

        setContentView(R.layout.learning_worksheets);

        // Initialize buttons
        btn_download = findViewById(R.id.btn_download);
        btn_download2 = findViewById(R.id.btn_download2);
        btn_download3 = findViewById(R.id.btn_download3);
        btn_download4 = findViewById(R.id.btn_download4);
        btn_download5 = findViewById(R.id.btn_download5);
        btn_download6 = findViewById(R.id.btn_download6);

        // Attach PDF download URLs
        setDownloadAction(btn_download , "https://drive.google.com/uc?export=download&id=1Nrxo4p49tmXcRKgVFIY3OV6-LIyRqptK");
        setDownloadAction(btn_download2, "https://drive.google.com/uc?export=download&id=11bdprKEonjZiOG7O_C0i2FRFLJSJtGz0");
        setDownloadAction(btn_download3, "https://drive.google.com/uc?export=download&id=1CnxIRB4wE0_uUrcnfovbE0KJ7zVQE6X6");
        setDownloadAction(btn_download4, "https://drive.google.com/uc?export=download&id=1xzA9m4mPyf4jH7gCeEm6R_aiKg03DeGY");
        setDownloadAction(btn_download5, "https://drive.google.com/uc?export=download&id=1hCcsXQgyWFuFCmG7hjJCUzaoLYUFc2kq");
        setDownloadAction(btn_download6, "https://drive.google.com/uc?export=download&id=1bqi6zCIWgU1n6aK75RV9Fqqms4I-tXUW");

        // Full immersive mode
        getWindow().getDecorView().setSystemUiVisibility(
                android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                        | android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        // Back button
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Gradient title
        TextView titleText = findViewById(R.id.titleText);
        Shader shader = new LinearGradient(0, 0, 0, titleText.getTextSize(),
                new int[]{Color.parseColor("#8B0000"), Color.parseColor("#FF4500")},
                null, Shader.TileMode.CLAMP);
        titleText.getPaint().setShader(shader);
    }

    // Helper function for launching a browser to download PDF
    private void setDownloadAction(Button button, String url) {
        button.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
    }
}
