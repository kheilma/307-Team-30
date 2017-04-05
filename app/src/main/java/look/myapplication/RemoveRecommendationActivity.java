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
 * Created by Me on 2/27/2017.
 */
public class RemoveRecommendationActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private User user;

    public RemoveRecommendationActivity(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String userName = arg0[0];
        String recipient = arg0[1];
        String content = arg0[2];

        String data;
        String link;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?sender=" + URLEncoder.encode(userName, "UTF-8");
            data += "&recipient=" + URLEncoder.encode(recipient, "UTF-8");
            data += "&content=" + URLEncoder.encode(content, "UTF-8");

            link = "http://l00k.000webhostapp.com/removeRecommendation.php" + data;
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
                    Toast.makeText(context, "Recommendation deleted.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Failed to delete recommendation.", Toast.LENGTH_SHORT).show();
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
