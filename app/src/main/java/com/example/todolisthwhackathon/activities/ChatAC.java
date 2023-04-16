package com.example.todolisthwhackathon.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolisthwhackathon.R;
import com.example.todolisthwhackathon.data.api.CustomFirebase;

public class ChatAC extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String chatID = getIntent().getStringExtra("chatID");
        CustomFirebase.findChat(chatID);
    }
}
