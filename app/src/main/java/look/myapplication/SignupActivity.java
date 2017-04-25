package look.myapplication;

/**
 * Created by kyleyo on 2/18/2017.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Pattern;

public class SignupActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private String user;
    private String hash;

    public SignupActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    public static String byteArrayToHexString(byte[] b){
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++){
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] computeHash(String x)
            throws Exception
    {
        java.security.MessageDigest d =null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return  d.digest();
    }

    @Override
    protected String doInBackground(String... arg0) {
        MainActivity mainActivity = (MainActivity)context;
        String fullName = arg0[0];
        user = arg0[1];
        String passWord = arg0[2];
        String phoneNumber = arg0[3];
        String emailAddress = arg0[4];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        if (fullName.matches("")) {
            return new String("name can not be blank");
        }
        if (!(user.matches("[a-zA-Z0-9]+"))) {
            return new String("Invalid username");
        }
        if (!emailAddress.matches("[a-z0-9]+[@][a-z]+[.][a-z]+")) {
            return new String("not a valid email address");
        }
        if (!phoneNumber.matches("[0-9]{10}")) {
            return new String("not a valid phone number");
        }
        if (passWord.length() < 8) {
            return new String("password not length needs to be at least 8 characters");
        }
        if (passWord.equals(passWord.toUpperCase()) || passWord.equals(passWord.toLowerCase())) {
            return new String("password must contain at least one uppercase and lowercase letter");
        }

        try {
            hash = byteArrayToHexString(computeHash(passWord));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            data = "?fullname=" + URLEncoder.encode(fullName, "UTF-8");
            data += "&username=" + URLEncoder.encode(user, "UTF-8");
            data += "&password=" + URLEncoder.encode(hash, "UTF-8");
            data += "&phonenumber=" + URLEncoder.encode(phoneNumber, "UTF-8");
            data += "&emailaddress=" + URLEncoder.encode(emailAddress, "UTF-8");

            link = "http://l00k.000webhostapp.com/signup.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            mainActivity.sendError(e.getMessage());
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        MainActivity mainActivity = (MainActivity)context;
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Data inserted successfully. Signup successful.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                mainActivity.sendError(e.getMessage());
                e.printStackTrace();
                Toast.makeText(context, result , Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
}
