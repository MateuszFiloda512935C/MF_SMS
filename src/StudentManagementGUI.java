import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class StudentManagementGUI {
    private StudentManagerImpl studentManager;

    // GUI constructor
    public StudentManagementGUI(StudentManagerImpl studentManager) {
        this.studentManager = studentManager;
        createGUI();
    }

    // GUI logic
    private void createGUI() {
        /*
         * MAIN FRAME
         */
        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.LIGHT_GRAY);

        /*
         * INPUT PANEL
         */
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBackground(Color.lightGray);

        // Input labels
        JLabel idLabel = new JLabel("Student ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel ageLabel = new JLabel("Age:");
        JLabel gradeLabel = new JLabel("Grade:");

        // Input fields
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField gradeField = new JTextField();

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);
        inputPanel.add(gradeLabel);
        inputPanel.add(gradeField);

        /*
         * BUTTONS PANEL
         */
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        buttonPanel.setBackground(Color.lightGray);
        // Buttons
        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Student");
        JButton updateButton = new JButton("Update Student");
        JButton displayButton = new JButton("Display All Students");
        JButton calculateButton = new JButton("Calculate Average");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(calculateButton);

        /*
        * OUTPUT PANEL
        */

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        // Styling
        outputArea.setFont(new Font("Consolas", Font.BOLD, 16));
        outputArea.setForeground(Color.white);
        outputArea.setBackground(Color.darkGray);
        outputArea.setBorder(BorderFactory.createLineBorder(Color.black));

        // Creates scrollable area
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Add Scrollbar only if needed

        /*
         * WRAPPER PANEL TO MAINTAIN PROPORTIONS
         */
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        // Wrapping all the panels into one and placing in center
        // So that every panel will resize equally thus maintaining correct proportion

        // Input panel (20%)
        gbc.gridy = 0;
        gbc.weighty = 0.2;
        wrapperPanel.add(inputPanel, gbc);

        // Button panel (10%)
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        wrapperPanel.add(buttonPanel, gbc);

        // Output panel (70%)
        gbc.gridy = 2;
        gbc.weighty = 0.7;
        wrapperPanel.add(scrollPane, gbc);

        frame.add(wrapperPanel, BorderLayout.CENTER);


        /*
        * BUTTONS LISTENERS, LOGIC
        */
        addButton.addActionListener(e -> {
            try {
                // Check if Fields are correct
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                double grade = Double.parseDouble(gradeField.getText().trim());

                if (id.isEmpty() || name.isEmpty()) {
                    outputArea.setText("Error: Make sure that all fields are fill in.");
                    return;
                }

                if (studentManager.idExists(id)){
                    outputArea.setText("Error: Student with id: " + id + ", already exists.");
                    return;
                }

                if (age <= 0) {
                    outputArea.setText("Error: Age must be a positive number.");
                    return;
                }

                if (grade < 0.0 || grade > 100.0) {
                    outputArea.setText("Error: Grade must be between 0.0 and 100.0.");
                    return;
                }

                studentManager.addStudent(new Student(name, age, grade, id));
                outputArea.setText("Student added successfully.");
            } catch (NumberFormatException ex) {
                outputArea.setText("Error: Invalid input. Ensure age and grade are numbers.");
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage()); // If unknown error occurs, dump it message to output
            }
        });

        removeButton.addActionListener(e -> {
            // Check if id exist in DB
            String id = idField.getText().trim();
            if (id.isEmpty() || id == null) {
                outputArea.setText("Error: Make sure that ID field is not empty.");
                return;
            }
            if (!studentManager.idExists(id)){
                outputArea.setText("Error: Student with id: " + id + ", does not exist.");
                return;
            }
            studentManager.removeStudent(id);
            outputArea.setText("Student with ID " + id + " removed successfully.");
        });

        updateButton.addActionListener(e -> {
            try {
                // Check if fields are correct.
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                double grade = Double.parseDouble(gradeField.getText().trim());

                if (id.isEmpty() || name.isEmpty()){
                    outputArea.setText("Error: Make sure that all fields are fill in.");
                    return;
                }

                if (!studentManager.idExists(id)){
                    outputArea.setText("Error: Student with id: " + id + ", does not exist.");
                    return;
                }

                if (age <= 0) {
                    outputArea.setText("Error: Age must be a positive number.");
                    return;
                }

                if (grade < 0.0 || grade > 100.0) {
                    outputArea.setText("Error: Grade must be between 0.0 and 100.0.");
                    return;
                }

                studentManager.updateStudent(id, new Student(name, age, grade, id));
                outputArea.setText("Student updated successfully.");
            } catch (NumberFormatException ex) {
                outputArea.setText("Error: Invalid input. Ensure age and grade are numbers.");
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage()); // If unknown error occurs, dump it message to output
            }
        });

        displayButton.addActionListener(e -> {
            // Get array with students object from DB
            ArrayList<Student> students = studentManager.displayAllStudents();
            if (students.isEmpty()) {
                outputArea.setText("No students found.");
            } else {
                // Build string, iterating over array
                StringBuilder sb = new StringBuilder("Student List:\n");
                for (Student student : students) {
                    sb.append("ID: ").append(student.getStudentID())
                            .append(", Name: ").append(student.getName())
                            .append(", Age: ").append(student.getAge())
                            .append(", Grade: ").append(student.getGrade())
                            .append("\n");
                }
                outputArea.setText(sb.toString());
            }
        });

        calculateButton.addActionListener(e -> {
            double average = studentManager.calculateAverageGrade();
            outputArea.setText("Average Grade: " + average);
        });

        // Closing listener that ensures the DB connection is closed before killing the application
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                studentManager.closeConnection();
                outputArea.setText("Goodbye!");
                System.exit(0);
            }
        });

        // Shows GUI
        frame.setVisible(true);
    }
}
