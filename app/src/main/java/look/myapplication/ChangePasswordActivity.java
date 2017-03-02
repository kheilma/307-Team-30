package look.myapplication;

/**
 * Created by kyleyo on 2/26/2017.
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

public class ChangePasswordActivity extends AsyncTask<String, Void, String> {

    private Context context;

    public ChangePasswordActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        String currentPass = arg0[0];
        String newPass = arg0[1];


        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        if (currentPass.matches("")) {
            return new String("Please enter your current password");
        }
        if (newPass.matches("")) {
            return new String("password must not be blank");
        }
        if (newPass.length() < 8) {
            return new String("password not long enough");
        }
        if (newPass.equals(newPass.toUpperCase()) || newPass.equals(newPass.toLowerCase())) {
            return new String("password must contain at least one uppercase and lowercase letter");
        }
        try {
            data = "?currpassword=" + URLEncoder.encode(currentPass, "UTF-8");
            data += "&newpassword=" + URLEncoder.encode(newPass, "UTF-8");

            link = "http://l00k.000webhostapp.com/changepassword.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
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
                e.printStackTrace();
                Toast.makeText(context, result , Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
}

