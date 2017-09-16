package sg.edu.nus.iss.phoenix.user.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.user.android.controller.UserController;
import sg.edu.nus.iss.phoenix.user.android.entity.Role;
import sg.edu.nus.iss.phoenix.user.android.entity.User;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_RADIO_PROGRAM;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;

/**
 * Created by kmb on 16/9/17.
 */

public class RetrieveUsersDelegate extends AsyncTask<String, Void, String> {
    private static final String TAG = RetrieveUsersDelegate.class.toString();
    private UserController userController;

    public RetrieveUsersDelegate(UserController userController){
        this.userController = userController;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri builtUri = Uri.parse( PRMS_BASE_URL_USER).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return e.getMessage();
        }

        String jsonResp = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) jsonResp = scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }

        return jsonResp;
    }

    @Override
    protected void onPostExecute(String result) {
        List<User> userList = new ArrayList<User>();

        if (result != null && !result.equals("")) {
            try {
                JSONArray rpArray = new JSONArray(result);

                for (int i = 0; i < rpArray.length(); i++) {
                    JSONObject userJson = rpArray.getJSONObject(i);
                    String id = userJson.getString("id");
                    String name = userJson.getString("name");
                    String password = userJson.getString("password");
                    JSONArray rolesJsonArray =  userJson.getJSONArray("roles");
                    ArrayList<Role> roleList = new ArrayList<Role>();
                    for(int ii = 0; ii < rolesJsonArray.length(); ii++){
                        roleList.add(new Role(rolesJsonArray.getJSONObject(ii).getString("role")));
                    }
                    userList.add(new User(id, name, password, roleList));
                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }
        userController.userRetrieved(userList);
    }
}
