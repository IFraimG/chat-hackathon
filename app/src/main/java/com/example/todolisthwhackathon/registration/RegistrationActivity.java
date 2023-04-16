package com.example.todolisthwhackathon.registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolisthwhackathon.MainActivity;
import com.example.todolisthwhackathon.R;
import com.example.todolisthwhackathon.data.SharedPreferences.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RegistrationActivity extends AppCompatActivity {
    private EditText UserNameEdt, PasswordEdt;
    private ImageButton submit, chek;
    private String UserName, Password;
    private FirebaseFirestore db;
    private static int colUs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        db = FirebaseFirestore.getInstance();

        // initializing our edittext and buttons
        UserNameEdt = findViewById(R.id.userName);
        PasswordEdt = findViewById(R.id.editPassword);
        submit = findViewById(R.id.submit);

        // adding on click listener for button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting data from edittext fields.
                UserName = UserNameEdt.getText().toString();
                Password = PasswordEdt.getText().toString();

                CollectionReference collectionRef = db.collection("Users");

                if (TextUtils.isEmpty(UserName)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter  Username", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter  Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    Query query = collectionRef.whereEqualTo("userName", UserName);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String documentId = document.getId();

                                    // использовать полученный id документа
                                    colUs++;
                                }
                                System.out.println(colUs);
                                //чтобы имена пользователей не повторялись
                                if (colUs == 0) {

                                    // calling method to add data to Firebase Firestore.
                                    addDataToFirestore(UserName, Password);
                                }
                                else {
                                    Toast.makeText(RegistrationActivity.this, "Придумайте другое имя, данное имя уже существует", Toast.LENGTH_SHORT).show();
                                    colUs = 0;
                                }
                            }
                        }

                    });
                }
            }
        });
    }


    private void addDataToFirestore (String UserName, String Password) {

        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference dbUser = db.collection("Users");


        // adding our data to our courses object class.
        Registr registr = new Registr(UserName, Password);
        dbUser.add(registr).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                String id = documentReference.getId();
                saveAutorise(id, UserName, Password); // сохраняем данные пользователя в приложении для дальнейшего взаимодействия
                Toast.makeText(RegistrationActivity.this, "Поздравляем с успешной регистрацией", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(RegistrationActivity.this, "Возникла ошибка регистрации \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //при нажатии на log in
    public void onClickLogin(View view) {
        db = FirebaseFirestore.getInstance();
        UserNameEdt = findViewById(R.id.userName);
        PasswordEdt = findViewById(R.id.editPassword);
        UserName = UserNameEdt.getText().toString();
        Password = PasswordEdt.getText().toString();
        CollectionReference collectionRef = db.collection("Users");
        Query query = collectionRef.whereEqualTo("userName", UserName)
                .whereEqualTo("password", Password);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int colId = 0;
                    String id = "";
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String documentId = document.getId();
                        // использовать полученный id документа
                        id = documentId;
                        colId++;
                    }
                    if (colId == 1 && id != "") {
                        saveAutorise(id, UserName, Password);
                        Toast.makeText(RegistrationActivity.this, "Поздравляем с успешной авторизоцией!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    }
                    else {
                        Toast.makeText(RegistrationActivity.this, "Данные введены неверно", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    // сохранение данных пользователя
    private void saveAutorise(String id, String userName, String password) {
        SharedPreferences sPref = getSharedPreferences(Constants.AUTO, MODE_PRIVATE); //присватваем переменной объект шаредпреференс приватный
        SharedPreferences.Editor ed = sPref.edit(); //получаем объект эдитор для редактирования данных
        ed.putString(Constants.USER_ID, id); //в методе putString указываем наименование переменной(SAVED_TEXT) и вторым аргументом вводим значение переменной
        ed.putString(Constants.USER_NAME, userName); //в методе putString указываем наименование переменной(SAVED_TEXT) и вторым аргументом вводим значение переменной
        ed.putString(Constants.PASSWORD, password); //в методе putString указываем наименование переменной(SAVED_TEXT) и вторым аргументом вводим значение переменной
        ed.commit(); // это нужно для сохранения данных
    }
}
