package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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
                            programSlot.setPresenterId(producer.getId());
                        programSlot.setAssignedBy(MainController.getLoggedInUserName());

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

        if(programSlot.getId() > 0){
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

    public boolean isValidData() {
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
                break;
        }
    }
}
