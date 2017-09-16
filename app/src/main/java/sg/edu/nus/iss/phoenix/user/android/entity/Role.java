package sg.edu.nus.iss.phoenix.user.android.entity;

/**
 * Created by kmb on 16/9/17.
 */

public class Role {
    private String role;
    private String accessPrivilege;

    public Role() {}

    public Role(String roleIn) {
        this.role = roleIn;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String roleIn) {
        this.role = roleIn;
    }

    public String getAccessPrivilege() {
        return this.accessPrivilege;
    }

    public void setAccessPrivilege(String accessPrivilegeIn) {
        this.accessPrivilege = accessPrivilegeIn;
    }
}
