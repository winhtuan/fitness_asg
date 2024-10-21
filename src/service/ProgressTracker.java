package service;

import Model.Course;
import Model.Users;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Coach;
import model.Progress;
import reponsitory.CourseRepository;
import reponsitory.ProgressRepository;
import reponsitory.RegisteringRepository;
import view.Validation;

public class ProgressTracker {

    private RegisteringRepository registeringRepository;
    private List<Progress> allProgresses;
    private List<Course> listCourse;
    private CourseService courseService;
    private Validation check;

    public ProgressTracker() {
        registeringRepository = new RegisteringRepository();
        courseService = new CourseService();
        listCourse = new CourseRepository().readFile();
        check = new Validation();
        allProgresses = new ProgressRepository().readFile();
    }

    public static void main(String[] args) {
        new ProgressTracker().displayUsersByCoach();

    }

    public void displayUsersByCoach() {
        List<String> registeredUsers = new ArrayList<>();
        String coachId = check.getID("Enter your Coach ID: ", "COA-\\d{4}", "Coach ID");
        Coach coach = new CoachService().findByID(coachId);
        if (coach == null) {
            System.out.println("Coach ID not found. Please try again.");
            return;
        }
        for (Course course : listCourse) {
            if (course.getCoachID().equalsIgnoreCase(coachId)) {
                for (Map.Entry<String, List<Course>> entry : courseService.getListRegistering().entrySet()) {
                    for (Course registeredCourse : entry.getValue()) {
                        if (registeredCourse.getCourseId().equals(course.getCourseId())) {
                            registeredUsers.add(entry.getKey());
                        }
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
        String selectedUserId = check.getID("Enter the user ID to view progress: ", "CUS-\\d{4}", "User ID");
        viewUserProgress(selectedUserId);
    }

    public void viewUserProgress(String userId) {
        List<Course> userCourses = new RegisteringRepository(courseService).readFile().get(userId);
        if (userCourses == null || userCourses.isEmpty()) {
            System.out.println("No courses found for user " + userId + ".");
            return;
        }
        courseService.displayUserCourses(userId);

        String courseID = check.getID("Enter the courseID: ", "COR-\\d{4}", "Course ID");
        Course course = courseService.findByID(courseID);

        System.out.println("Progress Evaluation for user " + userId + ":");
        Progress userProgress = allProgresses.stream()
                .filter(up -> up.getUserID().equals(userId) && up.getCourseID().equals(courseID))
                .findFirst()
                .orElse(null);

        if (userProgress != null) {
            int completedCount = 0;
            int totalExercises = 0;

            // Count distinct exercises and completed exercises
            for (Map.Entry<LocalDate, Map<String, Boolean>> entry : userProgress.getProgress().entrySet()) {
                Map<String, Boolean> exercises = entry.getValue();
                totalExercises += exercises.size(); // Count all exercises for the date
                completedCount += (int) exercises.values().stream().filter(Boolean::booleanValue).count(); // Count completed exercises
            }

            double progressPercentage = (totalExercises > 0) ? (double) completedCount / totalExercises * 100 : 0;

            System.out.printf("Course ID: %s, Course Name: %s, Progress: %.2f%% (%d/%d exercises completed)\n",
                    course.getCourseId(), course.getCourseName(), progressPercentage, completedCount, totalExercises);
        } else {
            System.out.println("No progress found for user " + userId + " in course " + courseID);
        }
    }
}
