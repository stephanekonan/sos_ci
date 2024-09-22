package com.example.sos_ci;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sos_ci.fragments.NumeroUrgenceFragment;
import com.example.sos_ci.fragments.NumeroVertFragment;

public class HomeActivity extends AppCompatActivity {

    private TextView table1, table2;
    private int selectedTabNumber = 1;

    @SuppressLint({"MissingInflatedId", "CommitTransaction"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        table1 = findViewById(R.id.table1);
        table2 = findViewById(R.id.table2);

        getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer, NumeroVertFragment.class, null);

        table1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(1);
            }
        });

        table2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTab(2);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("CommitTransaction")
    private void selectTab(int tabNumber) {

        TextView selectedTextView = null;
        TextView nonSelectedTextView = null;

        if(tabNumber == 1) {

            selectedTextView = table1;
            nonSelectedTextView = table2;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, NumeroUrgenceFragment.class, null);

        } else if (tabNumber == 2) {

            selectedTextView = table2;
            nonSelectedTextView = table1;

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, NumeroUrgenceFragment.class, null);

        }

        assert selectedTextView != null;
        float slideTo = (tabNumber - selectedTabNumber) * selectedTextView.getWidth();

        TranslateAnimation translateAnimation = new TranslateAnimation(0, slideTo, 0, 0);

        translateAnimation.setDuration(100);

        if(selectedTabNumber == 1) {
            table1.startAnimation(translateAnimation);
        } else if (selectedTabNumber == 2) {
            table2.startAnimation(translateAnimation);
        }

        TextView finalSelectedTextView = selectedTextView;
        TextView finalNonSelectedTextView = nonSelectedTextView;
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @SuppressLint("ResourceType")
            @Override
            public void onAnimationEnd(Animation animation) {
                finalSelectedTextView.setBackgroundResource(R.drawable.round_back_white_100);
                finalSelectedTextView.setTypeface(null, Typeface.BOLD);
                finalSelectedTextView.setTextColor(Color.BLACK);

                finalNonSelectedTextView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                finalNonSelectedTextView.setTextColor(Color.parseColor("#80FFFFFF"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        selectedTabNumber = tabNumber;
    }
}