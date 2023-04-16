package com.example.todolisthwhackathon.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.todolisthwhackathon.data.api.CustomFirebase;
import com.example.todolisthwhackathon.data.entities.Chat;

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
        Chat chat = new Chat("iiii");
        chats.add(chat);
        _chats.setValue(chats);
//        _chats.setValue(CustomFirebase.getAllChats(userID));

        return _chats;
    }

    public void addChat(Chat advertisement) {
        chats.add(advertisement);
        _chats.setValue(chats);
        // запрос на сервер
    }
}

