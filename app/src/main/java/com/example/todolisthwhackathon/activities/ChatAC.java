package com.example.todolisthwhackathon.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolisthwhackathon.R;
import com.example.todolisthwhackathon.data.api.CustomFirebase;

public class ChatAC extends AppCompatActivity {

    EditText messageText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageText = findViewById(R.id.messagetext);

        if (getIntent() != null) {
//            String chatID = getIntent().getStringExtra("chatID");
//            CustomFirebase.findChat(chatID);
        }
    }
}
