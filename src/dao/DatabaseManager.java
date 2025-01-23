package dao;

import Model.Grade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private Connection conn;

 
 // Database connection details for MySQL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_mysql_username";
    private static final String PASSWORD = "your_mysql_password";


    public DatabaseManager() {
        conn = getConnection();
         // Ensure the table exists when the DatabaseManager is instantiated
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to PostgreSQL database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }



    // CRUD operations for all_grades table

    public void addGrade(Grade grade) {
        String sql = "INSERT INTO all_grades (student_id, course_id, grade) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, grade.getStudentId());
            pstmt.setInt(2, grade.getCourseId());
            pstmt.setInt(3, grade.getGrade());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                grade.setGradeId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGrade(Grade grade) {
        String sql = "UPDATE all_grades SET student_id = ?, course_id = ?, grade = ? WHERE grade_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, grade.getStudentId());
            pstmt.setInt(2, grade.getCourseId());
            pstmt.setInt(3, grade.getGrade());
            pstmt.setInt(4, grade.getGradeId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteGrade(int gradeId) {
        String sql = "DELETE FROM all_grades WHERE grade_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gradeId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM all_grades";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeId(rs.getInt("grade_id"));
                grade.setStudentId(rs.getInt("student_id"));
                grade.setCourseId(rs.getInt("course_id"));
                grade.setGrade(rs.getInt("gradep"));
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public Grade getGradeById(int gradeId) {
        Grade grade = null;
        String sql = "SELECT * FROM all_grades WHERE grade_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, gradeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    grade = new Grade();
                    grade.setGradeId(rs.getInt("grade_id"));
                    grade.setStudentId(rs.getInt("student_id"));
                    grade.setCourseId(rs.getInt("course_id"));
                    grade.setGrade(rs.getInt("grade"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grade;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Disconnected from PostgreSQL database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
