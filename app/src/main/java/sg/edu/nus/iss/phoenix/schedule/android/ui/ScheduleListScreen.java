package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.core.android.ui.MainScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

import static android.R.attr.filter;
import static android.R.attr.mode;
import static android.R.attr.start;

public class ScheduleListScreen extends AppCompatActivity {

    private ListView mListView;
    private ScheduleAdapter scheduleAdapter;
    private ProgramSlot programSlot = null;
    private FloatingActionButton btnAddSchedule;
    private EditText txtStartDate;
    private EditText txtEndDate;
    private Button btnFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        btnAddSchedule = (FloatingActionButton)findViewById(R.id.btnAddSchedule);
        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getScheduleController().selectCreateScheduleScreen();
            }
        });
        if(!MainController.getLoggedInUserRoles().contains("manager")){
            btnAddSchedule.setVisibility(View.GONE);
        }

        ArrayList<ProgramSlot> programSlots = new ArrayList<ProgramSlot>();
        scheduleAdapter = new ScheduleAdapter(this, programSlots);
        mListView = (ListView)findViewById(R.id.scheduleListView);
        mListView.setAdapter(scheduleAdapter);
        registerForContextMenu(mListView); // register the context menu.

        // Setup FAB to add new controller.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAddSchedule);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlFactory.getScheduleController().selectCreateSchedule();
            }
        });

        txtStartDate = (EditText)findViewById(R.id.txtStartDate);
        txtEndDate = (EditText)findViewById(R.id.txtEndDate);

        SimpleDateFormat filterDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtStartDate.setText(filterDateFormat.format(new Date()));
        txtEndDate.setText(txtStartDate.getText().toString());

        btnFilter = (Button)findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    load();
                }catch (ParseException e){
                    Toast toast = Toast.makeText(MainController.getApp(), "Invalid date format!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private void load() throws ParseException{
        SimpleDateFormat filterDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = filterDateFormat.parse(txtStartDate.getText().toString());
        Date endDate = filterDateFormat.parse(txtEndDate.getText().toString());

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(startDate);
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);
        startDate = calendarStart.getTime();

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(endDate);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
        calendarEnd.set(Calendar.MINUTE, 0);
        calendarEnd.set(Calendar.SECOND, 0);
        calendarEnd.set(Calendar.MILLISECOND, 0);
        endDate = calendarEnd.getTime();

        if(endDate.getTime() >= startDate.getTime()){
            ControlFactory.getScheduleController().onDisplayScheduleList(ScheduleListScreen.this, startDate.getTime(), endDate.getTime());
        }else{
            Toast toast = Toast.makeText(MainController.getApp(), "End date cannot be smaller than Start Date!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    // back button action bar
    public boolean onOptionsItemSelected(MenuItem item){

        Intent intent = new Intent(getApplicationContext(), MainScreen.class);
        MainController.displayScreen(intent);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.scheduleListView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_add, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                handleEdit(item, info);
                return true;
            case R.id.copy:
                handleCopy(item, info);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void handleCopy(MenuItem item, AdapterView.AdapterContextMenuInfo info) {
        int menuItemIndex = item.getItemId();
        ProgramSlot selectedProgramSlot = (ProgramSlot) this.mListView.getItemAtPosition(info.position);
        ControlFactory.getScheduleController().selectCopySchedule(selectedProgramSlot);
    }

    private void handleEdit(MenuItem item, AdapterView.AdapterContextMenuInfo info) {
        int menuItemIndex = item.getItemId();
        ProgramSlot selectedProgramSlot = (ProgramSlot) this.mListView.getItemAtPosition(info.position);
        ControlFactory.getScheduleController().selectUpdateSchedule(selectedProgramSlot);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);
        try {
            load();
        }catch (ParseException e){
            Toast toast = Toast.makeText(MainController.getApp(), "Invalid date format!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void showProgramslots(List<ProgramSlot> programSlots) {
        Log.i(ScheduleListScreen.class.getName(), " " + programSlots.size());
        scheduleAdapter.clear();
        scheduleAdapter.addAll(programSlots);
    }

}
