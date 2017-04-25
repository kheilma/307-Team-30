package look.myapplication;

/**
 * Created by Travis Kovacic on 3/1/2017.
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

public class CreateNotificationActivity extends AsyncTask<String, Void, String> {

    private Context context;

    public CreateNotificationActivity(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0) {
        MainActivity mainActivity = (MainActivity) context;
        String userName = arg0[0];
        String recipient = arg0[1];
        String message = arg0[2];

        String data;
        String link;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?username=" + URLEncoder.encode(userName, "UTF-8");
            data += "&recipient=" + URLEncoder.encode(recipient, "UTF-8");
            data += "&message=" + URLEncoder.encode(message, "UTF-8");

            link = "http://l00k.000webhostapp.com/sendNotification.php" + data;
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

    protected void onPostExecute(String result) {
        String jsonStr = result;
        MainActivity mainActivity = (MainActivity) context;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Notification sent.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Failed to send notification.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Please seek assistance from your Complaint Department representative.", Toast.LENGTH_SHORT).show();
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

