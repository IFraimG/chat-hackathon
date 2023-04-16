package com.example.todolisthwhackathon.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolisthwhackathon.R;
import com.example.todolisthwhackathon.activities.ChatAC;
import com.example.todolisthwhackathon.data.entities.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    Context ctx;
    private List<Chat> chats = new ArrayList<>();
    private final LayoutInflater inflater;

    public ChatListAdapter(Context context) {
        this.ctx = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatListAdapter.ViewHolder holder, int position) {
        Chat chat = chats.get(position);
        holder.title.setText(chat.inviteLink);

        holder.link.setOnClickListener(View -> {
            Intent intent = new Intent(this.ctx, ChatAC.class);
            intent.putExtra("chatID", chat.chatID);
            ctx.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void updateAdverts(List<Chat> chats) {
        if (this.chats != null) {
            try {
                this.chats.clear();
                this.chats.addAll(chats);
            } catch (NullPointerException err) {
                Log.e("msg", "null chats");
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        Button link;

        public ViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.chat_item_title);
            this.date = (TextView) view.findViewById(R.id.chat_item_date);
            this.link = (Button) view.findViewById(R.id.chat_item_link);
        }
    }
}
