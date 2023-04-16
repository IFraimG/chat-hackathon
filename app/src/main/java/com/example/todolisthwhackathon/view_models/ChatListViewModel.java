package com.example.todolisthwhackathon.view_models;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.todolisthwhackathon.data.api.CustomFirebase;
import com.example.todolisthwhackathon.data.entities.Chat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class ChatListViewModel extends AndroidViewModel {
    public List<Chat> chats = new ArrayList<>();
    private final MutableLiveData<List<Chat>> _chats = new MutableLiveData<>();

    public ChatListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Chat>> getAllChats(String userID) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Chat> list = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Chat chat = ds.getValue(Chat.class);
                        if (chat.usersID != null) {
                            for (int i = 0; i < chat.usersID.size(); i++) {
                                if (chat.usersID.get(i).equals(userID)) list.add(chat);
                            }
                        }
                    }
                    _chats.setValue(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        chats.add(chat);
//        _chats.setValue(list);
//        _chats.setValue(CustomFirebase.getAllChats(userID));

        return _chats;
    }

    public void addChat(Chat chat) {
        chats.add(chat);
        _chats.setValue(chats);
        // запрос на сервер
    }
}

