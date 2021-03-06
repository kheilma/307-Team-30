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
 * Created by Me on 4/24/2017.
 */

public class getPreferencesActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private String mode = "";
    private String name = "";

    public getPreferencesActivity(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0) {
        MainActivity mainActivity = (MainActivity)context;
        String userName = "'" + arg0[0] + "'";
        String preferences = arg0[1];
        this.mode = preferences;
        this.name = arg0[0];

        String data;
        String link;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?username=" + URLEncoder.encode(userName, "UTF-8");

            link = "http://l00k.000webhostapp.com/getPreferences.php" + data;
            System.out.println(link);
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
        MainActivity mainActivity = (MainActivity)context;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Preferences retrieved.", Toast.LENGTH_SHORT).show();
                    if(mode.equals("user")) {
                        mainActivity.getPersonalPreferences(jsonObj.getString("query_message"));
                    }
                    else if(mode.equals("friend")) {
                        mainActivity.defineFriendTags(jsonObj.getString("query_message"),name);
                    }
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Failed to retrieve preferences.", Toast.LENGTH_SHORT).show();
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
