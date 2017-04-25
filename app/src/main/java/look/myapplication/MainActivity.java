package look.myapplication;

/**
 * Created by kyleyo on 2/18/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private boolean loggedIn;
    private boolean spinnerSet;
    public ArrayList<String> delete = new ArrayList<>(20);
    public ArrayList<String> faves = new ArrayList<>(20);
    public ArrayList<String> unFave = new ArrayList<>(20);
    public String [] tagOpts = {"Comedy ", "Tragedy", "Horror", "Science", "Cute", "Sports", "News", "Food", "NSFW"};
    private int [] mediaTags = new int[tagOpts.length];
    private String user;
    private User current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            if (getIntent().getExtras() != null && loggedIn) {
                Log.d("log1", "test");
                setContentView(R.layout.create);
            } else {
                setContentView(R.layout.login);

                loggedIn = false;
                spinnerSet = false;
            }
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        try {
            setContentView(R.layout.profile);
        }catch(Exception e) {
        new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void sendNotification(View v, User sender, String receiver){

    }

    public void signup(View v) {
        try {
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
        }catch(Exception e) {
        new ReportBugActivity(this).execute(e.getMessage());
    }
    }

    public void login(View v) {
        try {
            EditText loginUsername = (EditText) findViewById(R.id.loginUserName);
            EditText loginPassword = (EditText) findViewById(R.id.loginPassword);
            String userName = loginUsername.getText().toString();
            String passWord = loginPassword.getText().toString();

            user = "'" + userName + "'";

            Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();
            new LoginActivity(this).execute(userName, passWord);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void changePass(View v) {
        try {
            EditText currPassword = (EditText) findViewById(R.id.currentPasswordChange);
            EditText newPassword = (EditText) findViewById(R.id.newPasswordChange);
            String currPass = currPassword.getText().toString();
            String newPass = newPassword.getText().toString();

            Toast.makeText(this, "Attempting to change password...", Toast.LENGTH_LONG).show();
            new ChangePasswordActivity(this).execute(user, currPass, newPass);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void createRecommendation(View v, int [] mediaTags){
        try {
            // Holds code for creating recommendation after clicking the button on the profile
            int test = 0;
            String value1 = "";
            if (getIntent().getExtras() != null) {
                Bundle extras = getIntent().getExtras();
                value1 = extras.getString(Intent.EXTRA_TEXT);
                test = 1;
            }
            Log.d("lol1", value1);
            Toast.makeText(this, "Created Recommendation", Toast.LENGTH_SHORT).show();

            EditText name = (EditText) findViewById(R.id.destinationUserName);
            String recipient = name.getText().toString();
            name.setText("");

            EditText description = (EditText) findViewById(R.id.description);
            String recDescription = description.getText().toString();
            description.setText("");

            EditText type = (EditText) findViewById(R.id.recType);
            String recType = type.getText().toString();
            type.setText("");

            EditText link = (EditText) findViewById(R.id.link);
            String recLink = link.getText().toString();
            link.setText("");

            String tags = "";
            for (int i = 0; i < mediaTags.length; i++) {
                tags += mediaTags[i] + ",";
            }
            tags = tags.substring(0, tags.length() - 1);

            changeRecScreen(getCurrentFocus());
            String content = "";
            if (test == 1) {
                content = "description:" + recDescription + "|type:" + recType + "|link" + value1 + "|tags:" + tags;
            } else {
                content = "description:" + recDescription + "|type:" + recType + "|link" + recLink + "|tags:" + tags;
            }
            new CreateRecommendationActivity(this, current_user).execute(user.substring(1, user.length() - 1), recipient, content);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void removeRecommendation(View v, String content) {
        try {
            Toast.makeText(this, "Removed Recommendation", Toast.LENGTH_SHORT).show();

            String recipient = user.substring(1, user.length() - 1);
            int indexOfRecipient = content.indexOf("&");
            String sender = content.substring(0, indexOfRecipient);
            String real_content = content.split("&")[2];
            new RemoveRecommendationActivity(this, current_user).execute(sender, recipient, real_content);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void addFriend(View v) {
        try {
            EditText name = (EditText) findViewById(R.id.friendUserName);
            String friendName = name.getText().toString();

            Toast.makeText(this, "Adding " + friendName + " as a friend", Toast.LENGTH_SHORT).show();
            name.setText("");

            addFriendScreen(getCurrentFocus());

            Toast.makeText(this, "Sending notification...", Toast.LENGTH_SHORT).show();
            new CreateNotificationActivity(this).execute(current_user.getUserName(), friendName, current_user.getUserName() + " added you as a friend.");
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void newGroup(View v) {
        try {
            setContentView(R.layout.creategroup);
            EditText group = (EditText) findViewById(R.id.groupName);
            String groupName = group.getText().toString();

            String userName = user;
            Toast.makeText(this, "Bringing up your friends...", Toast.LENGTH_SHORT).show();
            new ViewFriendsActivity(this).execute(userName, "1");
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }
    public void bugReportScreen(View v) {
        try {
            setContentView(R.layout.bugreport);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }
    public void newBugReport(View v) {
        try {
            EditText deviceModel = (EditText) findViewById(R.id.bugdevicemodel);
            EditText issue = (EditText) findViewById(R.id.bugissue);
            EditText version = (EditText) findViewById(R.id.bugappversion);

            String deviceModel1 = deviceModel.getText().toString();
            String issue1 = issue.getText().toString();
            String version1 = version.getText().toString();

            new SubmitBugActivity(this).execute(deviceModel1, issue1, version1);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void setNewGroupScreen(String friendsString) {
        try {
            TableLayout t = (TableLayout) findViewById(R.id.groupFriendsList);
            t.removeAllViews();
            t.removeAllViewsInLayout();
            String[] names = friendsString.split("\n");

            final ArrayList<String> addedFriends = new ArrayList<String>();

            for (int i = 0; i < names.length; i++) {
                TableRow row = new TableRow(this);
                TextView text = new TextView(this);
                Button rec = new Button(this);
                Button delete = new Button(this);
                if (names[i].indexOf('|') == -1) {
                    Toast.makeText(getContext(), "Add friends before creating a group!", Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.profile);
                    return;
                }
                String name = names[i].substring(0, names[i].indexOf('|'));
                String accepted = names[i].substring(names[i].indexOf('|') + 1);

                final String friendName = name;

                if (accepted.equals("1")) {
                    text.setText(name);
                    text.setTextSize(16);
                } else {
                    name += "\n (not accepted)";
                    text.setText(name);
                    text.setTextSize(8);
                }
                text.setPadding(0, 0, 20, 0);
                text.setTextColor(Color.MAGENTA);
                text.setGravity(Gravity.CENTER);

                rec.setText("Add To Group");
                rec.setTextSize(16);
                rec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addedFriends.add(0, friendName);
                        Toast.makeText(getContext(), "Added: " + friendName, Toast.LENGTH_SHORT).show();
                    }
                });
                {

                }
                row.addView(text);
                row.addView(rec);
                t.addView(row);

            }

            Button submit = (Button) findViewById(R.id.CreateGroupButton);
            submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String owner = user;
                    EditText editText = (EditText) findViewById(R.id.groupName);
                    String groupName = editText.getText().toString();
                    String selectedFriends = "";
                    for (String currName : addedFriends) {
                        selectedFriends += currName + ",";
                    }

                    new CreateGroupActivity(getContext()).execute(owner, groupName, selectedFriends);
                }
            });
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void groupsScreen(View v) {
        try {
            setContentView(R.layout.groups);
            String username = user.substring(1, user.length() - 1);

            new GetGroupsActivity(this).execute(username);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void getGroupsScreen (String recQ) {
        try {
            final String[] groupsArray = recQ.split("\n");
            setContentView(R.layout.groups);
            TableLayout table = (TableLayout) findViewById(R.id.groupTable);
            table.removeAllViewsInLayout();
            table.removeAllViews();
            for (int i = 0; i < groupsArray.length; i++) {
                TableRow row = new TableRow(this);
                TextView text = new TextView(this);
                Button view = new Button(this);

                if (groupsArray[i].indexOf('&') == -1) {
                    text.setText("You have no groups!");
                    row.addView(text);
                    table.addView(row);
                    return;
                }

                final String groupName = groupsArray[i].substring(0, groupsArray[i].indexOf('&'));


                if (groupName.length() < 21) {
                    text.setText(groupName);
                } else {
                    text.setText(groupName.substring(0, 20));
                }
                text.setTextSize(24);
                text.setPadding(10, 0, 50, 0);
                row.addView(text);

                view.setText("View");
                view.setGravity(Gravity.RIGHT);
                view.setTextSize(24);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setContentView(R.layout.create);
                        EditText name = (EditText) findViewById(R.id.destinationUserName);
                        String recipient = name.getText().toString();
                        name.setText("");

                        EditText description = (EditText) findViewById(R.id.description);
                        String recDescription = description.getText().toString();
                        description.setText("");

                        EditText type = (EditText) findViewById(R.id.recType);
                        String recType = type.getText().toString();
                        type.setText("");

                        EditText link = (EditText) findViewById(R.id.link);
                        String recLink = link.getText().toString();
                        link.setText("");

                        changeRecScreen(getCurrentFocus());

                        String content = "description:" + recDescription + "|type:" + recType + "|link" + recLink;
                        new CreateRecommendationActivity(getContext(), current_user).execute(user.substring(1, user.length() - 1), "1", content, groupName);
                        Toast.makeText(view.getContext(), "Viewing Group", Toast.LENGTH_SHORT).show();
                        viewGroup(groupsArray);
                    }
                });
                row.addView(view);
                table.addView(row);
            }
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void viewGroup( String [] groups) {
        try {
            setContentView(R.layout.viewcontent);
            TableLayout t = (TableLayout) findViewById(R.id.viewtable);
            for (int i = 0; i < groups[0].split("&").length; i++) {
                TextView text = new TextView(this);
                TableRow row = new TableRow(this);
                text.setText(groups[0].split("&")[i]);
                text.setTextSize(24);
                row.addView(text);
                t.addView(row);
            }
            Button back = (Button) findViewById(R.id.viewBack);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    groupsScreen(getCurrentFocus());
                }
            });
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }

    }

    public void acceptFriend(View v, String friend, String answer){
        try {
            String username = user.substring(1, user.length() - 1);
            System.out.println(username);
            new AcceptFriendActivity(this).execute(user, friend, answer);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void friendScreen(View v) {
        try {
            String userName = user;
            setContentView(R.layout.friendlist);
            Toast.makeText(this, "Bringing up your friends...", Toast.LENGTH_SHORT).show();
            new ViewFriendsActivity(this).execute(userName, "2");
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void getFriendTags(String name) {
        new getPreferencesActivity(this).execute(name, "friend");
    }

    public void defineFriendTags(String tag, String name) {
        try {
            String[] tagSplit = tag.split(",");
            int[] vals = new int[mediaTags.length];
            for (int j = 0; j < tagSplit.length; j++) {
                String split = tagSplit[j];
                if (!split.equals("1") && !split.equals("0")) {
                    continue;
                }
                vals[j] = Integer.parseInt(tagSplit[j]);
            }
            for (int i = 0; i < mediaTags.length; i++) {
                mediaTags[i] = vals[i];
            }
            TextView tags = new TextView(this);
            String tagText = "";
            for (int j = 0; j < mediaTags.length; j++) {
                if (mediaTags[j] == 1) {
                    tagText += tagOpts[j] + " | ";
                }
            }
            tags.setText(tagText);
            tags.setTextSize(16);

            TextView profiletags = (TextView) findViewById(R.id.profileTags);
            profiletags.setText("Preferences: " + tagText);
        }catch (Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void setFriendScreen(String friendsString) {
        try {
            setContentView(R.layout.friendlist);
            TableLayout t = (TableLayout) findViewById(R.id.friendListTable);
            t.removeAllViews();
            t.removeAllViewsInLayout();
            String[] names = friendsString.split("\n");

            for (int i = 0; i < names.length; i++) {
                TableRow row = new TableRow(this);
                TextView text = new TextView(this);
                Button rec = new Button(this);
                Button delete = new Button(this);

                int endIndex = names[i].indexOf('|');
                if (endIndex == -1) break;
                final String name = names[i].substring(0, endIndex);
                String accepted = names[i].substring(endIndex + 1);

                if (accepted.equals("1")) {
                    text.setText(name);
                    text.setTextSize(16);
                } else {
                    String othername = name + "\n (not accepted)";
                    text.setText(othername);
                    text.setTextSize(8);
                }
                text.setPadding(0, 0, 20, 0);
                text.setTextColor(Color.MAGENTA);
                text.setGravity(Gravity.CENTER);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View view) {
                        System.out.println("Checking out " + name + "'s profile!");

                        // SET THE RATING TO 0 FOR RIGHT NOW, NEED TO BE ABLE TO PULL RATING FROM THE USER
                        populateFriendProfile(view, name, "0");
                    }
                });

                //this button currently send the user to the create recommendation screen
                // ideally, it should be updated so the recipient is filled in already
                rec.setText("Recommend");
                rec.setTextSize(16);
                rec.setTag(name);
                rec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeRecScreen(view);
                    }
                });
                {

                }

                delete.setText("Unfriend");
                delete.setTextSize(16);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        acceptFriend(view, name, "0");
                        profileScreen(view);
                    }
                });

                row.addView(text);
                row.addView(rec);
                row.addView(delete);
                t.addView(row);

            }
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void populateFriendProfile(View V, String name, String rating) {
        try {

            setContentView(R.layout.friendprofile);

            TextView profileTitle = (TextView) findViewById(R.id.profileTitle);
            profileTitle.setText(name + "'s Profile");

            TextView tags = new TextView(this);
            getFriendTags(name);
            String tagText = "";
            for (int j = 0; j < mediaTags.length; j++) {
                if (mediaTags[j] == 1) {
                    tagText += tagOpts[j] + " | ";
                }
            }
            tags.setText(tagText);
            tags.setTextSize(16);

            TextView profileRating = (TextView) findViewById(R.id.profileRating);
            profileRating.setText(name + "'s Profile Rating: " + rating);


        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void blockFriend(View v){
        try {
            TextView friendNameTextView = (TextView) findViewById(R.id.profileTitle);
            String friendNameNotSplit = friendNameTextView.getText().toString();
            int index = friendNameNotSplit.indexOf(":");
            // index+2 to go past the :, and the space after it just to get the name with no spaces
            // This will not need to be done if profileTitle does not have the "Profile Name: " before the actual name
            String friendName = friendNameNotSplit.substring(index + 2, friendNameNotSplit.length());
            System.out.println("Blocking " + friendName + "!");

            String username = user.substring(1, user.length() - 1);

            new BlockActivity(this).execute(username, friendName);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void changefavoritesScreen(View V) {
        try {
            setContentView(R.layout.queue);
            new GetFavoritesActivity(getContext()).execute(current_user.userName);
            //develop favorites from database
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void changeNotificationScreen(View v) {
        try {
            setContentView(R.layout.notifications);
            String userName = user.substring(1, user.length() - 1);
            new getNotificationActivity(this).execute(userName);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void featuresScreen(View v){
        try {
            setContentView(R.layout.features);
            populateFeatures(v);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void populateFeatures(View v){
        try {
            // Very simple test to populate the features list
            // Still will need to create way to pull the features from the database
            TableLayout stk = (TableLayout) findViewById(R.id.table_main);
            stk.setBackgroundColor(Color.WHITE);
            stk.removeAllViews();
            stk.removeAllViewsInLayout();

            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.table_main);
            mainLayout.setBackgroundColor(Color.WHITE);
            for (int i = 0; i < 4; i++) {
                // Could be the title of the feature
                TextView test = new TextView(this);
                test.setText("Test New Feature " + i);
                test.setTextSize(24);
                test.setPadding(0, 5, 0, 5);
                stk.addView(test);

            }
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void notificationScreen(String nList) {
        try {
            final String[] contentArray = nList.split("\n");

            TableLayout stk = (TableLayout) findViewById(R.id.notification_table);

            // If there is an error, go back to profile and display message.
            if (stk == null) {
                setContentView(R.layout.profile);
                Toast.makeText(this, "Failed to load notifications.", Toast.LENGTH_SHORT).show();
                TextView profileText = (TextView) findViewById(R.id.myprofile);
                String userName = user.substring(1, user.length() - 1);
                profileText.setText(userName + "'s Profile");
                return;
            }

            stk.removeAllViews();
            stk.removeAllViewsInLayout();
            LinearLayout[] sortArray = new LinearLayout[contentArray.length];
            String[] friendsArray = new String[contentArray.length];
            for (int i = 0; i < contentArray.length; i++) {
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                TextView t1v = new TextView(this);
                final String name = contentArray[i];

                // Don't know why there is a name with an empty string inside the notifications...
                // But this gets rid of the empty notification
                if (name.equals("")) {
                    return;
                }

                t1v.setText(name);
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
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        profileScreen(getCurrentFocus());
                        acceptFriend(view, name, "1");
                    }
                });


                Button ignore = new Button(this);
                ignore.setText("Ignore");
                ignore.setTextSize(24);
                ignore.setTextColor(Color.BLACK);
                ignore.setGravity(Gravity.RIGHT);
                ignore.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                ignore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        changeNotificationScreen(getCurrentFocus());
                        acceptFriend(view, name, "0");

                    }
                });

                row.addView(t1v);
                row.addView(accept);
                row.addView(ignore);
                stk.addView(row);
            }
        /*boolean flag = true;
        String tempfriend = "";
        LinearLayout tempLayout = new LinearLayout(null);

        while (flag) {
            flag = false;
            for (int j = 0; j < contentArray.length-1; j++) {
                if ("friend".compareTo(sortWay)==0) {
                    if (friendsArray[j].compareTo(friendsArray[j+1])==1) {
                        tempfriend = friendsArray[j];
                        tempLayout = sortArray[j];
                        sortArray[j] = sortArray[j+1];
                        sortArray[j+1] = tempLayout;
                        friendsArray[j] = friendsArray[j+1];
                        friendsArray[j+1] = tempfriend;
                        flag = true;
                    }
                }
            }
        }*/

            for (int i = 0; i < contentArray.length; i++) {

                stk.addView(sortArray[i]);
            }
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void deleteQ(String recQ) {
        try {
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
                text.setBackgroundColor(Color.WHITE);
                text.setLayoutParams(new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                ));

                String description = "";
                String[] split = content.split("description:");
                if (split.length > 1) {
                    for (int j = 0; j < split[1].length(); j++) {
                        if (split[1].charAt(j) == '|' || i > 19) {
                            break;
                        }
                        description += split[1].charAt(j);
                    }
                    text.setText(description);
                } else {
                    if (content.length() > 20) {
                        text.setText(content.substring(0, 19));
                    } else {
                        text.setText(content);
                    }
                }
                text.setTextSize(32);
                text.setTextColor(Color.BLACK);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) text.getLayoutParams();
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
                        if (box.isChecked()) {
                            delete.add(content);
                        } else {
                            delete.remove(content);
                        }
                    }
                });

                RelativeLayout.LayoutParams param1 = (RelativeLayout.LayoutParams) box.getLayoutParams();
                param1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                row.addView(text);
                row.addView(box);
                dTable.addView(row);
                TextView border = new TextView(this);
                border.setText("");
                border.setTextSize(1);
                border.setBackgroundColor(Color.BLACK);
                dTable.addView(border, TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
            }
            final Button deleteB = (Button) findViewById(R.id.deleteQScrnBtn);
            deleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // call activity for deleting from Q
                    profileScreen(getCurrentFocus());
                    for (int i = 0; i < delete.size(); i++) {
                        removeRecommendation(view, delete.get(i));
                    }
                    delete.clear();
                }
            });

            Button back = (Button) findViewById(R.id.recQBack2);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setContentView(R.layout.queue);
                    queueScreen(fullContent, 1, "DEFAULT");
                }
            });
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void queueScreen(String recQ, final int favorites, String sortWay) {
        try {
            final String recQ1 = recQ;
            String[] contentArray = recQ.split("\n");
            final String fullContent = recQ;
            TableLayout stk = (TableLayout) findViewById(R.id.table_main);

            if (stk == null) {
                setContentView(R.layout.profile);
                Toast.makeText(this, "Failed to load queue.", Toast.LENGTH_SHORT).show();
                TextView profileText = (TextView) findViewById(R.id.myprofile);
                String userName = user.substring(1, user.length() - 1);
                profileText.setText(userName + "'s Profile");
                return;
            }

            stk.setBackgroundColor(Color.WHITE);
            stk.removeAllViews();

            final Spinner sorter = (Spinner) findViewById(R.id.spinnerSort);
            if (sortWay.compareTo("default") != 0) {
                sorter.setSelection(1);
            }
            sorter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("d1", "true");
                    String selection = sorter.getSelectedItem().toString();
                    if (selection.compareTo("By Friend") == 0) {
                        Log.d("d2", "ye");
                        queueScreen(recQ1, favorites, "friend");
                        return;
                    }
                    if (selection.compareTo("Recent") == 0) {
                        queueScreen(recQ1, favorites, "default");
                        return;
                    }
                    if (selection.compareTo("Highest Rated Friends") == 0) {
                        queueScreen(recQ1, favorites, "rating");
                        return;
                    }
                    sorter.onSaveInstanceState();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            final int f = favorites;
            stk.removeAllViewsInLayout();
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.table_main);
            LinearLayout[] sortArray = new LinearLayout[contentArray.length];
            RelativeLayout[] ratingBarArray = new RelativeLayout[contentArray.length];
            String[] friendsArray = new String[contentArray.length];
            mainLayout.setBackgroundColor(Color.WHITE);
            for (int i = 0; i < contentArray.length; i++) {
                final String content = contentArray[i];
                if (content.indexOf('&') == -1) {
                    return;
                }
                friendsArray[i] = content.split("&")[0];
                int fav = 0;
                String[] info = content.split("&");
                final String recipient = info[1];
                final int rating = Integer.parseInt(info[3]);
                if (favorites == 0) {
                    fav = Integer.parseInt(info[4]);
                }
                Button submit = new Button(this);
                submit.setText("Submit");
                submit.setLayoutParams(new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                ));
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) submit.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                RelativeLayout ratingBar = new RelativeLayout(getContext());

                final TextView viewButton = new TextView(this);
                final ToggleButton toggleButton = new ToggleButton(this);
                if (fav == 1 || favorites == 1) {
                    toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.favorite));
                    toggleButton.setChecked(true);
                } else {
                    toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.unfavorite));
                }
                toggleButton.setText("");
                toggleButton.setTextOn("");
                toggleButton.setTextOff("");
                toggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (toggleButton.isChecked()) {
                            toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.favorite));
                            faves.add(recipient);
                            faves.add(content);

                        } else if (!toggleButton.isChecked() && favorites == 1) {
                            toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.unfavorite));
                            unFave.add(recipient);
                            unFave.add(content);
                        } else if (favorites == 1) {
                            unFave.remove(recipient);
                            unFave.remove(content);
                            toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.favorite));
                        } else {
                            toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.unfavorite));
                            faves.remove(content);
                            faves.remove(recipient);
                        }
                    }
                });


                TableRow tbrow = new TableRow(this);

                final RatingBar bar = new RatingBar(this);
                bar.setMax(5);
                bar.setProgress(rating);
                bar.setStepSize(1);
                bar.setNumStars(5);
                bar.setBackgroundColor(Color.WHITE);
                bar.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

                toggleButton.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                ));

                String description = "";
                String[] split = content.split("description:");
                if (split.length > 1) {
                    for (int j = 0; j < split[1].length(); j++) {
                        if (split[1].charAt(j) == '|') {
                            break;
                        }
                        description += split[1].charAt(j);
                    }
                    viewButton.setText(description);
                } else {
                    if (content.length() > 20) {
                        viewButton.setText(content.substring(0, 19));
                    } else {
                        viewButton.setText(content);
                    }
                }
                viewButton.setTextSize(18);
                viewButton.setTextColor(Color.BLACK);
                viewButton.setGravity(Gravity.CENTER);
                viewButton.setBackgroundColor(Color.WHITE);
                viewButton.setPadding(0, 0, 10, 0);
                viewButton.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        1
                ));

                viewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isFavorite = toggleButton.isChecked();
                        viewContent(content, fullContent, rating, isFavorite, f, sorter.getSelectedItem().toString());
                    }
                });

                tbrow.addView(viewButton, TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                tbrow.addView(toggleButton, TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                tbrow.setGravity(Gravity.CENTER);
                sortArray[i] = tbrow;
                //stk.addView(tbrow);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = current_user.userName;
                        String rating = "" + bar.getRating();
                        String sender = content.split("&")[0];
                        String dbcontent = content.split("&")[2];
                        new RateContentActivity(getContext()).execute(name, sender, dbcontent, rating);
                    }
                });
                TextView border = new TextView(this);
                border.setText("-");
                border.setTextSize(1);
                border.setTextColor(Color.BLACK);
                border.setBackgroundColor(Color.BLACK);

                ratingBar.addView(bar);
                ratingBar.addView(submit);
                //mainLayout.addView(ratingBar);
                ratingBarArray[i] = ratingBar;

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
                    if (f == 1) {
                        for (int i = 0; i < unFave.size(); i += 2) {
                            new UnfavoriteActivity(getContext(), current_user).execute(current_user.userName, unFave.get(i), unFave.get(i + 1));
                        }
                    } else {
                        for (int i = 0; i < faves.size(); i += 2) {
                            new MakeFavoriteActivity(getContext(), current_user).execute(current_user.userName, faves.get(i), faves.get(i + 1));
                        }
                    }
                    faves.clear();
                    unFave.clear();
                    profileScreen(getCurrentFocus());
                }
            });
            Button refresh = (Button) findViewById(R.id.recQRefresh);
            if (favorites == 1) {
                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changefavoritesScreen(getCurrentFocus());
                    }
                });
            } else {
                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeQueueScreen(getCurrentFocus());
                    }
                });
            }

            boolean flag = true;
            String tempfriend = "";
            LinearLayout tempLayout = null;
            RelativeLayout tempRel = null;
            Log.d("dsorr", sortWay);
            while (flag) {
                flag = false;
                for (int j = 0; j < contentArray.length - 1; j++) {
                    if ("friend".compareTo(sortWay) == 0) {
                        Log.d("d2", "sort");
                        Log.d("d4", String.valueOf(friendsArray[j].compareTo(friendsArray[j + 1])));
                        if (friendsArray[j].compareTo(friendsArray[j + 1]) > (1)) {
                            tempfriend = friendsArray[j];
                            tempLayout = sortArray[j];
                            tempRel = ratingBarArray[j];
                            sortArray[j] = sortArray[j + 1];
                            sortArray[j + 1] = tempLayout;
                            friendsArray[j] = friendsArray[j + 1];
                            friendsArray[j + 1] = tempfriend;
                            ratingBarArray[j] = ratingBarArray[j + 1];
                            ratingBarArray[j + 1] = tempRel;
                            flag = true;
                        }
                    }
                }
            }
            for (int i = 0; i < contentArray.length; i++) {
                TextView border = new TextView(this);
                border.setText("-");
                border.setTextSize(1);
                border.setTextColor(Color.BLACK);
                border.setBackgroundColor(Color.BLACK);
                stk.addView(sortArray[i]);
                mainLayout.addView(ratingBarArray[i]);
                mainLayout.addView(border);
                Log.d("d3", friendsArray[i]);
            }
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void viewContent(String details, String fullQ, int rating, boolean isFavorite, int favorites, final String sort) {
        try {
            setContentView(R.layout.viewcontent);
            final String fullContent = new String(fullQ);
            TableLayout viewT = (TableLayout) findViewById(R.id.viewtable);
            String[] names = details.split("&");
            String info = names[3];
            String[] tags = new String[mediaTags.length];
            String[] chunks = names[2].split("\\|");
            if (chunks.length > 3) {
                String[] tagChunks = chunks[3].substring(5).split(",");
                for (int j = 0; j < tags.length; j++) {
                    if (j < tagChunks.length) {
                        tags[j] = tagChunks[j];
                    } else {
                        tags[j] = "0";
                    }
                }

            }
            TableRow senderRow = new TableRow(this);
            TextView senderName = new TextView(this);
            senderName.setText("Sender: " + names[0]);
            senderName.setTextSize(32);
            senderRow.addView(senderName);

            TableRow descriptionRow = new TableRow(this);
            TextView description = new TextView(this);
            String desc = "Description: ";
            if (chunks[0].length() > 0) {
                desc += chunks[0].substring(12, chunks[0].length());
            }
            description.setText(desc);
            description.setTextSize(32);
            descriptionRow.addView(description);

            TableRow type = new TableRow(this);
            TextView typeText = new TextView(this);
            String typ = "Type: ";
            if (chunks.length > 1 && chunks[1].length() > 5) {
                typ += chunks[1].substring(5, chunks[1].length());
            }
            typeText.setText(typ);
            typeText.setTextSize(32);
            type.addView(typeText);

            TableRow linkRow = new TableRow(this);
            TextView linkText = new TextView(this);
            String link = "Link: ";
            if (chunks.length > 1 && chunks[2].length() > 4) {
                link += chunks[2].substring(4, chunks[2].length());
            }
            linkText.setTextSize(32);
            linkText.setText(link);
            linkRow.addView(linkText);

            RelativeLayout rate = new RelativeLayout(this);


            TableRow starBar = new TableRow(this);
            RatingBar bar = new RatingBar(this);
            bar.setNumStars(5);
            bar.setStepSize(1);
            bar.setProgress(rating);
            bar.setIsIndicator(true);
            bar.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            ));
            rate.addView(bar);
            starBar.addView(rate);

            TableRow tagRow = new TableRow(this);
            String text = "Tags: ";
            if (tags.length == 1) {
                text += "none";
            } else {
                for (int i = 0; i < mediaTags.length; i++) {
                    if (Integer.parseInt(tags[i]) == 1) {
                        text += tagOpts[i] + " | ";
                    }
                }
            }
            if (text.length() == 6) {
                text += "None";
            }
            TextView tagTxt = new TextView(this);
            tagTxt.setText(text);
            tagTxt.setTextSize(32);
            tagRow.addView(tagTxt);


            viewT.addView(senderRow);
            viewT.addView(descriptionRow);
            viewT.addView(type);
            viewT.addView(linkRow);
            viewT.addView(starBar);
            viewT.addView(tagRow);

            if (isFavorite || favorites == 1) {
                RelativeLayout fav = new RelativeLayout(this);
                ImageView heartIcon = new ImageView(this);
                heartIcon.setImageResource(R.drawable.favorite);

                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                rp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                fav.addView(heartIcon);
                viewT.addView(fav);

            }

            Button back = (Button) findViewById(R.id.viewBack);
            final int f = favorites;
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setContentView(R.layout.queue);
                    if (sort.compareTo("By Friend") == 0) {
                        queueScreen(fullContent, f, "friend");
                        return;
                    } else {
                        queueScreen(fullContent, f, "default");
                        return;
                    }

                }
            });
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void signupScreen(View v) {
        try {
            if (!loggedIn) {
                setContentView(R.layout.signup);
            }
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void profileScreen(View v) {
        try {
            setContentView(R.layout.profile);
            TextView profileText = (TextView) findViewById(R.id.myprofile);
            String userName = user.substring(1, user.length() - 1);
            profileText.setText(userName + "'s Profile");
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void addFriendScreen (View v) {
        try {
            setContentView(R.layout.newfriend);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void loginScreen(View v) {
        try {
            if (!loggedIn) {
                setContentView(R.layout.login);
            }
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void changePassScreen(View v) {
        try {
            setContentView(R.layout.changepassword);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void changeRecScreen(View v) {
        try {
            setContentView(R.layout.create);
            for (int i = 0; i < mediaTags.length; i++) {
                mediaTags[i] = 0;
            }
            String nameRecommendingTo;
            if (v.getTag() != null) {
                nameRecommendingTo = v.getTag().toString();
            } else {
                nameRecommendingTo = "";
            }

            EditText name = (EditText) findViewById(R.id.destinationUserName);
            name.setText(nameRecommendingTo);

            TableLayout tags = (TableLayout) findViewById(R.id.recScreenTags);
            TableRow tagRow = new TableRow(this);
            for (int i = 0; i < tagOpts.length; i++) {
                if (i % 3 == 0) {
                    tagRow = new TableRow(this);
                    tagRow.setGravity(Gravity.CENTER);
                }
                final CheckBox boxTag = new CheckBox(this);
                final int p = i;
                boxTag.setText(tagOpts[i]);
                boxTag.setTextSize(18);
                boxTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (boxTag.isChecked()) {
                            mediaTags[p] = 1;
                        } else {
                            mediaTags[p] = 0;
                        }
                    }
                });
                tagRow.addView(boxTag, TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                if (i != 0 && (i + 1) % 3 == 0 || i == tagOpts.length - 1) {
                    tags.addView(tagRow, TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                }
            }
            Button done = (Button) findViewById(R.id.btnNewRec);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createRecommendation(getCurrentFocus(), mediaTags);

                }
            });
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }

    }

    public void sendError( String e) {
        new ReportBugActivity(this).execute(e);
    }

    public void getFriendPreferences(String s) {

    }

    public void getPersonalPreferences (String tag) {
        setContentView(R.layout.preferences);
        String [] tagSplit = tag.split(",");
        int [] vals = new int[mediaTags.length];
        for(int j = 0; j < tagSplit.length; j++) {
            String split = tagSplit[j];
            if(!split.equals("1") && !split.equals("0")) {
                continue;
            }
            vals[j] = Integer.parseInt(tagSplit[j]);
        }
        for(int i = 0; i < mediaTags.length; i++) {
            mediaTags[i] = vals[i];
        }

        TableLayout tags = (TableLayout) findViewById(R.id.preferTab);
        TableRow tagRow = new TableRow(this);
        for (int i = 0; i < tagOpts.length; i++) {
            if (i % 3 == 0) {
                tagRow = new TableRow(this);
                tagRow.setGravity(Gravity.CENTER);
            }
            final CheckBox boxTag = new CheckBox(this);
            final int p = i;
            boxTag.setText(tagOpts[i]);
            boxTag.setTextSize(18);
            if(mediaTags[i] == 1) {
                boxTag.setChecked(true);
            }
            boxTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (boxTag.isChecked()) {
                        mediaTags[p] = 1;
                    } else {
                        mediaTags[p] = 0;
                    }
                }
            });
            tagRow.addView(boxTag, TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
            if (i != 0 && (i + 1) % 3 == 0 || i == tagOpts.length - 1) {
                tags.addView(tagRow, TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            }
        }
        Button confirm = (Button) findViewById(R.id.preferConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = "";
                for(int k = 0; k < mediaTags.length; k++) {
                    t += mediaTags[k] + ",";
                }
                new SetPersonalPreferences(getContext()).execute(current_user.userName, t);
            }
        });

        Button cancel = (Button) findViewById(R.id.preferCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mediaTags.length; i++) {
                    mediaTags[i] = 0;
                }
                profileScreen(getCurrentFocus());
            }
        });
    }

    public void setPersonalPreferences(View V) {
        try {
            setContentView(R.layout.preferences);
            new getPreferencesActivity(this).execute(current_user.userName, "user");
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void changeQueueScreen(View v) {
        try {
            setContentView(R.layout.queue);
            String userName = user.substring(1, user.length() - 1);
            new getQueueActivity(this).execute(userName);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public boolean getLoggedIn() {
        return this.loggedIn;
    }

    public void setLoggedIn(boolean loggedIn, String userName) {
        try {
            this.loggedIn = loggedIn;
            current_user = new User(userName, null, null, null, null);
            setContentView(R.layout.profile);
            TextView profileText = (TextView) findViewById(R.id.myprofile);
            String name = user.substring(1, user.length() - 1);
            profileText.setText(name + "'s Profile");

            if (getIntent().getExtras() != null && loggedIn) {
                Log.d("log1", "test");
                View view = findViewById(android.R.id.content);
                EditText temp = (EditText) findViewById(R.id.link);
                String value1 = "";
                if (getIntent().getExtras() != null) {
                    Bundle extras = getIntent().getExtras();
                    value1 = extras.getString(Intent.EXTRA_TEXT);
                }

                changeRecScreen(view);
                //temp.setHint(value1);
            }
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void helpScreen ( View v) {
        try {
            setContentView(R.layout.help);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
    }

    public void logout(View v){
        try {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            this.loggedIn = false;
            current_user = null;
            user = null;
            delete = new ArrayList<>(20);
            faves = new ArrayList<>(20);
            setContentView(R.layout.login);
        }catch(Exception e) {
            new ReportBugActivity(this).execute(e.getMessage());
        }
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
