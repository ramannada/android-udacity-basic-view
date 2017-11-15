package com.example.ramannada.mymiwok;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewpager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(fragmentAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout_words);
        tabLayout.setupWithViewPager(viewPager);

    }
}
