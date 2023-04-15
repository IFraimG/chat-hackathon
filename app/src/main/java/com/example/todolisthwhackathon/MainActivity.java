package com.example.todolisthwhackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.example.todolisthwhackathon.adapters.ViewPagerAdapter;
import com.example.todolisthwhackathon.data.SharedPreferences.Constants;
import com.example.todolisthwhackathon.fragments.Fragment_Chat;
import com.example.todolisthwhackathon.fragments.Fragment_Profil;
import com.example.todolisthwhackathon.fragments.Fragment_Settings;
import com.example.todolisthwhackathon.registration.RegistrationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    private FirebaseFirestore db;
    private static int colUs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        SharedPreferences sPref1 = getSharedPreferences(Constants.AUTO, MODE_PRIVATE);
        String id = sPref1.getString(Constants.USER_ID, "0");
        DocumentReference docRef = db.collection("Users").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Документ с указанным ID существует в Firestore
                    } else {
                        // Документ с указанным ID не существует в Firestore
                        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });


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
