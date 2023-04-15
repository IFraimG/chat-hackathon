package com.example.todolisthwhackathon.registration;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolisthwhackathon.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {

    // creating variables for our edit text
    private EditText UserNameEdt, PasswordEdt;

    // creating variable for button
    private ImageButton submit, chek;

    // creating a strings for storing
    // our values from edittext fields.
    private String UserName, Password;

    // creating a variable
    // for firebasefirestore.
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // getting our instance
        // from Firebase Firestore.
        db = FirebaseFirestore.getInstance();

        // initializing our edittext and buttons
        UserNameEdt = findViewById(R.id.userName);
        PasswordEdt = findViewById(R.id.password);
        submit = findViewById(R.id.submit);

        // adding on click listener for button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting data from edittext fields.
                UserName = UserNameEdt.getText().toString();
                Password = PasswordEdt.getText().toString();


                if (TextUtils.isEmpty(UserName)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter  Username", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(RegistrationActivity.this, "Please enter  Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(UserName, Password);
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
                Toast.makeText(RegistrationActivity.this, "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(RegistrationActivity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
