package Model;

public class Course {
    private int courseId;
    private String courseName;
    private String courseDescription;
    private Double credits;
    
  
    // Getters and setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }
    @Override
   public String toString() {
        return "Course ID: " + courseId + ", Name: " + courseName + ", Description: " + courseDescription + ", Credits: " + credits;
    }
   
}

