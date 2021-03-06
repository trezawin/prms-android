package sg.edu.nus.iss.phoenix.schedule.android.controller;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.CreateScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.DeleteScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.android.delegate.UpdateScheduleDelegate;
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

    public void selectCreateScheduleScreen() {
        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        intent.putExtra("program_slot", new ProgramSlot());
        MainController.displayScreen(intent);
    }

    public void createSchedule(MaintainScheduleScreen maintainScheduleScreen, ProgramSlot programSlot) {
        // TODO update DM
        new CreateScheduleDelegate(this).execute(programSlot);
        this.maintainScheduleScreen = maintainScheduleScreen;
    }

    public void scheduleCreated(Boolean status){
        if(status) {
            this.startUseCase();
            Toast toast = Toast.makeText(MainController.getApp(), "Saved successfully!", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(MainController.getApp(), "Saving failed. Another program slot is assigned at this time already!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void updateSchedule(MaintainScheduleScreen maintainScheduleScreen, ProgramSlot programSlot){
        // TODO update DM
        new UpdateScheduleDelegate(this).execute(programSlot);
        this.maintainScheduleScreen = maintainScheduleScreen;
    }

    public void scheduleUpdated(Boolean status){
        if(status) {
            this.startUseCase();
            Toast toast = Toast.makeText(MainController.getApp(), "Saved successfully!", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(MainController.getApp(), "Saving failed. Another program slot is assigned at this time already!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void deleteSchedule(MaintainScheduleScreen maintainScheduleScreen, int scheduleId){
        // TODO update DM
        new DeleteScheduleDelegate(this).execute(scheduleId);
        this.maintainScheduleScreen = maintainScheduleScreen;
    }

    public void scheduleDeleted(Boolean status){
        if(status){
            this.startUseCase();
            Toast toast = Toast.makeText(MainController.getApp(), "Deleted successfully!", Toast.LENGTH_LONG);
            toast.show();
        }else{
            Toast toast = Toast.makeText(MainController.getApp(), "Deletion failed!", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    public void onDisplayScheduleList(ScheduleListScreen scheduleListScreen, long startTimeStamp, long endTimeStamp) {
        this.scheduleListScreen = scheduleListScreen;
        new RetrieveScheduleDelegate(this).execute("all", String.valueOf(startTimeStamp), String.valueOf(endTimeStamp));
    }

    public void retrievedSchedules(List<ProgramSlot> programSlots) {
        scheduleListScreen.showProgramslots(programSlots);
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

    public void selectReviewSelectPresenterProducer(Activity parentScreen, String type) {
        ControlFactory.getReviewSelectPresenterProducerController().startUseCase(parentScreen, type);
    }

}
