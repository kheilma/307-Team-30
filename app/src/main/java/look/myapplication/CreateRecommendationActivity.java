package look.myapplication;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Matt on 2/21/2017.
 */

public class CreateRecommendationActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private User user;

    public CreateRecommendationActivity(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String userName = arg0[0]
        String recipient = arg0[1];
        String description = arg0[2];
        String type = arg0[3];
        String recLink = arg0[4];
        
        String data;
        String link;
        
        try {
            data = "?username=" + URLEncoder.encode(userName, "UTF-8");
            data = "&recipient=" + URLEncoder.encode(recipient, "UTF-8");
            data = "&description=" + URLEncoder.encode(description, "UTF-8");
            data = "&type=" + URLEncoder.encode(type, "UTF-8");
            data = "&recLink=" + URLEncoder.encode(recLink, "UTF-8");
            
            link = "http://l00k.000webhostapp.com/sendRecommendation.php" + data;
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
                    Toast.makeText(context, "Recommendation sent.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Failed to send recommendation.", Toast.LENGTH_SHORT).show();
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
