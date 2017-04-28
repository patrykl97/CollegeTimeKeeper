package ie.ul.collegetimekeeper.Objects;

/**
 * Created by Patryk on 10/03/2017.
 */

public class Work {

    private String [] typesOfWork;
    private int timeSpent;

    public Work() {
        this.typesOfWork = new String[]{"Homework","Project","Assignment"};
        this.timeSpent = 0;
    }

    public String [] getTypesOfWork() {
        return typesOfWork;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

}
