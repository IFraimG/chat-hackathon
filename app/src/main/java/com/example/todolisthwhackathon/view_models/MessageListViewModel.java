package com.example.todolisthwhackathon.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todolisthwhackathon.data.entities.Chat;
import com.example.todolisthwhackathon.data.entities.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageListViewModel extends AndroidViewModel {
    public List<Message> messages = new ArrayList<>();
    private final MutableLiveData<List<Message>> _messages = new MutableLiveData<>();

    public MessageListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Message>> getAllMessages(String userID, String chatID) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Message> list = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Message message = ds.getValue(Message.class);
                        if (message != null && message.chatID.equals(chatID)) {
                            message.setLogin("test");

                            list.add(message);
                        }

                    }
                    _messages.setValue(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return _messages;
    }

    public void addChat(Message message) {
        messages.add(message);
        _messages.setValue(messages);
        // запрос на сервер
    }
}

