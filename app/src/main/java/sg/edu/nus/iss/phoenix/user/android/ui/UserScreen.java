package sg.edu.nus.iss.phoenix.user.android.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.user.android.entity.Role;
import sg.edu.nus.iss.phoenix.user.android.entity.User;

public class UserScreen extends AppCompatActivity {

    private User user;
    private EditText useridEditText;
    private EditText passwordEditText;
    private EditText usernameEditText;
    private EditText userRolsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);
        useridEditText = (EditText) findViewById(R.id.maintain_user_id_text);
        passwordEditText = (EditText) findViewById(R.id.maintain_user_password_text);
        usernameEditText = (EditText) findViewById(R.id.maintain_user_name_text);
        userRolsEditText = (EditText) findViewById(R.id.maintain_user_role_text);
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


}
