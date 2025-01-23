package gui;

import Model.Grade;
import Service.GradeService;
import Service.StudentService;
import Service.CoureseService;
import Model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GradeForm extends JFrame {
    private static final long serialVersionUID = 1L;
    private GradeService gradeService;
    private StudentService studentService;
    private CoureseService courseService;
    private JTextField studentIdField;
    private JComboBox<Course> courseIdComboBox;
    private JTextField gradeField;
    private JTable gradeTable;
    private DefaultTableModel tableModel;

    public GradeForm() {
        gradeService = new GradeService();
        studentService = new StudentService();
        courseService = new CoureseService();

        setTitle("Grade Form");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Create UI components
        JPanel formPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for precise control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Padding between components

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdField = new JTextField(10); // Set preferred width
        JLabel courseIdLabel = new JLabel("Course  ID:");
        courseIdComboBox = new JComboBox<>(); // Dropdown for Course ID
        JLabel gradeLabel = new JLabel("Grade:");
        gradeField = new JTextField(10); // Set preferred width

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(studentIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(studentIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(courseIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(courseIdComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(gradeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(gradeField, gbc);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton saveButton = new JButton("Add");
        saveButton.setPreferredSize(new Dimension(80, 30));
        saveButton.setBackground(new Color(144, 202, 249));
        saveButton.setForeground(Color.BLACK);
        saveButton.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(80, 30));
        deleteButton.setBackground(new Color(250, 192, 144));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton updateButton = new JButton("Update");
        updateButton.setPreferredSize(new Dimension(80, 30));
        updateButton.setBackground(new Color(182, 230, 176));
        updateButton.setForeground(Color.BLACK);
        updateButton.setFont(new Font("Arial", Font.PLAIN, 14));

        saveButton.addActionListener(e -> {
            // Validate that none of the fields are empty
            if (studentIdField.getText().isEmpty() || courseIdComboBox.getSelectedItem() == null || gradeField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int studentId;
            int courseId;
            int gradeValue;

            try {
                studentId = Integer.parseInt(studentIdField.getText());
                courseId = ((Course) courseIdComboBox.getSelectedItem()).getCourseId();
                gradeValue = Integer.parseInt(gradeField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Student ID and Grade must be numeric.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the student ID exists
            if (studentService.getStudentById(studentId) == null) {
                JOptionPane.showMessageDialog(this, "Student ID does not exist.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate that the grade is within a valid range
            if (gradeValue < 0 || gradeValue > 10) {
                JOptionPane.showMessageDialog(this, "Grade must be between 0 and 10.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Check if the student already has a grade for this course
            if (gradeService.getGradeByStudentAndCourse(studentId, courseId) != null) {
                JOptionPane.showMessageDialog(this, "This student already has a grade for this course.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Grade grade = new Grade();
            grade.setStudentId(studentId);
            grade.setCourseId(courseId);
            grade.setGrade(gradeValue);
            gradeService.addGrade(grade);
            JOptionPane.showMessageDialog(this, "Grade saved successfully!");
            clearFields();
            refreshTable();
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = gradeTable.getSelectedRow();
            if (selectedRow != -1) {
                int studentId = (int) tableModel.getValueAt(selectedRow, 0);
                int courseId = (int) tableModel.getValueAt(selectedRow, 1);
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this grade?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    gradeService.deleteGrade(studentId, courseId);
                    JOptionPane.showMessageDialog(this, "Grade deleted successfully!");
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a grade to delete.");
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = gradeTable.getSelectedRow();
            if (selectedRow != -1) {
                int studentId;
                int courseId;
                int gradeValue;

                try {
                    studentId = Integer.parseInt(studentIdField.getText());
                    courseId = ((Course) courseIdComboBox.getSelectedItem()).getCourseId();
                    gradeValue = Integer.parseInt(gradeField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Student ID and Grade must be numeric.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate that none of the fields are empty
                //studentIdField.getText().isEmpty() ||
                if ( courseIdComboBox.getSelectedItem() == null || gradeField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if the student ID exists
                if (studentService.getStudentById(studentId) == null) {
                    JOptionPane.showMessageDialog(this, "Student ID does not exist.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate that the grade is within a valid range
                if (gradeValue < 0 || gradeValue > 10) {
                    JOptionPane.showMessageDialog(this, "Grade must be between 0 and 10.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Grade grade = new Grade();
                grade.setStudentId(studentId);
                grade.setCourseId(courseId);
                grade.setGrade(gradeValue);
                gradeService.updateGrade(grade);
                JOptionPane.showMessageDialog(this, "Grade updated successfully!");
                clearFields();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a grade to update.");
            }
        });

        buttonsPanel.add(saveButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(updateButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Student ID", "Course ID", "Grade"};
        tableModel = new DefaultTableModel(columnNames, 0);
        gradeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(gradeTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        setVisible(true);
        refreshTable();
        refreshCourseList();
    }

    private void clearFields() {
        studentIdField.setText("");
        courseIdComboBox.setSelectedIndex(-1); // Deselect current selection
        gradeField.setText("");
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // Clear the existing rows
        for (Grade grade : gradeService.getAllGrades()) {
            Object[] rowData = {grade.getStudentId(), grade.getCourseId(), grade.getGrade()};
            tableModel.addRow(rowData);
        }
    }

    private void refreshCourseList() {
        courseIdComboBox.removeAllItems();
        List<Course> courses = courseService.getAllCourses();
        for (Course course : courses) {
            courseIdComboBox.addItem(course);
        }
    }
}
