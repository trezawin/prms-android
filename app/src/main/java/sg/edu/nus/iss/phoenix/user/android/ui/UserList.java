package sg.edu.nus.iss.phoenix.user.android.ui;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.android.entity.User;

public class UserList extends AppCompatActivity {

    private ListView mListView;
    private UserAdapter userAdapter;
    private User selectedUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ArrayList<User> users = new ArrayList<User>();
        userAdapter = new UserAdapter(this, users);
        mListView = (ListView)findViewById(R.id.userListView);
        mListView.setAdapter(userAdapter);
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
}
