// Trivia_New.java
package com.reviling.filamentandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class Trivia_New extends AppCompatActivity {
    private ImageView backButton;
    private Button btn_home;
    private ViewPager2 triviaViewPager;

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

        setContentView(R.layout.trivia_new);

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

        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(v -> {
            Intent intent = new Intent(Trivia_New.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        triviaViewPager = findViewById(R.id.triviaViewPager);

        List<TriviaItem> triviaItems = new ArrayList<>();
        triviaItems.add(new TriviaItem("Atoms are the fundamental particles that compose all matter in the universe and they are incredibly small, measuring one-tenth of a billionth of a meter across.", 0));
        triviaItems.add(new TriviaItem("Trillions of atoms make up everyday objects, including your body.", R.drawable.fact_1));
        triviaItems.add(new TriviaItem("Atoms are made up of smaller particles called subatomic particles: electrons, protons, and neutrons.", R.drawable.fact_2));
        triviaItems.add(new TriviaItem("An atom is mostly empty space due to the large distance between the nucleus and electrons.", 0));
        triviaItems.add(new TriviaItem("Dalton's atomic theory was the first complete attempt to describe all matter in terms of atoms and their properties.", 0));
        triviaItems.add(new TriviaItem("In 1913, Niels Bohr proposed a theory for the hydrogen atom, based on quantum theory that some physical quantities only take discrete values.", 0));
        triviaItems.add(new TriviaItem("Bohr was the first to discover that electrons travel in separate orbits around the nucleus and that the number of electrons in the outer orbit determines the properties of an element.", 0));
        triviaItems.add(new TriviaItem("The chemical element bohrium (Bh), No. 107 on the periodic table of elements, is named after Bohr.", R.drawable.fact_3));
        triviaItems.add(new TriviaItem("In 1897, Thompson showed that cathode rays (radiation emitted when a voltage is applied between two metal plates inside a glass tube filled with low-pressure gas) consist of particles— electrons—that conduct electricity.", 0));
        triviaItems.add(new TriviaItem("Thomson's theory is now known as the plum pudding atomic model or Thomson atomic model. The atom was visually thought of as a uniformly positively charged mass (the \\\"pudding\\\" or \\\"dough\\\") with the electrons scattered throughout (like \\\"plums\\\") to balance the charges.", R.drawable.fact_4));
        triviaItems.add(new TriviaItem("The element 'rutherfordium' (atomic number 104) was named in Rutherford’s honor. It is a synthetic, radioactive element made in particle accelerators.", R.drawable.fact_5));
        triviaItems.add(new TriviaItem("Rutherford worked on radioactivity, coining the terms ‘alpha’ and ‘beta’ to describe the two different types of radiation emitted by uranium and thorium. He also observed that radioactive material took the same amount of time for half of it to decay, known as its “half-life”.", 0));

        triviaItems.add(new TriviaItem("In 1907, Rutherford, Hans Geiger, and Ernest Marsden carried out the Geiger-Marsden experiment, an attempt to examine the structure of the atom. The surprising results of this experiment demonstrated the existence of the atomic nucleus, which became an integral part of the Rutherford model of the atom.", 0));

        triviaItems.add(new TriviaItem("Ernest Rutherford is known as the “father of nuclear physics”. In 1911, he was the first to discover that atoms have a small charged nucleus surrounded by largely empty space, and are circled by tiny electrons, which became known as the Rutherford model (or planetary model) of the atom. He is also credited with the discovery of the proton in 1919 and hypothesized the existence of the neutron.", R.drawable.fact_6));

        triviaItems.add(new TriviaItem("Rutherford demonstrated his experiment on bombarding thin gold foil with alpha particles contributed immensely to the atomic theory by proposing his nuclear atomic model.",R.drawable.gold_foil_exp));

        TriviaAdapter adapter = new TriviaAdapter(triviaItems);
        triviaViewPager.setAdapter(adapter);
    }
}
