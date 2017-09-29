package sg.edu.nus.iss.phoenix.user.android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.UpdateProgramDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.UpdateUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.entity.Role;
import sg.edu.nus.iss.phoenix.user.android.entity.User;

public class UserScreen extends AppCompatActivity {

    private User user;
    private EditText useridEditText;
    private EditText passwordEditText;
    private EditText usernameEditText;
    private EditText userRolsEditText;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);
        useridEditText = (EditText) findViewById(R.id.maintain_user_id_text);
        passwordEditText = (EditText) findViewById(R.id.maintain_user_password_text);
        usernameEditText = (EditText) findViewById(R.id.maintain_user_name_text);
        userRolsEditText = (EditText) findViewById(R.id.maintain_user_role_text);
        updateButton = (Button)findViewById(R.id.maintain_user_save_button);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.user = (User) this.getIntent().getExtras().get("user");

        StringBuffer roles = new StringBuffer();
        for(Role role :  this.user.getRoles()){
            roles.append(",");
            roles.append(role.getRole());
        }

        useridEditText.setText(user.getId());
        passwordEditText.setText(user.getPassword());
        usernameEditText.setText(user.getName());
        if(roles.length()>1)
            userRolsEditText.setText(roles.substring(1).toString());
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
                User user = new User();
                user.setId(userid);
                user.setName(username);
                user.setPassword(password);
                user.setRoles(roleList);
                ControlFactory.getUserController().selectEditUser(user);
            }
        });
    }

}
