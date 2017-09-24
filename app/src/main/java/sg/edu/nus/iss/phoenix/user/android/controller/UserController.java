package sg.edu.nus.iss.phoenix.user.android.controller;

import android.content.Intent;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.android.ui.MaintainScheduleScreen;
import sg.edu.nus.iss.phoenix.user.android.delegate.RetrieveUsersDelegate;
import sg.edu.nus.iss.phoenix.user.android.entity.User;
import sg.edu.nus.iss.phoenix.user.android.ui.UserList;

/**
 * Created by kmb on 16/9/17.
 */

public class UserController {
    private UserList userListScreen;
    private User user;

    public void startUseCase(){
        user = null;
        Intent intent = new Intent(MainController.getApp(), UserList.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayUserList(UserList userListScreen){
        this.userListScreen = userListScreen;
        new RetrieveUsersDelegate(this).execute();
    }

    public void selectCreateSchedule(){
        ProgramSlot programSlot = new ProgramSlot();
        Intent intent = new Intent(MainController.getApp(), MaintainScheduleScreen.class);
        intent.putExtra("program_slot", programSlot);
        MainController.displayScreen(intent);
    }

    public void userRetrieved(List<User> userList){
        this.userListScreen.showUsers(userList);
    }
}
