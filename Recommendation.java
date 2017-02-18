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
    private User recipient;
    private String date;
    private String link;

    public Recommendation(String link, String type, User sender, User recipient) {
        this.link = link;
        this.type = type;
        this.sender = sender;
        this.recipient = recipient;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date2 = new Date();
        this.date = dateFormat.format(date2);
        Log.d("Date Test\n\n\n",this.date);
    }

    public void send() {
        recipient.addRecommendation(this);
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
}
