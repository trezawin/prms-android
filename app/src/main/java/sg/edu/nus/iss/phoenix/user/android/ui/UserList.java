package sg.edu.nus.iss.phoenix.user.android.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.core.android.ui.MainScreen;
import sg.edu.nus.iss.phoenix.schedule.android.ui.ScheduleListScreen;
import sg.edu.nus.iss.phoenix.user.android.entity.User;

public class UserList extends AppCompatActivity {

    private ListView mListView;
    private UserAdapter userAdapter;
    private User selectedUser = null;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        createButton = (Button) findViewById(R.id.maintain_user_create_button);

        // set back button action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ArrayList<User> users = new ArrayList<User>();
        userAdapter = new UserAdapter(this, users);
        mListView = (ListView)findViewById(R.id.userListView);
        mListView.setAdapter(userAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int itemPosition     = position;
                User  itemValue = null;

                try {
                    itemValue = (User) userAdapter.getItem(position);
                    if(itemValue!=null) {
                        ControlFactory.getUserController().selectUpdateUser(itemValue);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();*/

            }

        });
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);
        ControlFactory.getUserController().onDisplayUserList(this);
    }

    public void showUsers(List<User> users) {
        Log.i(UserList.class.getName(), "KMB" + users.size());
        userAdapter.clear();
        userAdapter.addAll(users);
    }

    // back button action bar
    public boolean onOptionsItemSelected(MenuItem item){

        Intent intent = new Intent(getApplicationContext(), MainScreen.class);
        MainController.displayScreen(intent);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ControlFactory.getUserController().selectUpdateUser(null);
            }

        });
    }
}
