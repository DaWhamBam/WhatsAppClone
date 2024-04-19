package com.example.chatapp.model;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ChatMassage {

    String senderid;
    String text;
    long time;
    boolean isMine;

    public ChatMassage(String senderid, String text, long time) {
        this.senderid = senderid;
        this.text = text;
        this.time = time;

        if (senderid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            isMine = true;
        }
    }

    public ChatMassage() {
    }


    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public  String convertTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date(getTime());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }

}
