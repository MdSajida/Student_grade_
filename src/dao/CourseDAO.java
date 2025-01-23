package dao;

import Model.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // Method to add a course
    public void addCourse(Course course) {
        String sql = "INSERT INTO Courses_ (course_id,course_name, course_description, credits) VALUES (?,?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, course.getCourseId());
        	pstmt.setString(2, course.getCourseName());
            pstmt.setString(3, course.getCourseDescription());
            pstmt.setDouble(4, course.getCredits());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM courses_")) {
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourseId(resultSet.getInt("course_id"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setCourseDescription(resultSet.getString("course_description"));
                course.setCredits(resultSet.getDouble("credits"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    // Method to update a course
    public void updateCourse(Course course) {
        String sql = "UPDATE Courses_ SET course_name = ?, course_description = ?, credits = ? WHERE course_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getCourseDescription());
            pstmt.setDouble(3, course.getCredits());
            pstmt.setInt(4, course.getCourseId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a course
    public void deleteCourse(int courseId) {
        String sql = "DELETE FROM Courses_ WHERE course_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Course getCourseById(int courseId) {
        Course course = null;
        try {
        	Connection conn = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM courses_ WHERE course_id = ?");
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                course = new Course();
                course.setCourseId(resultSet.getInt("course_id"));
                course.setCourseName(resultSet.getString("course_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }
    
}
