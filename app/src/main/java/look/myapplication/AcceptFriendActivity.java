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
public class AcceptFriendActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private User user;
    private String choice;
    private String friendName;

    public AcceptFriendActivity(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String user = arg0[0];
        String friend = arg0[1];
        String answer = arg0[2];
        choice = answer;
        friendName = friend;

        String data;
        String link;
        BufferedReader bufferedReader;
        String result;
        MainActivity mainActivity = (MainActivity)context;

        try {
            data = "?user=" + URLEncoder.encode(user, "UTF-8");
            data += "&friend=" + URLEncoder.encode(friend, "UTF-8");
            data += "&answer=" + URLEncoder.encode(answer, "UTF-8");

            link = "http://l00k.000webhostapp.com/AcceptFriendRequest.php" + data;

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
        MainActivity main = (MainActivity) context;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    if(choice.equals("1")) {
                        Toast.makeText(context, "Friend invite accepted.", Toast.LENGTH_SHORT).show();
                    } else if(choice.equals("0")){
                        Toast.makeText(context, friendName + " removed as a friend.", Toast.LENGTH_SHORT).show();
                    }

                } else if (query_result.equals("FAILURE")) {
                    if(choice.equals("1")) {
                        Toast.makeText(context, "Failed to accept friend invite.", Toast.LENGTH_SHORT).show();
                    } else if(choice.equals("0")){
                        Toast.makeText(context, "Failed to remove " + friendName + " as a friend.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Please seek assistance from your Complaint Department representative.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                main.sendError(e.getMessage());
                e.printStackTrace();
                Toast.makeText(context, result , Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
}
