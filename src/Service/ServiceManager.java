package Service;

import dao.DatabaseManager;
import Model.Grade;
import java.util.List;

public class ServiceManager {
    private DatabaseManager databaseManager;

    public ServiceManager() {
        databaseManager = new DatabaseManager();
    }

    public void addGrade(int studentId, int courseId, int grade) {
        Grade newGrade = new Grade(studentId, courseId, grade);
        databaseManager.addGrade(newGrade);
    }

    public void updateGrade(int gradeId, int studentId, int courseId, int grade) {
        Grade updatedGrade = new Grade(studentId, courseId, grade);
        updatedGrade.setGradeId(gradeId);
        databaseManager.updateGrade(updatedGrade);
    }

    public void deleteGrade(int gradeId) {
        databaseManager.deleteGrade(gradeId);
    }

    public List<Grade> getAllGrades() {
        return databaseManager.getAllGrades();
    }

    public Grade getGradeById(int gradeId) {
        return databaseManager.getGradeById(gradeId);
    }

    public void close() {
        databaseManager.closeConnection();
    }
}
