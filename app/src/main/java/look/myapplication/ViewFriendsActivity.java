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

public class ViewFriendsActivity extends AsyncTask {

    Context context;

    ViewFriendsActivity(Context context) {
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        User user = (User) objects[0];
        ArrayList<User> friends = user.getFriendList();
        String[] names = new String[user.getFriendCount()];
        for( int i = 0; i < user.getFriendCount(); i++) {
            names[i] = friends.get(i).getUserName();

        }


        return names;
    }
}
