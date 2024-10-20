package service;

import Model.Course;
import Model.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Coach;
import reponsitory.CourseRepository;
import reponsitory.TrainingRepository;
import view.Validation;

public class ProgressTracker {

    private TrainingRepository trainingRepository;
    private List<Course> listCourse;
    private List<Coach> listCoach;
    private CourseService courseService;
    private Validation check;

    public ProgressTracker() {
        trainingRepository = new TrainingRepository();
        courseService = new CourseService();
        listCourse = new CourseRepository().readFile();
        check = new Validation();
    }

    public static void main(String[] args) {

    }

    public void displayUsersByCoach(String coachId) {
        List<String> registeredUsers = new ArrayList<>();
        for (Course course : listCourse) { 
            if (course.getCoachID().equalsIgnoreCase(coachId)) {
                for (Map.Entry<String, List<String>> entry : courseService.getUserCourseMap().entrySet()) {
                    if (entry.getValue().contains(course.getCourseId())) { 
                        registeredUsers.add(entry.getKey()); 
                    }
                }
            }
        }
        if (registeredUsers.isEmpty()) {
            System.out.println("No users found for coach ID: " + coachId);
            return;
        }
        System.out.println("Users registered with coach ID " + coachId + ":");
        for (String userId : registeredUsers) {
            Users user = new UserService().findByID(userId);
            if (user != null) {
                System.out.println("User ID: " + user.getId() + ", Name: " + user.getName());
            } 
        }
        String selectedUserId = check.getInputString("Enter the user ID to view progress: ", "CUS-\\d{4}", "User ID");
        viewUserProgress(selectedUserId);
    }

    public void viewUserProgress(String userId) {
        List<String> userCourses = courseService.getUserCourseMap().get(userId);
        if (userCourses == null || userCourses.isEmpty()) {
            System.out.println("No courses found for user " + userId + " to evaluate progress.");
            return;
        }
        System.out.println("Progress Evaluation for user " + userId + ":");

        for (String courseId : userCourses) {
            Course course = courseService.findByID(courseId);
            if (course != null) {
                Map<String, Boolean> exercises = trainingRepository.readUserProgress(course, userId);
                int completedCount = (int) exercises.values().stream().filter(Boolean::booleanValue).count();
                int totalExercises = exercises.size();
                double progressPercentage = (double) completedCount / totalExercises * 100;
                System.out.printf("Course ID: %s, Progress: %.2f%% (%d/%d exercises completed)\n",
                        courseId, progressPercentage, completedCount, totalExercises);
            } else {
                System.out.println("Course with ID " + courseId + " not found.");
            }
        }
    }

    public void updateUserProgress(String userId, String courseId) {
        Course course = courseService.findByID(courseId);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        Map<String, Boolean> exercises = trainingRepository.readUserProgress(course, userId);

        System.out.println("Current progress:");
        for (Map.Entry<String, Boolean> entry : exercises.entrySet()) {
            System.out.println(entry.getKey() + " - " + (entry.getValue() ? "Completed" : "Not Completed"));
        }

        for (String exercise : exercises.keySet()) {
            boolean newStatus = check.getBoolean("Mark " + exercise + " as completed? (true/false): ");
            exercises.put(exercise, newStatus);
        }

        trainingRepository.writeUserProgress(course, userId, exercises);
        System.out.println("Progress updated successfully.");
    }

}
