package look.myapplication;

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

/**
 * Created by Travis Kovacic on 3/2/2017.
 */

public class getNotificationActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private boolean loggedIn;
    private String userName;

    public getNotificationActivity(Context context) {
        this.context = context;
        this.loggedIn = true;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            userName = arg0[0];
            data = "?username=" + URLEncoder.encode(userName, "UTF-8");

            link = "http://l00k.000webhostapp.com/getNotification.php" + data;
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
                    Toast.makeText(context, "Succesfully pulled up queue.", Toast.LENGTH_SHORT).show();
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.notificationScreen(jsonObj.getString("query_message"));
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Failed to pull up recommendations for " + userName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Please seek assistance from your Complaint Department representative.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }


}


