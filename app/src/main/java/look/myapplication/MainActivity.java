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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private boolean loggedIn;
    private boolean spinnerSet;
    public ArrayList<String> delete = new ArrayList<>(20);
    public ArrayList<String> faves = new ArrayList<>(20);
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

    public void removeRecommendation(View v, String content) {
        Toast.makeText(this, "Removed Recommendation", Toast.LENGTH_SHORT).show();

        String name = user;
        new RemoveRecommendationActivity(this, current_user).execute(user.substring(1, user.length()-1), content);
    }

    public void addFriend(View v) {
        EditText name = (EditText) findViewById(R.id.friendUserName);
        String friendName = name.getText().toString();

        Toast.makeText(this, "Adding " + friendName + " as a friend", Toast.LENGTH_SHORT).show();
        name.setText("");

        Toast.makeText(this, "Sending notification...", Toast.LENGTH_SHORT).show();
        new CreateNotificationActivity(this).execute(current_user.getUserName(), friendName, current_user.getUserName()+" added you as a friend.");
    }

    public void groupsScreen (final View v) {
        setContentView(R.layout.groups);
        TableLayout table = (TableLayout) findViewById(R.id.groupTable);
        table.removeAllViewsInLayout();
        table.removeAllViews();
        ArrayList<String> groups = current_user.getGroups();
        for(int i = 0; i < 110; i++) {
            groups.add(i, "REally long name to see how it works on the screen, a few more letters to push it off the screen " + i);
            TableRow row = new TableRow(this);
            TextView text =  new TextView(this);
            Button view = new Button(this);

            groups.set(0, "Short");
            String gName = groups.get(i);
            if(gName.length() < 21) {
                text.setText(gName);
            }
            else {
                text.setText(gName.substring(0,20));
            }
            text.setTextSize(24);
            text.setPadding(10,0,50,0);
            row.addView(text);

            view.setText("View");
            view.setGravity(Gravity.RIGHT);
            view.setTextSize(24);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Viewing Group",Toast.LENGTH_SHORT ).show();
                }
            });
            row.addView(view);
            table.addView(row);
        }
    }

    public void friendScreen(View v) {
        String userName = user;
        setContentView(R.layout.friendlist);
        Toast.makeText(this, "Bringing up your friends...", Toast.LENGTH_SHORT).show();
        new ViewFriendsActivity(this).execute(userName);
    }

    public void setFriendScreen(String friendsString) {
        setContentView(R.layout.friendlist);
        TableLayout t = (TableLayout) findViewById(R.id.friendListTable);
        t.removeAllViews();
        t.removeAllViewsInLayout();
        String[] names = friendsString.split("\n");

        for( int i = 0; i < names.length; i++) {
            TableRow row = new TableRow(this);
            TextView text = new TextView(this);
            Button rec = new Button(this);
            Button delete = new Button(this);

            String name = names[i].substring(0, names[i].indexOf('|'));
            String accepted = names[i].substring(names[i].indexOf('|')+1);

            if(accepted.equals("1")) {
                text.setText(name);
                text.setTextSize(16);
            } else {
                name += "\n (not accepted)";
                text.setText(name);
                text.setTextSize(8);
            }
            text.setPadding(0,0,20,0);
            text.setTextColor(Color.MAGENTA);
            text.setGravity(Gravity.CENTER);

            //this button currently send the user to the create recommendation screen
            // ideally, it should be updated so the recipient is filled in already
            rec.setText("Recommend");
            rec.setTextSize(16);
            rec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeRecScreen(view);
                }
            }); {

            }

            delete.setText("Unfriend");
            delete.setTextSize(16);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // not sure how the database stuff works but deleting the person would go here
                    // name of friend is saved into name[i] for searching the database
                    // for now i guess back to the profile screen
                    profileScreen(view);
                }
            });

            row.addView(text);
            row.addView(rec);
            row.addView(delete);
            t.addView(row);

        }
    }

    public void changefavoritesScreen(View V) {
        //setContentView(R.layout.favorites);
        //develop favorites from database
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
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            TextView t1v = new TextView(this);
            t1v.setText(contentArray[i]);
            t1v.setTextSize(24);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.LEFT);
            t1v.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));

            Button accept = new Button(this);
            accept.setText("Accept");
            accept.setTextSize(24);
            accept.setTextColor(Color.BLACK);
            accept.setGravity(Gravity.RIGHT);
            accept.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));


            Button ignore = new Button(this);
            ignore.setText("Ignore");
            ignore.setTextSize(24);
            ignore.setTextColor(Color.BLACK);
            ignore.setGravity(Gravity.RIGHT);
            ignore.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            row.addView(t1v);
            row.addView(accept);
            row.addView(ignore);

            stk.addView(row);
        }
    }

    public void deleteQ(String recQ) {
        setContentView(R.layout.qdelete);
        String[] contentArray = recQ.split("\n");
        final String fullContent = new String(recQ);
        TableLayout dTable = (TableLayout) findViewById(R.id.deleteQTable);
        dTable.setBackgroundColor(Color.WHITE);
        dTable.removeAllViews();
        dTable.removeAllViewsInLayout();
        for (int i = 0; i < contentArray.length; i++) {
            RelativeLayout row = new RelativeLayout(this);
            TextView text = new TextView(this);
            final String content = contentArray[i];
            text.setText(content);
            text.setBackgroundColor(Color.WHITE);
            text.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            ));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)text.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

            final CheckBox box = new CheckBox(this);
            box.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
            );
            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(box.isChecked()) {
                        delete.add(content);
                    }
                    else {
                        delete.remove(content);
                    }
                }
            });

            RelativeLayout.LayoutParams param1 = (RelativeLayout.LayoutParams)box.getLayoutParams();
            param1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            row.addView(text);
            row.addView(box);
            dTable.addView(row);
        }
        Button delete = (Button) findViewById(R.id.deleteQScrnBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call activity for deleting from Q
                profileScreen(getCurrentFocus());
                removeRecommendation(view, fullContent);
            }
        });

        Button back = (Button) findViewById(R.id.recQBack2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.queue);
                queueScreen(fullContent);
            }
        });

    }

    public void queueScreen(String recQ) {
        String[] contentArray = recQ.split("\n");
        final String fullContent = new String(recQ);
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        stk.setBackgroundColor(Color.WHITE);
        stk.removeAllViews();
        stk.removeAllViewsInLayout();
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.table_main);
        mainLayout.setBackgroundColor(Color.WHITE);
        for (int i = 0; i < contentArray.length; i++) {
            final String content = contentArray[i];
            Button submit = new Button(this);
            submit.setText("Submit");
            submit.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            ));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)submit.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

            RelativeLayout ratingBar = new RelativeLayout(getContext());

            TextView viewButton = new TextView(this);
            final ToggleButton toggleButton = new ToggleButton(this);
            toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.unfavorite));
            toggleButton.setText("");
            toggleButton.setTextOn("");
            toggleButton.setTextOff("");
            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(toggleButton.isChecked()) {
                        toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.favorite));
                        faves.add(content);
                    }
                    else {
                        toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.unfavorite));
                        faves.remove(content);
                    }
                }
            });


            TableRow tbrow = new TableRow(this);

            final RatingBar bar = new RatingBar(this);
            bar.setMax(5);
            bar.setStepSize(1);
            bar.setNumStars(5);
            bar.setProgress(0);
            bar.setBackgroundColor(Color.WHITE);
            bar.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            toggleButton.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));

            if(contentArray[i].length() > 20) {
                viewButton.setText(content.substring(0,19));
            }
            else {
                viewButton.setText(content);
            }
            viewButton.setTextSize(18);
            viewButton.setTextColor(Color.BLACK);
            viewButton.setGravity(Gravity.CENTER);
            viewButton.setBackgroundColor(Color.WHITE);
            viewButton.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));

            tbrow.addView(viewButton, TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
            tbrow.addView(toggleButton, TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
            stk.addView(tbrow);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = current_user.userName;
                    String rating =  "" + bar.getRating();
                    Toast.makeText(getContext(), "name: " + name + "content: " + content , Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "rating: " + rating, Toast.LENGTH_LONG).show();
                    new RateContentActivity(getContext()).execute(name,content,rating);
                }
            });
            TextView border = new TextView(this);
            border.setText("-");
            border.setTextSize(1);
            border.setTextColor(Color.BLACK);
            border.setBackgroundColor(Color.BLACK);

            ratingBar.addView(bar);
            ratingBar.addView(submit);
            mainLayout.addView(ratingBar);
            mainLayout.addView(border, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        Button delete = (Button) findViewById(R.id.QdeleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteQ(fullContent);
            }
        });

        Button back = (Button) findViewById(R.id.recQBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call activity to add favorites
                // consider calling activity in the delete button above
                profileScreen(getCurrentFocus());
            }
        });
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

    public void logout(View v){
        Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
        this.loggedIn = false;
        current_user = null;
        user = null;
        setContentView(R.layout.login);
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
