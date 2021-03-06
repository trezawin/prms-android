package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.core.android.ui.MainScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.user.android.entity.User;

public class MaintainScheduleScreen extends AppCompatActivity {

    private Button btnSave;
    private Button btnDelete;
    private EditText txtProgram;
    private EditText txtStartDatetime;
    private EditText txtDuration;
    private EditText txtReviewSelectPresenterProducer;
    private EditText txtReviewSelectProducer;

    private ProgramSlot programSlot;
    private User presenter;
    private User producer;
    private RadioProgram radioProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_schedule_screen);

        programSlot = (ProgramSlot)this.getIntent().getSerializableExtra("programSlot");

        btnSave = (Button)findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        txtReviewSelectPresenterProducer = (EditText) findViewById(R.id.txtReviewSelectPresenterProducer);
        txtReviewSelectProducer = (EditText)findViewById(R.id.txtReviewSelectProducer);

        txtProgram = (EditText) findViewById(R.id.txtProgram);
        txtStartDatetime = (EditText)findViewById(R.id.txtStartDatetime);
        txtDuration = (EditText)findViewById(R.id.txtDuration);

        // set back button action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if(programSlot == null) {
            programSlot = new ProgramSlot();
        }
        if(programSlot.getId() > 0) {
            btnDelete.setVisibility(View.VISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(MaintainScheduleScreen.this.isValidData()){
                        SimpleDateFormat startDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                        programSlot.setProgramName(txtProgram.getText().toString());
                        programSlot.setAssignedBy(MainController.getLoggedInUserName());
                        programSlot.setDateOfProgram(startDateTimeFormat.parse(txtStartDatetime.getText().toString()));
                        if(producer != null)
                            programSlot.setProducerId(producer.getId());
                        if(presenter != null)
                            programSlot.setPresenterId(presenter.getId());
                        programSlot.setAssignedBy(MainController.getLoggedInUserName());

                        Calendar durationCalendar = Calendar.getInstance();
                        durationCalendar.setTime(new Date());
                        durationCalendar.set(Calendar.HOUR_OF_DAY, 0);
                        durationCalendar.set(Calendar.SECOND, 0);
                        durationCalendar.set(Calendar.MILLISECOND, 0);
                        durationCalendar.set(Calendar.MINUTE, Integer.parseInt(txtDuration.getText().toString()));
                        programSlot.setDuration(durationCalendar.getTime());

                        if(programSlot.getId() == 0)
                            ControlFactory.getScheduleController().createSchedule(MaintainScheduleScreen.this, programSlot);
                        else
                            ControlFactory.getScheduleController().updateSchedule(MaintainScheduleScreen.this, programSlot);
                    }
                }catch (ParseException e){

                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControlFactory.getScheduleController().deleteSchedule(MaintainScheduleScreen.this, programSlot.getId());
            }
        });

        txtReviewSelectPresenterProducer.setInputType(InputType.TYPE_NULL);
        txtReviewSelectPresenterProducer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ControlFactory.getScheduleController().selectReviewSelectPresenterProducer(MaintainScheduleScreen.this, "presenter");
                }
            }
        });

        txtReviewSelectProducer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    ControlFactory.getScheduleController().selectReviewSelectPresenterProducer(MaintainScheduleScreen.this, "producer");
                }
            }
        });

        txtProgram.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ControlFactory.getProgramController().startUseCase(MaintainScheduleScreen.this);
                }
            }
        });

        if(programSlot.getId() > 0 || (programSlot.getProgramName() != null &&
                !programSlot.getProgramName().equals(""))){
            SimpleDateFormat programDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Calendar durationCalendar = Calendar.getInstance();
            durationCalendar.setTime(programSlot.getDuration());

            txtProgram.setText(programSlot.getProgramName());
            txtReviewSelectPresenterProducer.setText(programSlot.getPresenterName());
            txtReviewSelectProducer.setText(programSlot.getProducerName());
            txtStartDatetime.setText(programDateFormat.format(programSlot.getDateOfProgram()));
            txtDuration.setText(String.valueOf(durationCalendar.get(Calendar.MINUTE)));

            presenter = new User();
            presenter.setId(programSlot.getPresenterId());

            producer = new User();
            producer.setId(programSlot.getProducerId());
        }
    }

    // back button action bar
    public boolean onOptionsItemSelected(MenuItem item){

        Intent intent = new Intent(getApplicationContext(), ScheduleListScreen.class);
        MainController.displayScreen(intent);
        return true;
    }

    public boolean isValidData() {
        if(txtProgram.getText().toString().equals("")){
            Toast toast = Toast.makeText(MainController.getApp(), "Program name cannot be empty!", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        if(txtStartDatetime.getText().toString().equals("")){
            Toast toast = Toast.makeText(MainController.getApp(), "Program date cannot be empty!", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }else{
            SimpleDateFormat startDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                startDateTimeFormat.parse(txtStartDatetime.getText().toString());
            }catch (ParseException e){
                Toast toast = Toast.makeText(MainController.getApp(), "Invalid program date!", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        if(txtDuration.getText().toString().equals("")){
            Toast toast = Toast.makeText(MainController.getApp(), "Duration cannot be empty!", Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                presenter = (User)data.getSerializableExtra("user");
                txtReviewSelectPresenterProducer.setText(presenter.getName());
                break;
            case 2:
                producer = (User)data.getSerializableExtra("user");
                txtReviewSelectProducer.setText(producer.getName());
                break;
            case 3:
                radioProgram = (RadioProgram)data.getSerializableExtra("radio_program");
                txtProgram.setText(radioProgram.getRadioProgramName());

                SimpleDateFormat durationFormat = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date duration = durationFormat.parse(radioProgram.getRadioProgramDuration());
                    Calendar durationCalendar = Calendar.getInstance();
                    durationCalendar.setTime(duration);
                    txtDuration.setText(String.valueOf(durationCalendar.get(Calendar.MINUTE)));
                }catch(ParseException e){

                }
                break;
        }
    }
}
