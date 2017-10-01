package sg.edu.nus.iss.phoenix.user.android.presenterProducer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.android.entity.User;


/**
 * Created by treza on 01/10/17.
 */
public class PresenterProducerList extends AppCompatActivity {

    private ListView mListView;
    private PresenterProducerAdapter presenterProducerAdapter;
    private User selectedUser = null;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        createButton = (Button) findViewById(R.id.maintain_user_create_button);

        ArrayList<User> users = new ArrayList<User>();
        presenterProducerAdapter = new PresenterProducerAdapter(this, users);
        mListView = (ListView)findViewById(R.id.userListView);
        mListView.setAdapter(presenterProducerAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int itemPosition     = position;
                User  itemValue = null;

                try {
                    itemValue = (User) presenterProducerAdapter.getItem(position);
                    if(itemValue!=null) {
                        ControlFactory.getUserController().selectUpdateUser(itemValue);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);
        ControlFactory.getReviewSelectPresenterProducerController().onDisplayUserList(this);
    }

    public void showUsers(List<User> users) {
        Log.i(PresenterProducerList.class.getName(), "KMB" + users.size());
        presenterProducerAdapter.clear();
        presenterProducerAdapter.addAll(users);
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
