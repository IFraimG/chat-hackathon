package com.example.todolisthwhackathon.data.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.todolisthwhackathon.data.entities.Chat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

public class CustomFirebase {
    static private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    static public List<Chat> getAllChats(String userID) {
        List<Chat> list = new ArrayList<>();

        mDatabase.child("chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Chat chat = ds.getValue(Chat.class);
                        if (chat.usersID != null) {
                            for (int i = 0; i < chat.usersID.size(); i++) {
                                if (chat.usersID.get(i).equals(userID)) list.add(chat);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        ValueEventListener chatsListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot ds: dataSnapshot.getChildren()) {
//                    Chat chat = ds.getValue(Chat.class);
//                    if (chat.usersID != null) {
//                        for (int i = 0; i < chat.usersID.size(); i++) {
//                            if (chat.usersID.get(i).equals(userID)) list.add(chat);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w("err", "loadPost:onCancelled", databaseError.toException());
//            }
//        };
//        mDatabase.addValueEventListener(chatsListener);
        return list;
    }

    static public void createChat(Chat chat) {
        mDatabase.child("chats").child(chat.chatID).setValue(chat);
    }

    static public Chat findChat(String chatID) {
        List<Chat> list = new ArrayList<>();
        mDatabase.child("chats").child(chatID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.add(snapshot.getValue(Chat.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list.get(0);
    }
}
