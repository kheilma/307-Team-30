package look.myapplication;

/**
 * Created by kyleyo on 2/18/2017.
 */

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

public class LoginActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private boolean loggedIn;
    private String userName;
    private String hash;

    public LoginActivity(Context context) {
        this.context = context;
        this.loggedIn = false;
    }

    protected void onPreExecute() {

    }

    public static String byteArrayToHexString(byte[] b){
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++){
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] computeHash(String x)
            throws Exception
    {
        java.security.MessageDigest d =null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return  d.digest();
    }

    @Override
    protected String doInBackground(String... arg0) {
        MainActivity mainActivity = (MainActivity)context;
        userName = arg0[0];
        String passWord = arg0[1];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        if (userName.matches("")) {
            return new String("username can not be blank");
        }
        if (passWord.matches("")) {
            return new String("password field can not be blank");
        }

        try {
            hash = byteArrayToHexString(computeHash(passWord));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            data = "?username=" + URLEncoder.encode(userName, "UTF-8");
            data += "&password=" + URLEncoder.encode(hash, "UTF-8");

            link = "http://l00k.000webhostapp.com/Login.php" + data;
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
                    Toast.makeText(context, "Login successfull.", Toast.LENGTH_SHORT).show();
                    mainActivity.setLoggedIn(true, userName);
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Login failed.", Toast.LENGTH_SHORT).show();
                    mainActivity.setContentView(R.layout.login);
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
