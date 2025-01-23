package dao;

import Model.Grade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {
   

    // Method to add a grade
    public void addGrade(Grade grade) {
        String sql = "INSERT INTO Grades_ (student_id, course_id, gradep) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, grade.getStudentId());
            pstmt.setInt(2, grade.getCourseId());
            pstmt.setInt(3, grade.getGrade());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get all grades
    public List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM Grades_";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Grade grade = new Grade();
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

    // Method to update a grade
    public void updateGrade(Grade grade) {
        String sql = "UPDATE Grades_ SET gradep = ? WHERE student_id = ? AND course_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, grade.getGrade());
            pstmt.setInt(2, grade.getStudentId());
            pstmt.setInt(3, grade.getCourseId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a grade
    public void deleteGrade(int student_id, int course_id) {
        String sql = "DELETE FROM Grades_ WHERE student_id = ? AND course_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, student_id);
            pstmt.setInt(2, course_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Grade getGradeByStudentAndCourse(int studentId, int courseId) {
        String query = "SELECT * FROM Grades_ WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
        	PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Grade grade = new Grade();
                grade.setGradeId(rs.getInt("grade_id"));
                grade.setStudentId(rs.getInt("student_id"));
                grade.setCourseId(rs.getInt("course_id"));
                grade.setGrade(rs.getInt("gradep"));
                return grade;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
