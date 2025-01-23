package Service;

import Model.Course;
import Model.Grade;
import Model.Student;
import dao.StudentDAO;
import java.util.List;

public class StudentService {
    private StudentDAO studentDAO = new StudentDAO();

    public void addStudent(Student student) {
        studentDAO.addStudent(student);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    public void updateStudent(Student student) {
        studentDAO.updateStudent(student);
    }

    public void deleteStudent(int studentId) {
        studentDAO.deleteStudent(studentId);
    }
    public Student getStudentById(int studentId) {
        return studentDAO.getStudentById(studentId);
    }
    public List<Grade> getGradesByStudentId(int studentId) {
        return studentDAO.getGradesByStudentId(studentId);
    }

    public Course getCourseById(int courseId) {
        return studentDAO.getCourseById(courseId);
    }
    
}
