package look.myapplication;

/**
 * Created by kyleyo on 2/26/2017.
 */
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
import java.util.regex.Pattern;

public class ChangePasswordActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private String hash;

    public ChangePasswordActivity(Context context) {
        this.context = context;
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
        String user = arg0[0];
        String currentPass = arg0[1];
        String newPass = arg0[2];


        String link;
        String data;
        BufferedReader bufferedReader;
        String result;
        MainActivity mainActivity= (MainActivity) context;

        if (currentPass.matches("")) {
            return new String("Please enter your current password");
        }
        if (newPass.matches("")) {
            return new String("password must not be blank");
        }
        if (newPass.length() < 8) {
            return new String("password not long enough");
        }
        if (newPass.equals(newPass.toUpperCase()) || newPass.equals(newPass.toLowerCase())) {
            return new String("password must contain at least one uppercase and lowercase letter");
        }

        try {
            hash = byteArrayToHexString(computeHash(newPass));
        } catch (Exception e) {
            mainActivity.sendError(e.getMessage());
            e.printStackTrace();
        }

        try {
            data = "?user=" + URLEncoder.encode(user, "UTF-8");
            data += "&newpassword=" + URLEncoder.encode(hash, "UTF-8");

            link = "http://l00k.000webhostapp.com/changepassword.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            System.out.println(link);
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
        String jsonStr = result;
        MainActivity mainActivity = (MainActivity) context;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Data inserted successfully. Signup successful.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
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

