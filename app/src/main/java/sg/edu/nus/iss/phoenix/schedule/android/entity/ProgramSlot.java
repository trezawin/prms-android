package sg.edu.nus.iss.phoenix.schedule.android.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kmb on 24/9/17.
 */

public class ProgramSlot implements Serializable {
    private int id;
    private Date duration;
    private Date dateOfProgram;
    private String programName;
    private String assignedBy;

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public Date getDateOfProgram() {
        return dateOfProgram;
    }

    public void setDateOfProgram(Date dateOfProgram) {
        this.dateOfProgram = dateOfProgram;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }
}
