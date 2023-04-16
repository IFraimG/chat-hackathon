package com.example.todolisthwhackathon.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolisthwhackathon.R;
import com.example.todolisthwhackathon.activities.ChatAC;
import com.example.todolisthwhackathon.adapters.ChatListAdapter;
import com.example.todolisthwhackathon.data.SharedPreferences.Constants;
import com.example.todolisthwhackathon.data.api.CustomFirebase;
import com.example.todolisthwhackathon.data.entities.Chat;
import com.example.todolisthwhackathon.view_models.ChatListViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

public class Fragment_Chat extends Fragment {
    ChatListViewModel viewModel;
    String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        SharedPreferences sPref1 = getActivity().getSharedPreferences(Constants.AUTO, Context.MODE_PRIVATE);
        id = sPref1.getString(Constants.USER_ID, "0");
        
        RecyclerView recyclerView = view.findViewById(R.id.recycler_chats);

        ChatListAdapter chatListAdapter = new ChatListAdapter(getContext());
        recyclerView.setAdapter(chatListAdapter);
        recyclerView.setVerticalScrollBarEnabled(true);

        viewModel = new ViewModelProvider(getActivity(),
                (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(ChatListViewModel.class);

        viewModel.getAllChats(id).observe(getActivity(), new Observer<List<Chat>>() {
            @Override
            public void onChanged(List<Chat> chats) {
                chatListAdapter.updateAdverts(chats);
            }
        });

        Button createChat = (Button) view.findViewById(R.id.create_chat);
        Button joinChat = (Button) view.findViewById(R.id.join_chat);
        EditText inputId = (EditText) view.findViewById(R.id.input_id);

        createChat.setOnClickListener(View -> {
            Chat chat = new Chat(id);
            FirebaseDatabase.getInstance().getReference().child("chats").child(chat.chatID).setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Intent intent = new Intent(getContext(), ChatAC.class);
                    intent.putExtra("chatID", chat.chatID);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("err", e.toString());
                }
            });

        });


        joinChat.setOnClickListener(View -> {
            FirebaseDatabase.getInstance().getReference().child("chats").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                Chat chat = ds.getValue(Chat.class);
                                if (chat != null && chat.inviteLink.equals(inputId.getText().toString())) {
                                    List<String> usersID = chat.usersID;
                                    if (!usersID.contains(id)) {
                                        usersID.add(id);
                                        String key = ds.getKey();
                                        FirebaseDatabase.getInstance().getReference().child("chats").child(key).child("usersID").setValue(usersID);
                                        Toast.makeText(getContext(), "Вступление прошло успешно!", Toast.LENGTH_SHORT).show();
                                        inputId.setText("");

                                        Intent intent = new Intent(getActivity(), ChatAC.class);
                                        intent.putExtra("chatID", chat.chatID);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        });
        return view;
    }
}
