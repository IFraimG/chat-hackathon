package com.example.todolisthwhackathon.data.entities;

import java.util.Date;
import java.util.UUID;

public class Message {
    public String authorID;
    public String text;
    public String chatID;
    public String messageID;
    public String dateOfCreated;
    public String login;

    public Message() {
    }

    public Message(String text, String authorID, String chatID) {
        this.authorID = authorID;
        this.chatID = chatID;
        this.text = text;
        this.dateOfCreated = Long.toString(new Date().getTime());
        this.login = "";
        this.messageID = UUID.randomUUID().toString();
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
