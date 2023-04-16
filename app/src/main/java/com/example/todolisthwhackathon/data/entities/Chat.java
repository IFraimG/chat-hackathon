package com.example.todolisthwhackathon.data.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Chat {
    public String chatID;
    public List<String> usersID;
    public String title = "";
    public String inviteLink;
    public String createdAt = Long.toString(new Date().getTime());

    public Chat() {

    }

    public Chat(String userID) {
        this.chatID = UUID.randomUUID().toString();
        this.inviteLink = UUID.randomUUID().toString();
        this.usersID = new ArrayList<>();
        this.usersID.add(userID);
    }
}
