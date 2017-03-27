package look.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TableLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Matt on 3/7/2017.
 */

public class ViewFriendsActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private boolean loggedIn;
    private String userName;

    public ViewFriendsActivity(Context context) {
        this.context = context;
        this.loggedIn = false;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        userName = arg0[0];

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
                    MainActivity mainActivity = (MainActivity)context;
                    mainActivity.setFriendScreen(jsonObj.getString("query_message"));
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Could not bring up friends list.", Toast.LENGTH_SHORT).show();
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
