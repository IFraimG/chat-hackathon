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
import com.example.todolisthwhackathon.data.entities.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    Context ctx;
    private List<Message> messages = new ArrayList<>();
    private final LayoutInflater inflater;
    String authorID;

    public MessageListAdapter(Context context) {
        this.ctx = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MessageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageListAdapter.ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.text.setText(message.text);

        long dateLong = Long.parseLong(message.dateOfCreated);
        Date date = new Date(dateLong);

        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        holder.date.setText(df2.format(date));

//        holder.date.setText(message.dateOfCreated.toString());
        holder.user.setText(message.login);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void updateMessages(List<Message> messages) {
        if (this.messages != null) {
            try {
                this.messages.clear();
                this.messages.addAll(messages);
            } catch (NullPointerException err) {
                Log.e("msg", "null chats");
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView date;
        TextView user;

        public ViewHolder(View view) {
            super(view);
            this.text = (TextView) view.findViewById(R.id.msg_text);
            this.date = (TextView) view.findViewById(R.id.msg_date);
            this.user = (TextView) view.findViewById(R.id.msg_user);
        }
    }
}
