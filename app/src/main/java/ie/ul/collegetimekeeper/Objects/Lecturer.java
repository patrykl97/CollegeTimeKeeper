package ie.ul.collegetimekeeper.Objects;

/**
 * Created by Patryk on 10/03/2017.
 */

public class Lecturer extends User {

    public Lecturer() {
        this.setUserType("Staff");
    }

    public Lecturer(int lecturerID, String lecturerName, String lecturerSurname, String collegeName) {
        super(lecturerID, lecturerName, lecturerSurname, collegeName, "Staff");
    }



}
