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

public class ProgressService implements IProgressService{

    private List<Progress> allProgresses;
    private List<Course> listCourse;
    private CourseService courseService;
    private Validation check;

    public ProgressService() {
        courseService = new CourseService();
        listCourse = new CourseRepository().readFile();
        check = new Validation();
        allProgresses = new ProgressRepository().readFile();
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
        courseService.displayUserCourses(selectedUserId);
        String selectedCourseID = check.getID("Enter the courseID: ", "COR-\\d{4}", "Course ID");
        viewUserProgress(selectedUserId, selectedCourseID);
    }

    @Override
    public void viewUserProgress(String userId, String courseID) {
        List<Course> userCourses = new RegisteringRepository(courseService).readFile().get(userId);
        if (userCourses == null || userCourses.isEmpty()) {
            System.out.println("No courses found for user " + userId + ".");
            return;
        }

        System.out.println("Progress Evaluation for user " + userId + ":");
        Progress userProgress = allProgresses.stream()
                .filter(up -> up.getUserID().equals(userId) && up.getCourseID().equals(courseID))
                .findFirst()
                .orElse(null);

        if (userProgress != null) {
            Map<LocalDate, Map<String, Boolean>> progressMap = userProgress.getProgress();

            if (progressMap.isEmpty()) {
                System.out.println("No exercises recorded for this course.");
                return;
            }

            int totalExercises = 0;
            int completedExercises = 0;

            for (Map<String, Boolean> exerciseMap : progressMap.values()) {
                for (Boolean status : exerciseMap.values()) {
                    totalExercises++;
                    if (status) {
                        completedExercises++;
                    }
                }
            }

            double progressPercentage = (totalExercises > 0) ? ((double) completedExercises / totalExercises) * 100 : 0;

            System.out.println("Progress for User: " + userId + " in Course: " + courseID);
            System.out.println("Total Exercises: " + totalExercises);
            System.out.println("Completed Exercises: " + completedExercises);
            System.out.printf("Completion: %.2f%%\n", progressPercentage);
        } else {
            System.out.println("No progress found for user " + userId + " in course " + courseID);
        }
    }

    @Override
    public void updateUserProgress(String userId, String courseID) {
        
    }
}
