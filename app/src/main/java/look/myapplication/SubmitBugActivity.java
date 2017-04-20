package look.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by kyleyo on 4/4/2017.
 */

public class SubmitBugActivity extends AsyncTask<String, Void, String> {
    private Context context;

    public SubmitBugActivity(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String device =  arg0[0];
        String issue =  arg0[1];
        String version = arg0[2];

        String data;
        String link;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?device=" + URLEncoder.encode(device, "UTF-8");
            data += "&issue=" + URLEncoder.encode(issue, "UTF-8");
            data += "&version=" + URLEncoder.encode(version, "UTF-8");
            link = "http://l00k.000webhostapp.com/submitBug.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (device.matches("")) {
                return new String("Please enter your device model");
            }
            if (issue.matches("")) {
                return new String("Please enter your issue");
            }
            if (version.matches("")) {
                return new String("Please enter your app version");
            }
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    protected void onPostExecute(String result) {
        String jsonStr = result;
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
                e.printStackTrace();
                Toast.makeText(context, result , Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
}
