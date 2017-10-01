package sg.edu.nus.iss.phoenix.user.android.presenterProducer.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.android.entity.User;


/**
 * Created by treza on 01/10/17.
 */
public class PresenterProducerListScreen extends AppCompatActivity {

    private ListView mListView;
    private PresenterProducerAdapter presenterProducerAdapter;
    private User selectedUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenterproducer_list);

        ArrayList<User> users = new ArrayList<User>();
        presenterProducerAdapter = new PresenterProducerAdapter(this, users);
        mListView = (ListView)findViewById(R.id.prenternProducerListView);
        mListView.setAdapter(presenterProducerAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int itemPosition = position;
                User itemValue = null;
                try {
                    itemValue = (User) presenterProducerAdapter.getItem(position);
                    if(itemValue!=null) {
                        Intent intent = new Intent();
                        intent.putExtra("user", itemValue);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
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
        Log.i(PresenterProducerListScreen.class.getName(), "KMB" + users.size());
        presenterProducerAdapter.clear();
        presenterProducerAdapter.addAll(users);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
