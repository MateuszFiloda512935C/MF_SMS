
// Link to jdbc driver for sqlite https://github.com/xerial/sqlite-jdbc/releases
// Remember to add jdbc driver to your project modules and to your project manifest,
// Without that connection to the DB will not work.
public class Main {
    public static void main(String[] args) {
        String dbURL = "jdbc:sqlite:students.db"; // If URL doesn't match with existing DB path, this program will automatically create sqLite DB
        StudentManagerImpl studentManager = new StudentManagerImpl(dbURL); // Creates studentManager
        new StudentManagementGUI(studentManager); // Creates UI
    }
}