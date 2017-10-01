package sg.edu.nus.iss.phoenix.schedule.android.delegate;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ProgramController;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_RADIO_PROGRAM;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE;

public class RetrieveScheduleDelegate extends AsyncTask<String, Void, String> {
    // Tag for logging
    private static final String TAG = RetrieveScheduleDelegate.class.getName();

    private ScheduleController scheduleController;

    public RetrieveScheduleDelegate(ScheduleController scheduleController) {
        this.scheduleController = scheduleController;
    }

    @Override
    protected String doInBackground(String... params) {
        Uri builtUri1 = Uri.parse(PRMS_BASE_URL_SCHEDULE).buildUpon().build();
        Uri builtUri = Uri.withAppendedPath(builtUri1, params[0]).buildUpon().build();
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
        List<ProgramSlot> programSlots = new ArrayList<>();
        processResponse(result, programSlots);
        scheduleController.retrievedSchedules(programSlots);
    }

    public void processResponse(String result, List<ProgramSlot> programSlots) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"); // eg; 1970-01-01T00:12:34+07:30

        if (result != null && !result.equals("")) {
            try {
                JSONArray rpArray = new JSONArray(result);

                for (int i = 0; i < rpArray.length(); i++) {
                    JSONObject rpJson = rpArray.getJSONObject(i);

                    String duration = rpJson.getString("duration");
                    String dateOfProgram = rpJson.getString("dateOfProgram");

                    ProgramSlot ps = new ProgramSlot();
                    ps.setProgramName(rpJson.getString("programName"));
                    ps.setDuration(sdf.parse(duration));
                    ps.setDateOfProgram(sdf.parse(dateOfProgram));

                    ps.setPresenterId(rpJson.getString("presenterId"));
                    ps.setPresenterName(rpJson.getString("presenterName"));
                    ps.setProducerId(rpJson.getString("producerId"));
                    ps.setProducerName(rpJson.getString("producerName"));

                    //TODO : to remove comment
//                    ps.setAssignedBy((rpJson.getString("assignedBy") == null? "" : rpJson.getString("assignedBy")));

                    programSlots.add(ps);

                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            } catch (Exception ex) {
                Log.v(TAG, ex.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }
    }
}
