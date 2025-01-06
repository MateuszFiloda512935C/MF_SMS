import java.sql.*;
import java.util.ArrayList;

public class StudentManagerImpl implements StudentManager {
    private Connection conn;

    // Constructor that connects to DB
    public StudentManagerImpl(String dbUrl) {
        try{
            conn = DriverManager.getConnection(dbUrl);
            createTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // For testing connection with DB
    public boolean testConn(){
        return conn != null;
    }

    // Check if id exists in DB
    public boolean idExists(String id){
        if (id.isEmpty() || id == null) { return false; }
        String sql = "SELECT COUNT(*) FROM students WHERE studentID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // If something goes wrong, assume no ID was found
        return false;
    }

    // Create table if it not exists in DB already
    private void createTableIfNotExists() throws SQLException{
        String createTable = "CREATE TABLE IF NOT EXISTS students ("
                + "name TEXT,"
                + "age INTEGER,"
                + "grade REAL,"
                + "studentID TEXT PRIMARY KEY"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTable);
        }
    }

    // Closes connection with DB
    public void closeConnection(){
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addStudent(Student student) {
        String sql = "INSERT INTO students (name, age, grade, studentID) VALUES (?, ?, ?, ?);";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setDouble(3, student.getGrade());
            preparedStatement.setString(4, student.getStudentID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeStudent(String studentID){
        String sql = "DELETE FROM students WHERE studentID = ?;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setString(1, studentID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStudent(String studentID, Student updatedStudent) {
        String updateSQL = "UPDATE students SET name = ?, age = ?, grade = ? WHERE studentID = ?;";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, updatedStudent.getName());
            preparedStatement.setInt(2, updatedStudent.getAge());
            preparedStatement.setDouble(3, updatedStudent.getGrade());
            preparedStatement.setString(4, studentID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Student> displayAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String selectSQL = "SELECT * FROM students;";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                double grade = resultSet.getDouble("grade");
                String studentID = resultSet.getString("studentID");
                students.add(new Student(name, age, grade, studentID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public double calculateAverageGrade() {
        String averageSQL = "SELECT AVG(grade) AS averageGrade FROM students;";
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(averageSQL)) {
            if (resultSet.next()) {
                return resultSet.getDouble("averageGrade");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
