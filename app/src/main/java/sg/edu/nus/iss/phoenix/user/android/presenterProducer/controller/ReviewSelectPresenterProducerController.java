package sg.edu.nus.iss.phoenix.user.android.presenterProducer.controller;

import android.content.Intent;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.schedule.android.ui.MaintainScheduleScreen;
import sg.edu.nus.iss.phoenix.user.android.delegate.CreateUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.DeleteUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.RetrieveUsersDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.UpdateUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.entity.User;
import sg.edu.nus.iss.phoenix.user.android.presenterProducer.delegate.RetrievePresenterProducerDelegate;
import sg.edu.nus.iss.phoenix.user.android.presenterProducer.ui.PresenterProducerList;
import sg.edu.nus.iss.phoenix.user.android.presenterProducer.ui.ReviewSelectProducerPresenterScreen;
import sg.edu.nus.iss.phoenix.user.android.ui.UserList;
import sg.edu.nus.iss.phoenix.user.android.ui.UserScreen;

/**
 * Created by treza on 01/10/17.
 */

public class ReviewSelectPresenterProducerController {
    private PresenterProducerList reviewSelectProducerPresenterScreen;
    private User user;
    private UserScreen userScreen;

    public void startUseCase() {
        user = null;
        Intent intent = new Intent(MainController.getApp(), PresenterProducerList.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayUserList(PresenterProducerList reviewSelectProducerPresenterScreen) {
        this.reviewSelectProducerPresenterScreen = reviewSelectProducerPresenterScreen;
        new RetrievePresenterProducerDelegate(this).execute();
    }

    public void userRetrieved(List<User> userList) {
        this.reviewSelectProducerPresenterScreen.showUsers(userList);
    }

    public void onDisplayUser(UserScreen userScreen) {
        this.userScreen = userScreen;
        new RetrievePresenterProducerDelegate(this).execute();
    }

    public void selectUpdateUser(User user) {
        Intent intent = new Intent(MainController.getApp(), UserScreen.class);
        intent.putExtra("user", user);
        MainController.displayScreen(intent);
    }
}
