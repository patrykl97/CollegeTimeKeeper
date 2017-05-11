package ie.ul.collegetimekeeper.Objects;

import java.io.Serializable;
import java.util.ArrayList;

import ie.ul.collegetimekeeper.Objects.Lecturer;
import ie.ul.collegetimekeeper.Objects.Work;

/**
 * Created by Patryk on 10/03/2017.
 */

public class Module implements Serializable{

    private int dbModuleID;
    private String moduleID;
    private int lecturerID;
    private String moduleTitle;
    private Lecturer lecturer;
    private Work work;
    private ArrayList<Work> workList = new ArrayList<Work>();
    private int position;

    public Module(String moduleID) {
        this.moduleID = moduleID;
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

    public void setWork(String typeWork){
        work = new Work(typeWork);
    }


    public Work getWork() {
        return  work;
    }

    public void setWorkList(ArrayList<Work> workList){
        this.workList = workList;
    }

    public ArrayList<Work> getWorkList() { return workList; }

    public void addToWorkList(String typeOfWork){
        workList.add(new Work(typeOfWork));
    }

    public String getModuleID() {
        return moduleID;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public int getPosition(){ return  position; }

}
