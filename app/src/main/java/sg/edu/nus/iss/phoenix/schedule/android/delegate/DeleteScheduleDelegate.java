package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_DELETE;

/**
 * Created by kmb on 1/10/17.
 */

public class DeleteScheduleDelegate extends AsyncTask<Integer, Void, Boolean> {
    private static final String TAG = DeleteScheduleDelegate.class.toString();

    private ScheduleController scheduleController;

    public DeleteScheduleDelegate(ScheduleController scheduleController){
        this.scheduleController = scheduleController;
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        Uri builtUri1 = Uri.parse(PRMS_BASE_URL_SCHEDULE_DELETE + "/" + params[0]).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri1.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
        }

        String jsonResp = null;
        boolean success =false;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) jsonResp = scanner.next();

            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }

        return success;
    }

    @Override
    protected void onPostExecute(Boolean b) {
        super.onPostExecute(b);
        this.scheduleController.scheduleCreated(b);
    }
}
