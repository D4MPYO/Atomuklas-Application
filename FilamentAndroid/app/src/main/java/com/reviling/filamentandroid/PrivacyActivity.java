package com.reviling.filamentandroid;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PrivacyActivity extends AppCompatActivity {

    private TextView tvDataPrivacy;
    private ImageView ivIndicator;
    private CardView cardClearHistory, cardDeleteAccount, cardDataPolicy;
    private boolean isExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_privacy);

        // Initialize Views
        tvDataPrivacy = findViewById(R.id.tvDataPrivacy);
        ivIndicator = findViewById(R.id.ivIndicator);
        cardClearHistory = findViewById(R.id.cardClearHistory);
        cardDeleteAccount = findViewById(R.id.cardDeleteAccount);
        cardDataPolicy = findViewById(R.id.cardDataPolicy);

        // Initially hide the cards
        cardClearHistory.setVisibility(View.GONE);
        cardDeleteAccount.setVisibility(View.GONE);
        cardDataPolicy.setVisibility(View.GONE);

        // Set click listener for the "Data & Privacy" text
        tvDataPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = !isExpanded;
                int visibility = isExpanded ? View.VISIBLE : View.GONE;
                int indicatorResource = isExpanded ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_up;

                cardClearHistory.setVisibility(visibility);
                cardDeleteAccount.setVisibility(visibility);
                cardDataPolicy.setVisibility(visibility);
                ivIndicator.setImageResource(indicatorResource);
            }
        });
    }
}