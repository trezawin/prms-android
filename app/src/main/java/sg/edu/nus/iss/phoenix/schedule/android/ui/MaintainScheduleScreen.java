package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

public class MaintainScheduleScreen extends AppCompatActivity {

    private Button btnSave;
    private EditText txtProgram;
    private EditText txtStartDatetime;
    private EditText txtDuration;

    private ProgramSlot programSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_schedule_screen);

        programSlot = (ProgramSlot)this.getIntent().getSerializableExtra("program_slot");

        btnSave = (Button)findViewById(R.id.btnSave);
        txtProgram = (EditText) findViewById(R.id.txtProgram);
        txtStartDatetime = (EditText)findViewById(R.id.txtStartDatetime);
        txtDuration = (EditText)findViewById(R.id.txtDuration);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programSlot.setProgramName(txtProgram.getText().toString());


                if(programSlot.getId() == 0)
                    ControlFactory.getScheduleController().selectCreateSchedule(programSlot);
                else
                    ControlFactory.getScheduleController().selectUpdateSchedule(programSlot);
            }
        });
    }


}
