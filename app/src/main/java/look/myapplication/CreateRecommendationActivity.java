package com.meads30gmail.look;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Matt on 2/21/2017.
 */

public class CreateRecommendationActivity extends AsyncTask<String, Void, String> {

    private Context context;
    private User user;

    CreateRecommendationActivity(Context context, User user) {
        this.context = context;
        this.user = user;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String recipient = arg0[0];
        String description = arg0[1];
        String type = arg0[2];
        String recLink = arg0[3];

        user.newRecommendation(recLink, type, description, recipient);



        return null;
    }
}
