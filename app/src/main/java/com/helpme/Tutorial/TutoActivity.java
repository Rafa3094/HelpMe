package com.helpme.Tutorial;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.helpme.R;

import java.util.ArrayList;
import java.util.List;

public class TutoActivity extends AppCompatActivity {

    private ViewPager screenpager;
    private IntroViewPagerAdapter introAdapter;
    private TabLayout tabindicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuto);

        tabindicator = findViewById(R.id.tabIndicator);


        List<ScreenItem> nList = new ArrayList<>();
        nList.add(new ScreenItem("HelpMe", "Welcome to the HelpMe app! \nKeep sliding to know how to use it!", R.drawable.button_pressed));
        nList.add(new ScreenItem("", "", R.drawable.slide1));
        nList.add(new ScreenItem("", "", R.drawable.slide2));
        nList.add(new ScreenItem("", "", R.drawable.slide3));
        nList.add(new ScreenItem("", "", R.drawable.slide4));
        nList.add(new ScreenItem("", "", R.drawable.slide5));
        nList.add(new ScreenItem("", "", R.drawable.slide6));
        nList.add(new ScreenItem("Congratulations!", "You just finished the user guide! \nGo ahead and take care!", R.drawable.button_pressed));
        screenpager = findViewById(R.id.screen_viewpager);
        introAdapter = new IntroViewPagerAdapter(this, nList);
        screenpager.setAdapter(introAdapter);

        tabindicator.setupWithViewPager(screenpager);

        pressButtonNext();
    }

    private void pressButtonNext(){
        Button btn = findViewById(R.id.btntutonext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
