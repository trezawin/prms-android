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

//    private String presenterId;
//    private String presenterName;
//    private String producerId;
//    private String producerName;
//    private String radioProgramName;
//
//    public String getPresenterId() {
//        return presenterId;
//    }
//
//    public void setPresenterId(String presenterId) {
//        this.presenterId = presenterId;
//    }
//
//    public String getPresenterName() {
//        return presenterName;
//    }
//
//    public void setPresenterName(String presenterName) {
//        this.presenterName = presenterName;
//    }
//
//    public String getProducerId() {
//        return producerId;
//    }
//
//    public void setProducerId(String producerId) {
//        this.producerId = producerId;
//    }
//
//    public String getProducerName() {
//        return producerName;
//    }
//
//    public void setProducerName(String producerName) {
//        this.producerName = producerName;
//    }
//
//    public String getRadioProgramName() {
//        return radioProgramName;
//    }
//
//    public void setRadioProgramName(String radioProgramName) {
//        this.radioProgramName = radioProgramName;
//    }

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
