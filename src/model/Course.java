package Model;

public class Course {

    private String courseId;
    private String courseName;
    private double price;
    private String time;
    private String coachID;
    private Workout workout;

    public Course() {
    }

    public Course(String courseId, String courseName, double price, String time, String coach, Workout workout) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.price = price;
        this.time = time;
        this.coachID = coach;
        this.workout = workout;
    }

    @Override
    public String toString() {
        return String.format("%-12s | %-15s | %-8.2f | %-10s | %-8s | \n%s",
                courseId, courseName, price, time, coachID, workout.toString());
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCoachID() {
        return coachID;
    }

    public void setCoachID(String coachID) {
        this.coachID = coachID;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public int getExerciseCount() {
        return workout.getExercise().size();
    }
}
