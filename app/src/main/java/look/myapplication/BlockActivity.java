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
 * Created by Me on 4/7/2017.
 */

public class BlockActivity extends AsyncTask<String, Void, String> {
    private Context context;
    private boolean loggedIn;
    private String userName;

    public BlockActivity(Context context) {
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
        MainActivity main = (MainActivity) context;

        try {
            String blocker = arg0[0];
            String blocked = arg0[1];

            data = "?blocker=" + URLEncoder.encode(blocker, "UTF-8");
            data += "?blocked=" + URLEncoder.encode(blocked, "UTF-8");

            link = "http://l00k.000webhostapp.com/block.php" + data;
            URL url = new URL(link);
            System.out.println(link);
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
        MainActivity main = (MainActivity) context;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Succesfully blocked.", Toast.LENGTH_SHORT).show();

                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Failed to block", Toast.LENGTH_SHORT).show();
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
