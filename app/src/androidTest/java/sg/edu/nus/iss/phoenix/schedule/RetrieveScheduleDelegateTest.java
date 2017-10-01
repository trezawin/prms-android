package sg.edu.nus.iss.phoenix.schedule;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

import sg.edu.nus.iss.phoenix.schedule.android.controller.ScheduleController;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_SCHEDULE;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveScheduleDelegateTest {
    // Tag for logging
    private static final String TAG = RetrieveScheduleDelegateTest.class.getName();

    @Mock
    ScheduleController scheduleController;

    RetrieveScheduleDelegate retrieveScheduleDelegate = new RetrieveScheduleDelegate(scheduleController);

    @Test
    public void testHandleResponseMsg() {
        String result = "[\n" +
                "    {\n" +
                "        \"dateOfProgram\": \"2017-09-09T12:20:00+08:00\",\n" +
                "        \"dateOfProgramTimestamp\": 0,\n" +
                "        \"duration\": \"1970-01-01T00:12:34+07:30\",\n" +
                "        \"durationTimestamp\": 0,\n" +
                "        \"id\": 1,\n" +
                "        \"programName\": \"pg1\"\n" +
                "    }\n" +
                "]";
        List<ProgramSlot> programSlots = new ArrayList<>();
        retrieveScheduleDelegate.processResponse(result, programSlots);

        // test output
        Assert.assertEquals("Object size is correct!", 1, programSlots.size());
        Assert.assertEquals("Program name is correct!", "pg1", programSlots.get(0).getProgramName());

        // TODO : add more test cases.
    }
}
