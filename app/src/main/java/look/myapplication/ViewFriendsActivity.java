package look.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Matt on 3/7/2017.
 */

public class ViewFriendsActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private boolean loggedIn;
    private String userName;
    private String choice;

    public ViewFriendsActivity(Context context) {
        this.context = context;
        this.loggedIn = false;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        MainActivity mainActivity = (MainActivity)context;
        userName = arg0[0];
        choice = arg0[1];
        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?username=" + URLEncoder.encode(userName, "UTF-8");

            link = "http://l00k.000webhostapp.com/viewFriends.php" + data;
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
                    if (choice.equals("2")) {
                        mainActivity.setFriendScreen(jsonObj.getString("query_message"));
                    } else {
                        mainActivity.setNewGroupScreen(jsonObj.getString("query_message"));
                    }
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Could not bring up friends list.", Toast.LENGTH_SHORT).show();
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
