package sg.edu.nus.iss.phoenix.user.android.presenterProducer.controller;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.user.android.entity.Role;
import sg.edu.nus.iss.phoenix.user.android.entity.User;
import sg.edu.nus.iss.phoenix.user.android.presenterProducer.delegate.RetrievePresenterProducerDelegate;
import sg.edu.nus.iss.phoenix.user.android.presenterProducer.ui.PresenterProducerListScreen;
import sg.edu.nus.iss.phoenix.user.android.ui.UserScreen;

/**
 * Created by treza on 01/10/17.
 */

public class ReviewSelectPresenterProducerController {
    private PresenterProducerListScreen reviewSelectProducerPresenterScreen;
    private User user;
    private UserScreen userScreen;
    private String type;

    public void startUseCase(Activity parentScreen, String type) {
        user = null;
        Intent intent = new Intent(MainController.getApp(), PresenterProducerListScreen.class);
        parentScreen.startActivityForResult(intent, type.equals("presenter") ? 1 : 2);
        this.type = type;
    }

    public void onDisplayUserList(PresenterProducerListScreen reviewSelectProducerPresenterScreen) {
        this.reviewSelectProducerPresenterScreen = reviewSelectProducerPresenterScreen;
        new RetrievePresenterProducerDelegate(this).execute();
    }

    public void userRetrieved(List<User> userList) {
        List<User> presenterProducerList = new ArrayList<>();

        // only for presenter or producer role.
        for (User user : userList) {
            if(isContains(user.getRoles(), type)) {
                presenterProducerList.add(user);
            }
        }

        this.reviewSelectProducerPresenterScreen.showUsers(presenterProducerList);
    }

    private boolean isContains(ArrayList<Role> roles, String roleName) {
        for (Role r : roles) {
            if(r.getRole().equals(roleName))
                return true;
        }
        return false;
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
