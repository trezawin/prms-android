package sg.edu.nus.iss.phoenix.schedule.android.controller;

import sg.edu.nus.iss.phoenix.schedule.android.delegate.CreateScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

/**
 * Created by kmb on 24/9/17.
 */

public class ScheduleController {
    public void selectCreateSchedule(ProgramSlot programSlot){
        new CreateScheduleDelegate(this).execute();
    }

    public void startUseCase(){

    }

    public void scheduleCreated(Boolean status){
        this.startUseCase();
    }
}
