package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

public class ScheduleListScreen extends AppCompatActivity {

    private ListView mListView;
    private ScheduleAdapter scheduleAdapter;
    private ProgramSlot programSlot = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        ArrayList<ProgramSlot> programSlots = new ArrayList<ProgramSlot>();
        scheduleAdapter = new ScheduleAdapter(this, programSlots);
        mListView = (ListView)findViewById(R.id.scheduleListView);
        mListView.setAdapter(scheduleAdapter);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);
        ControlFactory.getScheduleController().onDisplayScheduleList(this);
    }

    public void showProgramslots(List<ProgramSlot> programSlots) {
        Log.i(ScheduleListScreen.class.getName(), " " + programSlots.size());
        scheduleAdapter.clear();
        scheduleAdapter.addAll(programSlots);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_xml_name, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_item_id:
//                // action
//                break;
//        }
//        return true;
//    }

}
