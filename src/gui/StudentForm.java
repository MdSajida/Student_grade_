package gui;

import Model.Student;
import Model.Grade;
import Model.Course;
import Service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private StudentService studentService;
    private JTextField studentIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField genderField;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    // New fields for search functionality
    private JTextField searchStudentIdField;

    public StudentForm() {
        studentService = new StudentService();

        setTitle("Student Form");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI components
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdField = new JTextField(15);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(15);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField(15);

        JLabel genderLabel = new JLabel("Gender:");
        genderField = new JTextField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(studentIdLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(studentIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(firstNameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(genderLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(genderField, gbc);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");
        JButton updateButton = new JButton("Update");

        saveButton.setPreferredSize(new Dimension(80, 30));
        deleteButton.setPreferredSize(new Dimension(80, 30));
        updateButton.setPreferredSize(new Dimension(80, 30));

        // Set button colors
        saveButton.setBackground(new Color(0, 204, 0)); // Green
        saveButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(204, 0, 0)); // Red
        deleteButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(0, 0, 204)); // Blue
        updateButton.setForeground(Color.WHITE);

        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        // Set up table to display students
        String[] columnNames = {"Student ID", "First Name", "Last Name", "Gender"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        
        saveButton.addActionListener(e -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String gender = genderField.getText();
                
                
                // Check first name and last name
                   if (!firstName.matches("^[a-zA-Z]+$")) {
                       JOptionPane.showMessageDialog(this, "Invalid first name. Please enter alphabetic characters only.");
                       return;
                   }
                   if (!lastName.matches("^[a-zA-Z]+(?: [a-zA-Z]+)*$")) {
                	    JOptionPane.showMessageDialog(this, "Invalid last name. Please enter alphabetic characters only.");
                	    return;
                	}

               
                // Validate input
                if (studentIdField.getText().isEmpty() || firstName.isEmpty() || lastName.isEmpty() || gender.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Check if student already exists
                if (studentService.getStudentById(studentId) != null) {
                    JOptionPane.showMessageDialog(this, "Student with this ID already exists.");
                    return;
                }
                
                // Check gender
                if (!gender.equals("M") && !gender.equals("F")) {
                    JOptionPane.showMessageDialog(this, "Invalid gender. Please enter 'M' or 'F'.");
                    return;
                }

                Student student = new Student();
                student.setStudentId(studentId);
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setGender(gender);
                studentService.addStudent(student);
                JOptionPane.showMessageDialog(this, "Student saved successfully!");
                clearFields();
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter valid Data ");
                //JOptionPane.showMessageDialog(this, "Enter valid Data Error: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                int studentId = (int) tableModel.getValueAt(selectedRow, 0);
                studentService.deleteStudent(studentId);
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                try {
                    int studentId = (int) tableModel.getValueAt(selectedRow, 0);
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String gender = genderField.getText();
                    
                  // Check first name and last name
                    if (!firstName.matches("^[a-zA-Z]+$")) {
                        JOptionPane.showMessageDialog(this, "Invalid first name. Please enter alphabetic characters only.");
                        return;
                    }
                    if (!lastName.matches("^[a-zA-Z]+$")) {
                        JOptionPane.showMessageDialog(this, "Invalid last name. Please enter alphabetic characters only.");
                        return;
                    }

                    // Validate input
                    if (firstName.isEmpty() || lastName.isEmpty() || gender.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Student student = new Student();
                    student.setStudentId(studentId);
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    student.setGender(gender);
                    studentService.updateStudent(student);
                    JOptionPane.showMessageDialog(this, "Student updated successfully!");
                    clearFields();
                    refreshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a student to update.");
            }
        });

        // Create a wrapper panel for formPanel and buttonPanel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add search section
        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel searchFormPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel searchStudentIdLabel = new JLabel("Search by Student ID:");
        searchStudentIdField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchFormPanel.add(searchStudentIdLabel);
        searchFormPanel.add(searchStudentIdField);
        searchFormPanel.add(searchButton);
        searchPanel.add(searchFormPanel, BorderLayout.NORTH);

        // Center the search panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateStudentReport();
            }
        });

        setVisible(true);
        refreshTable();
    }

    private void generateStudentReport() {
        try {
            int studentId = Integer.parseInt(searchStudentIdField.getText());
            Student student = studentService.getStudentById(studentId);
            if (student == null) {
                JOptionPane.showMessageDialog(this, "Student not found.");
                return;
            }
            List<Grade> grades = studentService.getGradesByStudentId(studentId);
            double totalPoints = 0;
            double totalCredits = 0;
            StringBuilder report = new StringBuilder();
            report.append("Student ID: ").append(student.getStudentId()).append("\n");
            report.append("Name: ").append(student.getFirstName()).append(" ").append(student.getLastName()).append("\n");
            report.append("Gender: ").append(student.getGender()).append("\n\n");
            report.append("Courses:\n");
            report.append(String.format("%-10s %-20s %-10s %-15s\n", "Course ID", "Course Name", "Credits", "Grade Points"));
            for (Grade grade : grades) {
                Course course = studentService.getCourseById(grade.getCourseId());
                report.append(String.format("%-10s %-20s %-10s %-15s\n", course.getCourseId(), course.getCourseName(), course.getCredits(), grade.getGrade()));
                totalPoints += grade.getGrade() * course.getCredits();
                totalCredits += course.getCredits();
            }
            double averageGradePoints = totalPoints / totalCredits;
            report.append("\nAverage Grade Points: ").append(String.format("%.2f", averageGradePoints));

            // Display report in a new dialog
            JDialog reportDialog = new JDialog(this, "Student Report", true);
            reportDialog.setSize(500, 400);
            reportDialog.setLocationRelativeTo(this);
            JTextArea reportArea = new JTextArea(report.toString());
            reportArea.setEditable(false);
            reportArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(reportArea);
            reportDialog.add(scrollPane);
            reportDialog.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        studentIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        genderField.setText("");
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // Clear the existing rows
        for (Student student : studentService.getAllStudents()) {
            Object[] rowData = {
                student.getStudentId(),
                student.getFirstName(),
                student.getLastName(),
                student.getGender()
            };
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentForm());
    }
}
