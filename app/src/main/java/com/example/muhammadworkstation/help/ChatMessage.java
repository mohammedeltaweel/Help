package com.example.muhammadworkstation.help;

/**
 * Created by Muhammad Workstation on 18/03/2016.
 */
public class ChatMessage {

    private String author,message;

    public ChatMessage(){

    }
    public ChatMessage(String author, String message){
        this.author=author;
        this.message=message;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }
}
