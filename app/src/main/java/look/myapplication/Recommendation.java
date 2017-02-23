package com.meads30gmail.look;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Matt on 2/18/2017.
 */

public class Recommendation {
    private String type;
    private User sender;
    private String recipient;
    private String date;
    private String link;
    private String description;

    public Recommendation(String link, String type, String description, String recipient) {
        this.link = link;
        this.type = type;
        this.recipient = recipient;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date2 = new Date();
        this.date = dateFormat.format(date2);
        // a console print statement to make sure date is working
        Log.d("Date Test\n\n\n",this.date);
    }

    public void send() {
        Log.d("Send", "recommendation sent\n\n\n\n");
    }

    public User getSender() {
        return sender;
    }

    public String getType() {
        return type;
    }

    public String getLink() {
        return link;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
