package com.meads30gmail.look;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Matt on 2/17/2017.
 */

class User {
    private String fullName;
    private String userName;
    private String eMail;
    private String phoneNumber;
    private String password;
    private ArrayList<User> friendList = new ArrayList<>();
    private int friendCount;
    private ArrayList<Recommendation> recommendations = new ArrayList<>();

    // Initializer for User object
    User(String fullName, String userName, String password, String phoneNumber, String eMail) {
        this.userName = userName;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.fullName  = fullName;
        friendCount = 0;

    }

    public void addFriend( User newFriend) {
        friendList.add(newFriend);
        friendCount++;
    }

    public String getUserName(){
        return userName;
    }

    public void newRecommendation(String link, String type, User sender, User recipient) {
        Recommendation r = new Recommendation(link, type, sender, recipient);
        r.send();
        Log.d("friendTest", recipient.friendList.get(0).userName + "\n\n\n");
    }
    public void addRecommendation(Recommendation r) {
        for(int i = 0; i < friendCount; i++) {
            if (r.getSender().userName.equals(friendList.get(i).userName)) {
                recommendations.add(r);
                break;
            }
        }
    }



    public String getFullName() {
        return fullName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
