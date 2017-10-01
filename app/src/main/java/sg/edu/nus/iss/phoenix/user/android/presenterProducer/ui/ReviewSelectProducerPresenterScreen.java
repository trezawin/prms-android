package sg.edu.nus.iss.phoenix.user.android.presenterProducer.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.android.entity.Role;
import sg.edu.nus.iss.phoenix.user.android.entity.User;

public class ReviewSelectProducerPresenterScreen extends AppCompatActivity {

    private User user;
    private EditText useridEditText;
    private EditText passwordEditText;
    private EditText usernameEditText;
    private EditText userRolsEditText;
    private Button updateButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);
        useridEditText = (EditText) findViewById(R.id.maintain_user_id_text);
        passwordEditText = (EditText) findViewById(R.id.maintain_user_password_text);
        usernameEditText = (EditText) findViewById(R.id.maintain_user_name_text);
        userRolsEditText = (EditText) findViewById(R.id.maintain_user_role_text);
        updateButton = (Button)findViewById(R.id.maintain_user_save_button);
        deleteButton = (Button) findViewById(R.id.maintain_user_delete_button);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.user = (User) this.getIntent().getExtras().get("user");
        if(this.user!=null){
            deleteButton.setVisibility(View.VISIBLE);
            useridEditText.setFocusableInTouchMode(false);
        } else{
            deleteButton.setVisibility(View.INVISIBLE);
            useridEditText.setFocusableInTouchMode(true);
        }
        if(this.user!= null) {
            StringBuffer roles = new StringBuffer();
            for (Role role : this.user.getRoles()) {
                roles.append(",");
                roles.append(role.getRole());
            }

            useridEditText.setText(user.getId());
            passwordEditText.setText(user.getPassword());
            usernameEditText.setText(user.getName());
            if (roles.length() > 1)
                userRolsEditText.setText(roles.substring(1).toString());
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userid = useridEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String userRoles = userRolsEditText.getText().toString();
                String[] strArray = userRoles.split(",");
                ArrayList<Role> roleList = new ArrayList<Role>();
                for (String tmp : strArray){
                    Role role = new Role(tmp);
                    roleList.add(role);
                }
                User tmpUser = new User();
                tmpUser.setId(userid);
                tmpUser.setName(username);
                tmpUser.setPassword(password);
                tmpUser.setRoles(roleList);
                if(user!=null)
                    ControlFactory.getUserController().selectEditUser(tmpUser);
                else
                    ControlFactory.getUserController().selectCreateUser(tmpUser);
            }
        });



        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userid = useridEditText.getText().toString();
                ControlFactory.getUserController().selectDeleteUser(userid);
            }
        });
    }

}
