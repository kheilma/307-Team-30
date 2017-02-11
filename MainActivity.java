package com.smacnakgmail.look;
/**
 * Created by Me on 8/3/2016.
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private boolean loggedIn;
    private boolean spinnerSet;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        loggedIn = false;
        spinnerSet = false;
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



    public void signupScreen(View v) {
        if (!loggedIn) {
            setContentView(R.layout.signup);
        }
    }



    public void loginScreen(View v) {
        if(!loggedIn) {
            setContentView(R.layout.login);
        }
    }


    public boolean getLoggedIn() {
        return this.loggedIn;
    }

    public void setLoggedIn(boolean loggedIn, String userName) {
        this.loggedIn = loggedIn;
        user = "'" + userName + "'";
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