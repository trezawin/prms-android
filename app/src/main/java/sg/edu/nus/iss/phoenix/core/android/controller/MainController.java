package sg.edu.nus.iss.phoenix.core.android.controller;

import android.app.Application;
import android.content.Intent;

import sg.edu.nus.iss.phoenix.core.android.ui.MainScreen;

public class MainController {
    private static Application app = null;
    private static String loggedInUserName = "";

    private String username;
    private MainScreen mainScreen;

    public static void setLoggedInUserName(String name){
        loggedInUserName = name;
    }

    public static String getLoggedInUserName(){
        return loggedInUserName;
    }

    public static Application getApp() {
        return app;
    }

    public static void setApp(Application app) {
        MainController.app = app;
    }

    public static void displayScreen(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        app.startActivity(intent);
    }

    public void startUseCase(String username) {
        this.username = username;

        Intent intent = new Intent(MainController.getApp(), MainScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplay(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        mainScreen.showUsername(username);
    }

    public void selectMaintainProgram() {
        ControlFactory.getProgramController().startUseCase();
    }

    public void selectScheduledProgram() {
        ControlFactory.getScheduleController().startUseCase();
    }

    public void maintainedProgram() {
        startUseCase(username);
    }

    public void selectLogout() {
        username = "<not logged in>";
        ControlFactory.getLoginController().logout();
    }

    public void selectMaintainUser(){
        ControlFactory.getUserController().startUseCase();
    }
}
