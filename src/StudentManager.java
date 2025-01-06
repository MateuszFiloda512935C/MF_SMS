import java.util.ArrayList;

public interface StudentManager {
    void addStudent(Student student); // Adds new Student to DB
    void removeStudent(String studentID); // Deletes Student from DB using studentID
    void updateStudent(String studentID, Student updatedStudent); // Updates Student data
    ArrayList<Student> displayAllStudents(); // Return array with all students
    double calculateAverageGrade(); // Calculates and returns average grade for all Students
}
