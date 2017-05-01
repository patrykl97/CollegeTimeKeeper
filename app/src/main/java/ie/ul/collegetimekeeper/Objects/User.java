package ie.ul.collegetimekeeper.Objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Patryk on 10/03/2017.
 */

public class User implements Serializable{

    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String collegeName;
    private String userType;
    private boolean signedIn;
    private ArrayList<Module> modulesList = new ArrayList<Module>();
    private boolean isStudent; //might have to be inititialised

    public User() {
        this.signedIn = true;
    }
    public User(int id, String name, String surname, String collegeName, String userType){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.collegeName = collegeName;
        this.userType = userType;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() { return surname; }

    public void setEmail(String email) { this.email = email; }

    public String getEmail() { return email; }

    public void setPassword(String password) { this.password = password; }

    public String getPassword() { return password; }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
        if(this.userType.equals("Student")){
            isStudent = true;
        }
        else {
            isStudent = false;
        }
    }

    public String getUserType(){
        return userType;
    }

    public void logOut() {
        signedIn = false;
    }

    public boolean getSignedIn() {
        return signedIn;
    }

    public void addModule(String moduleID) {
        this.modulesList.add(new Module(moduleID));
    }

    public ArrayList<Module> getModulesList(){
        return modulesList;
    }

}
