package look.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kyleyo on 4/2/2017.
 */
public class MainActivityTest {
    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void sendNotification() throws Exception {

    }

    @Test
    public void signup() throws Exception {
        String fullName = "test3";
        String userName = "test4";
        String passWord = "test1234EEE";
        String phoneNumber = "3767488472";
        String emailAddress = "test@test.com";

        String test = new SignupActivity(null).doInBackground(fullName, userName, passWord, phoneNumber, emailAddress);

        assertEquals(test, "{\"query_result\":\"SUCCESS\"}");

    }

    @Test
    public void login() throws Exception {
        String userName = "testUsername";
        String passWord = "TestPassword12";

        String test = new LoginActivity(null).doInBackground(userName, passWord);

        assertEquals(test, "{\"query_result\":\"SUCCESS\"}");

    }

    @Test
    public void changePass() throws Exception {

    }

    @Test
    public void createRecommendation() throws Exception {
        String recipient = "Travis";
        String recDescription = "test";
        String recType = "test";
        String recLink = "test";

        String content = "description:" + recDescription + "|type:" + recType + "|link" + recLink;

        String test = new CreateRecommendationActivity(null, null).doInBackground("test", "test", content);

        assertEquals(test, "{\"query_result\":\"SUCCESS\"}");

    }

    @Test
    public void addFriend() throws Exception {
        String friendName = "friend";
        String test = new CreateNotificationActivity(null).doInBackground("test", friendName, "test added you as a friend");

        assertEquals("{\"query_result\":\"SUCCESS\"}", test);

    }

    @Test
    public void groupsScreen() throws Exception {

    }

    @Test
    public void friendScreen() throws Exception {

    }

    @Test
    public void setFriendScreen() throws Exception {

    }

    @Test
    public void changefavoritesScreen() throws Exception {

    }

    @Test
    public void changeNotificationScreen() throws Exception {

    }

    @Test
    public void notificationScreen() throws Exception {

    }

    @Test
    public void deleteQ() throws Exception {

    }

    @Test
    public void queueScreen() throws Exception {

    }

    @Test
    public void signupScreen() throws Exception {

    }

    @Test
    public void profileScreen() throws Exception {

    }

    @Test
    public void addFriendScreen() throws Exception {

    }

    @Test
    public void loginScreen() throws Exception {

    }

    @Test
    public void changePassScreen() throws Exception {

    }

    @Test
    public void changeRecScreen() throws Exception {

    }

    @Test
    public void changeQueueScreen() throws Exception {

    }

    @Test
    public void getLoggedIn() throws Exception {

    }

    @Test
    public void setLoggedIn() throws Exception {

    }

    @Test
    public void logout() throws Exception {

    }

    @Test
    public void getContext() throws Exception {

    }

    @Test
    public void onCreateOptionsMenu() throws Exception {

    }

    @Test
    public void onOptionsItemSelected() throws Exception {

    }

}