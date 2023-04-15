package com.example.todolisthwhackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.todolisthwhackathon.adapters.ViewPagerAdapter;
import com.example.todolisthwhackathon.fragments.Page1;
import com.example.todolisthwhackathon.fragments.Page2;
import com.example.todolisthwhackathon.fragments.Page3;
import com.example.todolisthwhackathon.fragments.Page4;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());


        viewPagerAdapter.add(new Page1(), "Чат");
        viewPagerAdapter.add(new Page2(), "Профиль");
        viewPagerAdapter.add(new Page3(), "Настройки");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
