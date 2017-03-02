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

        //Test Recommendations to show the queue
        Recommendation test1 = new Recommendation("test1.com", "text", "This is a test", "Kyle");
        Recommendation test2 = new Recommendation("test2.com", "text", "This is a test", "Matt");
        Recommendation test3 = new Recommendation("test3.com", "text", "This is a test", "Travis");
        Recommendation test4 = new Recommendation("test4.com", "text", "This is a test", "Stephen");
        recommendations.add(0,test1);
        recommendations.add(1,test2);
        recommendations.add(2,test3);
        recommendations.add(3,test4);
    }

    public void addFriend( User newFriend) {
        friendList.add(newFriend);
        friendCount++;
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


    public ArrayList<Recommendation> getRecommendations() { return recommendations; }

    public String getUserName(){
        return userName;
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
