package look.myapplication;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Matt on 2/17/2017.
 */

class User {
    private String fullName;
    public String userName;
    private String eMail;
    private String phoneNumber;
    private String password;
    private ArrayList<User> friendList = new ArrayList<>();
    private ArrayList<String> groups = new ArrayList<>();
    private ArrayList<Recommendation> recommendations = new ArrayList<>();
    private int friendCount;

    // Initializer for User object
    User(String userName, String fullName, String password, String phoneNumber, String eMail) {
        this.userName = userName;
        this.eMail = eMail;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.fullName  = fullName;
        friendCount = 0;

        //Test Recommendations to show the queue
        for(int i = 0; i < 10; i++){
            Recommendation rec = new Recommendation("Test"+(i+1)+".com", "text", "This is a test", "Kyle");
            recommendations.add(rec);
        }
    }

    public void addFriend(User newFriend) {
        friendList.add(newFriend);
        friendCount++;
    }

    public void addGroup(String group) {
        groups.add(group);
    }


    public void newRecommendation(String link, String type, String description, String recipient) {
        Recommendation r = new Recommendation(link, type, description, recipient);
        r.setSender(this);
        r.send();

    }
    public void addRecommendation(Recommendation r) {
        for(int i = 0; i < friendCount; i++) {
            if (r.getSender().userName.equals(friendList.get(i).userName)) {
                recommendations.add(r);
                break;
            }
        }
    }



    public ArrayList<Recommendation> getRecommendations() {
        return recommendations;
    }


    public String getUserName(){
        return userName;
    }

    public int getFriendCount() { return friendCount; }

    public String getFullName() {
        return fullName;
    }

    public String geteMail() {
        return eMail;
    }

    public ArrayList<User> getFriendList() {
        return friendList;
    }

    public ArrayList<String> getGroups() {
        return groups;
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
