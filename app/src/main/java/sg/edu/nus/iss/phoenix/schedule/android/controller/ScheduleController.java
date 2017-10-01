package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.content.Intent;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.RetrieveProgramsDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.MaintainProgramScreen;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.ProgramListScreen;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.CreateScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.android.ui.MaintainScheduleScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleListScreen;

/**
 * Created by treza on 9/23/17.
 */
public class ScheduleController {
    private ScheduleListScreen scheduleListScreen;
    private MaintainScheduleScreen maintainScheduleScreen;

    public void startUseCase() {
        Intent intent = new Intent(MainController.getApp(), ScheduleListScreen.class);
        MainController.displayScreen(intent);
    }

    public void selectCreateScheduleScreen(){
        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        intent.putExtra("program_slot", new ProgramSlot());
        MainController.displayScreen(intent);
    }

    public void createSchedule(ProgramSlot programSlot){
        new CreateScheduleDelegate(this).execute(programSlot);
    }

    public void updateSchedule(ProgramSlot programSlot){

    }

    public void onDisplayScheduleList(ScheduleListScreen scheduleListScreen) {
        this.scheduleListScreen = scheduleListScreen;
        new RetrieveScheduleDelegate(this).execute("all");
    }

    public void retrievedSchedules(List<ProgramSlot> programSlots) {
        scheduleListScreen.showProgramslots(programSlots);
    }

    public void scheduleCreated(Boolean status){
        this.startUseCase();
    }

    public void selectCreateSchedule() {
        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        intent.putExtra("programSlot", new ProgramSlot());
        MainController.displayScreen(intent);
    }

    public void selectUpdateSchedule(ProgramSlot programSlot) {
        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        intent.putExtra("programSlot", programSlot);
        MainController.displayScreen(intent);
    }

    public void selectCopySchedule(ProgramSlot programSlot) {
        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        programSlot.setId(0);
        intent.putExtra("programSlot", programSlot);
        MainController.displayScreen(intent);
    }
}
