package look.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kyleyo on 4/2/2017.
 */
public class MainActivityTest {

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
    public void removeRecommendation() throws Exception {

        String recipient = "Travis";
        String recDescription = "test";
        String recType = "test";
        String recLink = "test";

        String content = "description:" + recDescription + "|type:" + recType + "|link" + recLink;

        String test = new RemoveRecommendationActivity(null, null).doInBackground("test", recipient, content);

        assertEquals(test, "{\"query_result\":\"SUCCESS\"}");
    }

    @Test
    public void addFriend() throws Exception {
        String friendName = "friend";
        String test = new CreateNotificationActivity(null).doInBackground("test", friendName, "test added you as a friend");

        assertEquals("{\"query_result\":\"SUCCESS\"}", test);

    }

    @Test
    public void newGroup() throws Exception {
        String groupName = "tester";
        String userName = "'test'";

        String test = new CreateGroupActivity(null).doInBackground(userName, groupName, "");

        assertEquals(test, "{\"query_result\":\"SUCCESS\"}");
    }


    @Test
    public void changeQueueScreen() throws Exception {
        String userName = "test";
        String test = new getQueueActivity(null).doInBackground(userName);

        assertEquals(test.substring(0, test.indexOf(',')), "{\"query_result\":\"SUCCESS\"");
    }

    @Test
    public void blockFriend() throws Exception {
        String userName = "test";
        String friendName = "friend";
        String test = new BlockActivity(null).doInBackground(userName, friendName);

        assertEquals(test, "{\"query_result\":\"SUCCESS\"");
    }




}