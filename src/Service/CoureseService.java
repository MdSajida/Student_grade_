package Service;

import Model.Course;
import dao.CourseDAO;
import java.util.List;

public class CoureseService {
    private CourseDAO courseDAO = new CourseDAO();

    public void addCourse(Course course) {
        courseDAO.addCourse(course);
    }

    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    public void updateCourse(Course course) {
        courseDAO.updateCourse(course);
    }

    public void deleteCourse(int courseId) {
        courseDAO.deleteCourse(courseId);
    }
    public Course getCourseById(int courseId) {
    	 return courseDAO.getCourseById(courseId);
    }
}
