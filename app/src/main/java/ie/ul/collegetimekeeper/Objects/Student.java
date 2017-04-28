package ie.ul.collegetimekeeper.Objects;

/**
 * Created by Patryk on 22/03/2017.
 */

public class Student extends User {

    public Student() {
        this.setUserType("Student");
    }

    public Student(int studentID, String studentName, String studentSurname, String collegeName) {
        super(studentID, studentName, studentSurname, collegeName, "Student");
    }

}
