package com.example.todolisthwhackathon.data.entities;

import java.util.Date;
import java.util.UUID;

public class Message {
    public String authorID;
    public String text;
    public String chatID;
    public String messageID;
    public Date dateOfCreated;
    public String login;

    public Message() {

    }

    public Message(String text, String authorID, String chatID) {
        this.authorID = authorID;
        this.chatID = chatID;
        this.dateOfCreated = new Date();
        this.login = "";
        this.messageID = UUID.randomUUID().toString();
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
