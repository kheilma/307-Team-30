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
 * Created by Me on 4/1/2017.
 */

public class UnfavoriteActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private User user;

    public UnfavoriteActivity(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    protected String doInBackground(String... arg0) {
        MainActivity mainActivity = (MainActivity)context;
        String userName = arg0[0];
        String recipient = arg0[1];
        String Content = arg0[2].split("&")[2];

        String data;
        String link;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?sender=" + URLEncoder.encode(userName, "UTF-8");
            data += "&recipient=" + URLEncoder.encode(recipient, "UTF-8");
            data += "&Content=" + URLEncoder.encode(Content, "UTF-8");

            link = "http://l00k.000webhostapp.com/unfavorite.php" + data;
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
        MainActivity mainActivity = (MainActivity)context;
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Content unfavorited!", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Failed to unfavorite content.", Toast.LENGTH_SHORT).show();
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
