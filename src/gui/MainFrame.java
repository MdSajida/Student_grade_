package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public MainFrame() {
        setTitle("Main Frame");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI components
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        JButton studentButton = new JButton("Student Form");
        studentButton.setPreferredSize(new Dimension(150, 40)); // Set preferred size
        studentButton.setBackground(new Color(144, 202, 249)); // Light blue background
        studentButton.setForeground(Color.BLACK); // Set text color to black
        studentButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font

        JButton gradeButton = new JButton("Grade Form");
        gradeButton.setPreferredSize(new Dimension(150, 40)); // Set preferred size
        gradeButton.setBackground(new Color(250, 192, 144)); // Light orange background
        gradeButton.setForeground(Color.BLACK); // Set text color to black
        gradeButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font

        JButton courseButton = new JButton("Course Form");
        courseButton.setPreferredSize(new Dimension(150, 40)); // Set preferred size
        courseButton.setBackground(new Color(182, 230, 176)); // Light green background
        courseButton.setForeground(Color.BLACK); // Set text color to black
        courseButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font

        // Add action listeners to buttons
        studentButton.addActionListener(e -> new StudentForm());
        gradeButton.addActionListener(e -> new GradeForm());
        courseButton.addActionListener(e -> new CourseForm());

        // Add buttons to panel
        panel.add(studentButton);
        panel.add(gradeButton);
        panel.add(courseButton);

        // Add panel to frame
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}