package Service;

import Model.Grade;
import dao.GradeDAO;
import java.util.List;

public class GradeService {
    private GradeDAO gradeDAO = new GradeDAO();

    public void addGrade(Grade grade) {
        gradeDAO.addGrade(grade);
    }

    public List<Grade> getAllGrades() {
        return gradeDAO.getAllGrades();
    }

    public void updateGrade(Grade grade) {
        gradeDAO.updateGrade(grade);
    }

    public void deleteGrade(int studentId, int courseId) {
        gradeDAO.deleteGrade(studentId, courseId);
    }
    public Grade getGradeByStudentAndCourse(int studentId, int courseId) {
        return gradeDAO.getGradeByStudentAndCourse(studentId, courseId);
    }
}
