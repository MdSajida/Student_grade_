package gui;

import Model.Course;
import Service.CoureseService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CourseForm extends JFrame {
    private CoureseService courseService;
    private JTextField courseIdField;
    private JTextField courseNameField;
    private JTextField courseDescriptionField;
    private JTextField creditsField; // Changed from JComboBox to JTextField
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public CourseForm() {
        courseService = new CoureseService();

        setTitle("Course Form");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI components
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Padding between components

        JLabel courseIdLabel = new JLabel("Course ID:");
        courseIdField = new JTextField(20);
        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameField = new JTextField(20);
        JLabel courseDescriptionLabel = new JLabel("Course Description:");
        courseDescriptionField = new JTextField(20);
        JLabel creditsLabel = new JLabel("Credits:");
        creditsField = new JTextField(20); // Changed from JComboBox<Double> to JTextField

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(courseIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(courseIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(courseNameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(courseNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(courseDescriptionLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(courseDescriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(creditsLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        inputPanel.add(creditsField, gbc);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton saveButton = new JButton("Add");
        saveButton.setPreferredSize(new Dimension(80, 30));
        saveButton.setBackground(new Color(144, 202, 249));
        saveButton.setForeground(Color.BLACK);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(80, 30));
        deleteButton.setBackground(new Color(250, 192, 144));
        deleteButton.setForeground(Color.BLACK);

        JButton updateButton = new JButton("Update");
        updateButton.setPreferredSize(new Dimension(80, 30));
        updateButton.setBackground(new Color(182, 230, 176));
        updateButton.setForeground(Color.BLACK);

        saveButton.addActionListener(e -> {
        	try {
            String courseIdText = courseIdField.getText();
            String courseName = courseNameField.getText();
            String courseDescription = courseDescriptionField.getText();
            String creditsText = creditsField.getText();
            
            // Check if student already exists
            if (courseService.getCourseById(Integer.parseInt(courseIdText)) != null) {
                JOptionPane.showMessageDialog(this, "Course with this ID already exists.");
                return;
            }
            
            if (!courseName.matches("^[a-zA-Z]+$")) {
                JOptionPane.showMessageDialog(this, "Invalid Course name. Please enter alphabetic characters only.");
                return;
            }
            // Inside the method where you handle the form submission
            //String courseCredits = courseCreditsField.getText();
            // Inside the method where you handle the form submission
            String courseD = courseDescriptionField.getText();

            if (!courseD.matches("^(?!\\s*$).+")) {
                JOptionPane.showMessageDialog(this, "Invalid course description. It cannot be empty or consist only of spaces.");
                return;
            }

            if (!creditsText.matches("^[1-5]$")) {
                JOptionPane.showMessageDialog(this, "Invalid course credits. Credits must be between 1 and 5.");
                return;
            }          
           
            // Validate input
            if (courseIdText.isEmpty() || courseName.isEmpty() || courseDescription.isEmpty() || creditsText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate numeric input
//            int courseId;
//            double credits_;
//            try {
//                int courseId = Integer.parseInt(courseIdText);
//               double  credits_ = Double.parseDouble(creditsText);
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(this, "Course ID and Credits must be numeric.", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
            int courseId = Integer.parseInt(courseIdText);
            double  credits_ = Double.parseDouble(creditsText);

            // Create new Course object
            Course course = new Course();
            course.setCourseId(courseId);
            course.setCourseName(courseName);
            course.setCourseDescription(courseDescription);
            course.setCredits(credits_);

            // Call service to add course
            courseService.addCourse(course);

            // Refresh table and clear fields
            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(this, "Course added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        	 } catch (Exception ex) {
                 JOptionPane.showMessageDialog(this, "Enter fileds Error: " + ex.getMessage());
             }}
        	);

        deleteButton.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                int courseId = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this course?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    courseService.deleteCourse(courseId);
                    JOptionPane.showMessageDialog(this, "Course deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            
            if (selectedRow != -1)
            	try {{
            		int course_Id = (int) tableModel.getValueAt(selectedRow, 0);
                String courseIdText = courseIdField.getText();
                String courseName = courseNameField.getText();
                String courseDescription = courseDescriptionField.getText();
                String creditsText = creditsField.getText();
                
                
                if (!courseName.matches("^[a-zA-Z]+$")) {
                    JOptionPane.showMessageDialog(this, "Invalid first name. Please enter alphabetic characters only.");
                    return;
                }
                if (!courseDescription.matches("^(?!\\s*$).+")) {
                    JOptionPane.showMessageDialog(this, "Invalid course description. It cannot be empty or consist only of spaces.");
                    return;
                }
                // Validate input
                if ( courseName.isEmpty() || courseDescription.isEmpty() || creditsText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
//courseIdText.isEmpty() ||
                int courseId;
                double credits;
                try {
                    courseId = Integer.parseInt(courseIdText);
                    credits = Double.parseDouble(creditsText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Course ID and Credits must be numeric.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create updated Course object
                Course course = new Course();
                course.setCourseId(course_Id);
                course.setCourseName(courseName);
                course.setCourseDescription(courseDescription);
                course.setCredits(credits);

                // Call service to update course
                courseService.updateCourse(course);

                // Refresh table and clear fields
                refreshTable();
                clearFields();

                JOptionPane.showMessageDialog(this, "Course updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }} catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonsPanel.add(saveButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(updateButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Course ID", "Course Name", "Description", "Credits"};
        tableModel = new DefaultTableModel(columnNames, 0);
        courseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        setVisible(true);
        refreshTable();
    }

    private void clearFields() {
        courseIdField.setText("");
        courseNameField.setText("");
        courseDescriptionField.setText("");
        creditsField.setText("");
    }

    private void refreshTable() {
        List<Course> courses = courseService.getAllCourses();
        tableModel.setRowCount(0); // Clear the existing rows
        for (Course course : courses) {
            Object[] rowData = {
                course.getCourseId(),
                course.getCourseName(),
                course.getCourseDescription(),
                course.getCredits()
            };
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CourseForm());
    }
}
