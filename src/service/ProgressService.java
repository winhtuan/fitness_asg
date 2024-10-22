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

public class ProgressService implements IProgressService {

    private List<Progress> allProgresses;
    private List<Course> listCourse;
    private CourseService courseService;
    private Validation check;

    public ProgressService() {
        courseService = new CourseService();
        check = new Validation();
        allProgresses = new ProgressRepository().readFile();
        listCourse = new CourseRepository().readFile();
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

            System.out.println("Available workout dates and statuses for user " + userId + ":");
            for (Map.Entry<LocalDate, Map<String, Boolean>> entry : progressMap.entrySet()) {
                LocalDate date = entry.getKey();
                Map<String, Boolean> statusMap = entry.getValue();
                System.out.print("Date: " + date.format(DATE_FORMAT));
                for (Map.Entry<String, Boolean> statusEntry : statusMap.entrySet()) {
                    String exercise = statusEntry.getKey();
                    Boolean status = statusEntry.getValue();
                    System.out.println(" - Exercise: " + exercise + " - Status: " + (status ? "Completed" : "Not Completed"));
                }
            }

            LocalDate inputDate = check.getDate("Enter the date to update status: ");
            if (progressMap.containsKey(inputDate)) {
                Map<String, Boolean> statusMap = progressMap.get(inputDate);
                System.out.print("Exercises for date " + inputDate.format(DATE_FORMAT) + ":");
                for (String exercise : statusMap.keySet()) {
                    System.out.println("Exercise: " + exercise + " - Status: " + (statusMap.get(exercise) ? "Completed" : "Not Completed"));
                }

                for (String exercise : statusMap.keySet()) {
                    System.out.print("Update status for " + exercise);
                    boolean newStatus = check.getBoolean("Enter the status (true/false): ");
                    statusMap.put(exercise, newStatus);
                }

                System.out.println("Status updated successfully for date " + inputDate + ".");
                new ProgressRepository().writeFile(allProgresses);
            } else {
                System.out.println("No progress found for the entered date: " + inputDate);
            }
        } else {
            System.out.println("No progress found for user " + userId + " in course " + courseID);
        }
        
    }

    public static void main(String[] args) {
        new ProgressService().updateUserProgress("CUS-0001", "COR-0001");
    }

    @Override
    public void display() {
        List<String> registeredUsers = new ArrayList<>();
        String coachId = check.getID("Enter your Coach ID: ", COACH_REGEX, "Coach ID");
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
        String selectedUserId = check.getID("Enter the user ID to view progress: ", USER_REGEX, "User ID");
        courseService.displayUserCourses(selectedUserId);
        String selectedCourseID = check.getID("Enter the courseID: ", COURSE_REGEX, "Course ID");
        viewUserProgress(selectedUserId, selectedCourseID);
    }

    @Override
    public Progress findByID(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
