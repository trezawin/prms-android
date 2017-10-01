package sg.edu.nus.iss.phoenix.user.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.user.android.controller.UserController;
import sg.edu.nus.iss.phoenix.user.android.entity.Role;
import sg.edu.nus.iss.phoenix.user.android.entity.User;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER_ROOT;

public class CreateUserDelegate extends AsyncTask<User, Void, Boolean> {
    // Tag for logging
    private static final String TAG = CreateUserDelegate.class.getName();

    private final UserController userController;

    public CreateUserDelegate(UserController userController) {
        this.userController = userController;
    }

    @Override
    protected Boolean doInBackground(User... params) {
        Uri builtUri = Uri.parse(PRMS_BASE_URL_USER_ROOT).buildUpon().build();
        //builtUri = Uri.withAppendedPath(builtUri,"update").buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        JSONObject json = new JSONObject();
        try {
            json.put("id", params[0].getId());
            json.put("name", params[0].getName());
            json.put("password", params[0].getPassword());
            //json.put("user", new JSONArray(new Object[] { "name", "Lamis"} ));
            List<JSONObject> list = new ArrayList<JSONObject>();
            for(Role role : params[ 0].getRoles()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("role",role.getRole());
                jsonObject.put("accessPrivilege",role.getAccessPrivilege());
                list.add(jsonObject);
            }
            json.put("roles", new JSONArray(list));
        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }

        String jsonResp = null;
        HttpURLConnection urlConnection = null;
        DataOutputStream dos = null;
        boolean success = false;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.writeUTF(json.toString());
            dos.write(256);
            Log.v(TAG, "Http put response " + urlConnection.getResponseCode());
            success = true;
        } catch (IOException exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
            if (dos != null) {
                try {
                    dos.flush();
                    dos.close();
                } catch (IOException exception) {
                    Log.v(TAG, exception.getMessage());
                }
            }
            if (urlConnection != null) urlConnection.disconnect();
        }
        return new Boolean(success);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        userController.userCreated(result.booleanValue());
    }
}
