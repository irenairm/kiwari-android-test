package com.example.android.myapplication;



public class Message {
    String message;
    String sender;
    String receiver;
    String date;
    String time;

    public Message(){

    }
    public Message(String sender, String receiver, String messageText, String date, String time){
        message = messageText;
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }



    public String getSender() {
        return sender;
    }
    public String getReceiver() {
        return receiver;
    }



    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver){
        this.receiver = receiver;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
