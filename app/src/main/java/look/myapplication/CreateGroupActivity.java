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
 * Created by kyleyo on 4/4/2017.
 */

public class CreateGroupActivity extends AsyncTask<String, Void, String> {
    private Context context;

    public CreateGroupActivity(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String owner = arg0[0];
        String groupName = "'" + arg0[1] + "'";
        String members = "'" + arg0[2] + "'";

        String data;
        String link;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?owner=" + URLEncoder.encode(owner, "UTF-8");
            data += "&GroupName=" + URLEncoder.encode(groupName, "UTF-8");
            data += "&members=" + URLEncoder.encode(members, "UTF-8");

            link = "http://l00k.000webhostapp.com/createGroup.php" + data;
            System.out.println(link);
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

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
