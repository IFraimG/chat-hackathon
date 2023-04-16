package com.example.todolisthwhackathon;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.example.todolisthwhackathon.adapters.ViewPagerAdapter;
import com.example.todolisthwhackathon.data.SharedPreferences.Constants;
import com.example.todolisthwhackathon.fragments.Fragment_Chat;
import com.example.todolisthwhackathon.fragments.Fragment_Profil;
import com.example.todolisthwhackathon.fragments.Fragment_Settings;
import com.example.todolisthwhackathon.registration.RegistrationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    public static String NameForProfil = ""; // имя пользователя для фрагмента аккаунта
    public static String PasswordForProfil = ""; // пароль пользователя для фрагмента аккаунта
    public static String UserId = ""; // id пользователя для фрагмента аккаунта

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private EditText editName, editPassword;


    private FirebaseFirestore db;
    private static int colUs = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sPref11 = getSharedPreferences(Constants.AUTO, MODE_PRIVATE);
        String userName = sPref11.getString(Constants.USER_NAME, "0");
        String password = sPref11.getString(Constants.PASSWORD, "0");
        String idU = sPref11.getString(Constants.USER_ID, "0");
        NameForProfil = userName;
        PasswordForProfil = password;
        UserId = idU;



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

    //кнопка выхода из приложения
    public void onClickLogOut(View view) {
        SharedPreferences sPref = getSharedPreferences(Constants.AUTO, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(Constants.USER_ID, "0");
        ed.commit();
        Toast.makeText(MainActivity.this, "Вы успешно вышли из аккаунта", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
    }

    //кнопка для удаления аккаунта
    public void onClickDel(View view) {
        CollectionReference collectionRef = db.collection("Users");
        SharedPreferences sPref1 = getSharedPreferences(Constants.AUTO, MODE_PRIVATE);
        String id = sPref1.getString(Constants.USER_ID, "0");
        if (!(id.equals("0"))) {
            String docId = id;
            collectionRef.document(docId).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            SharedPreferences sPref = getSharedPreferences(Constants.AUTO, MODE_PRIVATE);
                            SharedPreferences.Editor ed = sPref.edit();
                            ed.putString(Constants.USER_ID, "0");
                            ed.commit();
                            Toast.makeText(MainActivity.this, "Аккаунт успешно удален", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error deleting document", e);
                        }
                    });

        }
    }
}
