package sg.edu.nus.iss.phoenix.user.android.controller;

import android.content.Intent;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.android.ui.MaintainScheduleScreen;
import sg.edu.nus.iss.phoenix.user.android.delegate.CreateUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.DeleteUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.RetrieveUsersDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.UpdateUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.entity.User;
import sg.edu.nus.iss.phoenix.user.android.ui.UserList;
import sg.edu.nus.iss.phoenix.user.android.ui.UserScreen;

/**
 * Created by kmb on 16/9/17.
 */

public class UserController {
    private UserList userListScreen;
    private User user;
    private UserScreen userScreen;

    public void startUseCase() {
        user = null;
        Intent intent = new Intent(MainController.getApp(), UserList.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayUserList(UserList userListScreen) {
        this.userListScreen = userListScreen;
        new RetrieveUsersDelegate(this).execute();
    }

    public void userRetrieved(List<User> userList) {
        this.userListScreen.showUsers(userList);
    }

    public void onDisplayUser(UserScreen userScreen) {
        this.userScreen = userScreen;
        new RetrieveUsersDelegate(this).execute();
    }

    public void selectUpdateUser(User user) {
        Intent intent = new Intent(MainController.getApp(), UserScreen.class);
        intent.putExtra("user", user);
        MainController.displayScreen(intent);
    }

    ;

    public void selectCreateUser(User user) {
        new CreateUserDelegate(this).execute(user);
    }

    ;

    public void selectDeleteUser(String userid) {
        new DeleteUserDelegate(this).execute(userid);
    }

    ;

    public void selectEditUser(User user) {
        new UpdateUserDelegate(this).execute(user);
    }

    ;

    void usersRetrieved() {
    }

    ;

    public void userCreated(boolean flag) {
        if (flag)
            startUseCase();
    }

    ;

    public void userDeleted(boolean flag) {
        if (flag)
            startUseCase();
    }

    ;

    public void userUpdated(boolean flag) {
        if (flag)
            startUseCase();
    }

    ;
}
