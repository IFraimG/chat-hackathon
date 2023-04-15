package com.example.todolisthwhackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.example.todolisthwhackathon.adapters.ViewPagerAdapter;
import com.example.todolisthwhackathon.fragments.Fragment_Chat;
import com.example.todolisthwhackathon.fragments.Fragment_Profil;
import com.example.todolisthwhackathon.fragments.Fragment_Settings;
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


        viewPagerAdapter.add(new Fragment_Chat(), "чат");
        viewPagerAdapter.add(new Fragment_Profil(), "профиль");
        viewPagerAdapter.add(new Fragment_Settings(), "настройки");



        viewPager.setAdapter(viewPagerAdapter);


        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
