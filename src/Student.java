public class Student {
    private String name;
    private int age;
    private double grade;
    private String studentID;

    // Constructor
    Student(String name, int age, double grade, String studentID) {
        if (age <= 0){
            throw new IllegalArgumentException("Age must be greater than 0");
        }
        if (grade < 0.0 || grade > 100.0){
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
        if (studentID == null || studentID.isEmpty()){
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("Student name cannot be null or empty");
        }

        this.name = name;
        this.age = age;
        this.grade = grade;
        this.studentID = studentID;
    }

    // All getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("Student name cannot be null or empty");
        }
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0){
            throw new IllegalArgumentException("Age must be greater than 0");
        }
        this.age = age;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 100.0){
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
        this.grade = grade;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        if (studentID == null || studentID.isEmpty()){
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        this.studentID = studentID;
    }

    // Methods
    public void displayInfo(){
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Grade: " + grade);
        System.out.println("Student ID: " + studentID);
    }
}
