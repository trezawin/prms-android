package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

public class MaintainScheduleScreen extends AppCompatActivity {

    private Button btnSave;
    private Button btnDelete;
    private EditText txtProgram;
    private EditText txtStartDatetime;
    private EditText txtDuration;

    private ProgramSlot programSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_schedule_screen);

        programSlot = (ProgramSlot)this.getIntent().getSerializableExtra("programSlot");

        btnSave = (Button)findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        txtProgram = (EditText) findViewById(R.id.txtProgram);
        txtStartDatetime = (EditText)findViewById(R.id.txtStartDatetime);
        txtDuration = (EditText)findViewById(R.id.txtDuration);

        if(programSlot == null) {
            programSlot = new ProgramSlot();
        }
        if(programSlot.getId() > 0){
            btnDelete.setVisibility(View.VISIBLE);
        }
        if(programSlot.getId() == 0 && programSlot.getProgramName()!= null &&
                !programSlot.getProgramName().equals(""))
            btnSave.setText("Copy");

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                try {
                    if(MaintainScheduleScreen.this.isValidData()){
                        SimpleDateFormat startDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                        programSlot.setProgramName(txtProgram.getText().toString());
                        programSlot.setAssignedBy(MainController.getLoggedInUserName());
                        programSlot.setDateOfProgram(startDateTimeFormat.parse(txtStartDatetime.getText().toString()));

                        Calendar durationCalendar = Calendar.getInstance();
                        durationCalendar.setTime(new Date());
                        durationCalendar.set(Calendar.HOUR_OF_DAY, 0);
                        durationCalendar.set(Calendar.SECOND, 0);
                        durationCalendar.set(Calendar.MILLISECOND, 0);
                        durationCalendar.add(Calendar.MINUTE, Integer.parseInt(txtDuration.getText().toString()));
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
    }

    public boolean isValidData(){
        if(txtProgram.getText().toString().equals("")){
            return false;
        }
        if(txtStartDatetime.getText().toString().equals("")){
            return false;
        }
        if(txtDuration.getText().toString().equals("")){
            return false;
        }
        return true;
    }
}
