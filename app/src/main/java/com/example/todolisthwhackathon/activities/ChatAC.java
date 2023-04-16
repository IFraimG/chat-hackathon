package com.example.todolisthwhackathon.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolisthwhackathon.R;
import com.example.todolisthwhackathon.adapters.MessageListAdapter;
import com.example.todolisthwhackathon.data.SharedPreferences.Constants;
import com.example.todolisthwhackathon.data.api.CustomFirebase;
import com.example.todolisthwhackathon.data.entities.Chat;
import com.example.todolisthwhackathon.data.entities.Message;
import com.example.todolisthwhackathon.view_models.ChatListViewModel;
import com.example.todolisthwhackathon.view_models.MessageListViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

public class ChatAC extends AppCompatActivity {
    MessageListViewModel viewModel;
    Chat chat;
    String userID;
    String chatID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (getIntent() != null) {
            chatID = getIntent().getStringExtra("chatID");
            System.out.println(chatID);

            FirebaseDatabase.getInstance().getReference().child("chats").child(chatID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        chat = snapshot.getValue(Chat.class);
                        TextView titleChat = (TextView) findViewById(R.id.titleChat);
                        titleChat.setText(chat.inviteLink);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.messages_recycler);
            MessageListAdapter messageListAdapter = new MessageListAdapter(this);
            recyclerView.setAdapter(messageListAdapter);
            recyclerView.setVerticalScrollBarEnabled(true);

            SharedPreferences sPref1 = getSharedPreferences(Constants.AUTO, Context.MODE_PRIVATE);
            userID = sPref1.getString(Constants.USER_ID, "0");


            viewModel = new ViewModelProvider(this,
                    (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                    .get(MessageListViewModel.class);


            viewModel.getAllMessages(userID, chatID).observe(this, new Observer<List<Message>>() {
                @Override
                public void onChanged(List<Message> messages) {
                    messageListAdapter.updateMessages(messages);
                }
            });

            ImageView sendMessage = (ImageView) findViewById(R.id.sendMessage);
            EditText messageInput = (EditText) findViewById(R.id.message_text);
            sendMessage.setOnClickListener(View -> {
                if (messageInput.getText().toString().length() > 0) {
                    Message message = new Message(messageInput.getText().toString(), userID, chatID);
                    FirebaseDatabase.getInstance().getReference().child("messages").child(message.messageID).setValue(message);
                    messageInput.setText("");
                }
            });

            TextView returnChat = (TextView) findViewById(R.id.returnChat);
            returnChat.setOnClickListener(View -> finish());
        }
    }
}
