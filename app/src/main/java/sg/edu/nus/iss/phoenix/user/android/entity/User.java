package sg.edu.nus.iss.phoenix.user.android.entity;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kmb on 16/9/17.
 */

public class User implements Serializable{
    private String id;
    private String password;
    private String name;
    private ArrayList<Role> roles = new ArrayList<Role>();

    public User(String id, String password, String name, ArrayList<Role> roles){
        this.id = id;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    public User(){

    }



    public String getId() {
        return this.id;
    }

    public void setId(String idIn) {
        this.id = idIn;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String passwordIn) {
        this.password = passwordIn;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String nameIn) {
        this.name = nameIn;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }
}
