package ie.ul.collegetimekeeper.Objects;

import java.io.Serializable;

/**
 * Created by Patryk on 10/03/2017.
 */

public class Work implements Serializable{

    private String module;
    private String typeOfWork;
    private int timeSpent;

    public Work(String typeOfWork) {
        this.typeOfWork = typeOfWork;
        this.timeSpent = 0;
    }
    public Work(String module, String typeOfWork, int timeSpent) {
        this.module = module;
        this.typeOfWork = typeOfWork;
        this.timeSpent = timeSpent;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public void setModule(String module){
        this.module = module;
    }

    public String getModule(){
        return module;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

}
