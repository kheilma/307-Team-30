package look.myapplication;

/**
 * Created by kyleyo on 2/18/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private boolean loggedIn;
    private boolean spinnerSet;
    private String user;
    private User current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        loggedIn = false;
        spinnerSet = false;
    }

    public void sendNotification(View v, User sender, String receiver){

    }

    public void signup(View v) {
        EditText etFullName = (EditText) findViewById(R.id.etFullName);
        EditText etUserName = (EditText) findViewById(R.id.etUserName);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        EditText etPhone = (EditText) findViewById(R.id.etPhone);
        EditText etEmail = (EditText) findViewById(R.id.etEmail);

        String fullName = etFullName.getText().toString();
        String userName = etUserName.getText().toString();
        String passWord = etPassword.getText().toString();
        String phoneNumber = etPhone.getText().toString();
        String emailAddress = etEmail.getText().toString();

        Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();
        new SignupActivity(this).execute(fullName, userName, passWord, phoneNumber, emailAddress);
    }

    public void login(View v) {
        EditText loginUsername = (EditText) findViewById(R.id.loginUserName);
        EditText loginPassword = (EditText) findViewById(R.id.loginPassword);
        String userName = loginUsername.getText().toString();
        String passWord = loginPassword.getText().toString();

        user = "'" + userName + "'";

        Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();
        new LoginActivity(this).execute(userName, passWord);
    }

    public void changePass(View v) {
        EditText currPassword = (EditText) findViewById(R.id.currentPasswordChange);
        EditText newPassword = (EditText) findViewById(R.id.newPasswordChange);
        String currPass = currPassword.getText().toString();
        String newPass = newPassword.getText().toString();

        Toast.makeText(this, "Attempting to change password...", Toast.LENGTH_LONG).show();
        new ChangePasswordActivity(this).execute(currPass, newPass);
    }

    public void createRecommendation(View v){
        // Holds code for creating recommendation after clicking the button on the profile
        Toast.makeText(this, "Created Recommendation", Toast.LENGTH_SHORT).show();

        EditText name = (EditText) findViewById(R.id.destinationUserName);
        String recipient = name.getText().toString();
        EditText description = (EditText) findViewById(R.id.description);
        String recDescription = description.getText().toString();
        EditText type = (EditText) findViewById(R.id.recType);
        String recType = type.getText().toString();
        EditText link = (EditText) findViewById(R.id.link);
        String recLink = link.getText().toString();

        String content = "description:" + recDescription + "|type:" + recType + "|link" + recLink;
        new CreateRecommendationActivity(this, current_user).execute(user.substring(1, user.length()-1), recipient, content);
    }

    public void addFriend(View v) {
        EditText name = (EditText) findViewById(R.id.friendUserName);
        String friendName = name.getText().toString();

        Toast.makeText(this, "Adding " + friendName + " as a friend", Toast.LENGTH_SHORT).show();
        name.setText("");

        Toast.makeText(this, "Sending notification...", Toast.LENGTH_SHORT).show();
        new CreateNotificationActivity(this).execute(current_user.getUserName(), friendName, current_user.getUserName()+" added you as a friend.");
    }

    public void friendScreen(final View v) {
        setContentView(R.layout.friendlist);
        TableLayout t = (TableLayout) findViewById(R.id.friendListTable);
        t.removeAllViews();
        t.removeAllViewsInLayout();
        ArrayList<User> friends = current_user.getFriendList();
        String[] names = new String[4];
        names[0] = "Matt";
        names[1] = "Travis";
        names[2] = "Stephen";
        names[3] = "Kyle";

        for( int i = 0; i < 4; i++) {
           //names[i] = friends.get(i).getUserName();
            TableRow row = new TableRow(this);
            TextView text = new TextView(this);
            CheckBox box = new CheckBox(this);
            Button rec = new Button(this);
            Button delete = new Button(this);

            text.setText(names[i]);
            text.setTextColor(Color.MAGENTA);
            text.setGravity(Gravity.CENTER);
            text.setTextSize(24);

            box.setText("");
            box.setTextSize(24);

            //this button currently send the user to the create recommendation screen
            // ideally, it should be updated so the recipient is filled in already
            rec.setText("Recommend");
            rec.setTextSize(24);
            rec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeRecScreen(v);
                }
            }); {

            }

            delete.setText("Unfriend");
            delete.setTextSize(24);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // not sure how the database stuff works but deleting the person would go here
                    // name of friend is saved into name[i] for searching the database
                    // for now i guess back to the profile screen
                    profileScreen(v);
                }
            });

            row.addView(text);
            row.addView(box);
            row.addView(rec);
            row.addView(delete);
            t.addView(row);

        }
        new ViewFriendsActivity(this).execute(current_user);
    }

    public void changeNotificationScreen(View v) {
        setContentView(R.layout.notifications);
        String userName = user.substring(1, user.length()-1);
        new getNotificationActivity(this).execute(userName);
    }

    public void notificationScreen(String nList) {
        String[] contentArray = nList.split("\n");

        TableLayout stk = (TableLayout) findViewById(R.id.notification_table);
        stk.removeAllViews();
        stk.removeAllViewsInLayout();

        for (int i = 0; i < contentArray.length; i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(contentArray[i]);
            t1v.setTextSize(24);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);

            stk.addView(tbrow);
        }
    }

    public void queueScreen(String recQ) {
        String[] contentArray = recQ.split("\n");

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        stk.removeAllViews();
        stk.removeAllViewsInLayout();
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.table_main);
        for (int i = 0; i < contentArray.length; i++) {
            Button deleteButton = new Button(this);
            deleteButton.setText("delete");
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(contentArray[i]);
            t1v.setTextSize(24);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            stk.addView(tbrow);
            mainLayout.addView(deleteButton);
        }
    }

    public void signupScreen(View v) {
        if (!loggedIn) {
            setContentView(R.layout.signup);
        }
    }

    public void profileScreen(View v) {

        setContentView(R.layout.profile);
    }

    public void addFriendScreen (View v) {
        setContentView(R.layout.newfriend);
    }

    public void loginScreen(View v) {
        if(!loggedIn) {
            setContentView(R.layout.login);
        }
    }

    public void changePassScreen(View v) {

        setContentView(R.layout.changepassword);
    }

    public void changeRecScreen(View v) {
        setContentView(R.layout.create);
    }

    public void changeQueueScreen(View v) {
        setContentView(R.layout.queue);
        String userName = user.substring(1, user.length()-1);
        new getQueueActivity(this).execute(userName);
    }

    public boolean getLoggedIn() {
        return this.loggedIn;
    }

    public void setLoggedIn(boolean loggedIn, String userName) {
        this.loggedIn = loggedIn;
        current_user = new User(userName, null, null, null, null);
        setContentView(R.layout.queue);
        Toast.makeText(this, "Loading recommendations for " + userName, Toast.LENGTH_SHORT).show();
        new getQueueActivity(this).execute(userName);
    }

    public Context getContext() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
