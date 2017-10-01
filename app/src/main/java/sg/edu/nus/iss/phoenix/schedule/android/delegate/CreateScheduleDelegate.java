package sg.edu.nus.iss.phoenix.schedule.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE_CREATE;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER;

/**
 * Created by kmb on 24/9/17.
 */

public class CreateScheduleDelegate extends AsyncTask<ProgramSlot, Void, Boolean> {
    private static final String TAG = CreateScheduleDelegate.class.toString();

    private ScheduleController scheduleController;

    public CreateScheduleDelegate(ScheduleController scheduleController){
        this.scheduleController = scheduleController;
    }

    @Override
    protected Boolean doInBackground(ProgramSlot... params) {
        Uri builtUri = Uri.parse(PRMS_BASE_URL_SCHEDULE_CREATE).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
        }

        JSONObject programSlotJson = new JSONObject();
        try {
            programSlotJson.put("name", params[0].getProgramName());
            programSlotJson.put("durationTimestamp", params[0].getDuration().getTime());
            programSlotJson.put("dateOfProgramTimestamp", params[0].getDuration().getTime());
            programSlotJson.put("assignedBy", params[0].getAssignedBy());

//            programSlotJson.put("presenterId", params[0].getPresenterId());
//            programSlotJson.put("presenterName", params[0].getPresenterName());
//            programSlotJson.put("producerId", params[0].getProducerId());
//            programSlotJson.put("producerName", params[0].getProducerName());
//            programSlotJson.put("radioProgramName", params[0].getRadioProgramName());

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
            dos.writeUTF(programSlotJson.toString());
            dos.write(256);
            Log.v(TAG, "Http POST response " + urlConnection.getResponseCode());
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }

        return new Boolean(success);
    }

    @Override
    protected void onPostExecute(Boolean b) {
        super.onPostExecute(b);
        this.scheduleController.scheduleCreated(b);
    }
}