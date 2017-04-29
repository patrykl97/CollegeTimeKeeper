package ie.ul.collegetimekeeper.Objects;

import ie.ul.collegetimekeeper.Objects.Lecturer;
import ie.ul.collegetimekeeper.Objects.Work;

/**
 * Created by Patryk on 10/03/2017.
 */

public class Module {

    private int dbModuleID;
    private String moduleID;
    private int lecturerID;
    private String moduleTitle;
    private Lecturer lecturer;
    private Work work;

    public Module() {

    }

    public Module(String moduleID, String moduleTitle) {
        this.moduleID = moduleID;
        this.moduleTitle = moduleTitle;
    }

    public void setDbModuleID(int dbModuleID){
        this.dbModuleID = dbModuleID;
    }

    public int getDbModuleID(){
        return dbModuleID;
    }

    public void setLecturer(int lecturerID, String lecturerName, String lecturerSurname, String collegeName) {
        lecturer = new Lecturer(lecturerID, lecturerName, lecturerSurname, collegeName);
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturerID(int lecturerID)
    {
        this.lecturerID = lecturerID;
    }

    public int getLecturerID()
    {
        return lecturerID;
    }

    public void setWork() {
        work = new Work();
    }

    public Work getWork() {
        return  work;
    }

    public String getModuleID() {
        return moduleID;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

}
