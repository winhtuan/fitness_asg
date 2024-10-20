package service;

import Model.Course;
import Model.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import reponsitory.CourseRepository;
import reponsitory.TrainingRepository;
import view.Validation;

public class CourseService implements ICourseService {

    private Map<String, List<String>> userCourseMap;
    private List<Course> listCourse;
    private CourseRepository courseRepository;
    private TrainingRepository trainingRepository;
    private Validation check;
    private UserService userService;

    public CourseService() {
        courseRepository = new CourseRepository();
        trainingRepository = new TrainingRepository();
        listCourse = courseRepository.readFile();
        userCourseMap = trainingRepository.readFile();
        userService = new UserService();
        check = new Validation();

    }

    public static void main(String[] args) {
        CourseService courseService = new CourseService();
        courseService.displayUserCourses("CUS-0001");
        courseService.displayUserCourses("CUS-0003");
        courseService.displayUserCourses("CUS-0002");
    }

    public void registerUserToCourse() {
        String response = check.getInputString("Are you a new user (yes/no)? ", "^(yes|no)", "This Respone");
        String userId = null;
        Users user;
        if (response.equalsIgnoreCase("yes")) {
            user = userService.registNewUser();
            userId = user.getId();
        } else {
            userId = check.getID("Enter ID: ", USER_REGEX, "User ID");
            user = userService.findByID(userId);
            if (user == null) {
                System.out.println("Error! System can't find users: " + userId);
                return;
            }
        }
        display();
        do {
            String courseId = check.getInputString("Enter course ID to register: ", COURSE_REGEX, "Course ID");
            registCourse(courseId, user);
        } while (check.continueConfirm("Do you want to continue regis course"));
        displayUserCourses(userId);
    }

    @Override
    public void registCourse(String courseID, Users user) {
        Course targetCourse = findByID(courseID);
        if (targetCourse == null) {
            System.out.println("Invalid course. Please try again.");
            return;
        }
        userCourseMap.putIfAbsent(user.getId(), new ArrayList<>());
        userCourseMap.get(user.getId()).add(courseID);
        System.out.println("Course " + courseID + "with coach" + targetCourse.getCourseId() + " registered for user " + user.getName());
        trainingRepository.writeFile(userCourseMap);
    }

    @Override
    public void display() {
        System.out.println("List All Courses:");
        System.out.printf("%-10s | %-30s | %-15s | %-20s\n", "Course ID", "Course Name", "Price", "Decription");
        System.out.println("-".repeat(60));
        for (Course course : listCourse) {
            System.out.printf("%-10s | %-30s | $%-15.2f | %-20s\n", course.getCourseId(), course.getCourseName(),
                    course.getPrice(), course.getWorkout().getDescription());
        }
        System.out.println("-".repeat(60));
    }

    @Override
    public Course findByID(String id) {
        for (Course course : listCourse) {
            if (course.getCourseId().equalsIgnoreCase(id)) {
                return course;
            }
        }
        return null;
    }

    private void displayUserCourses(String userId) {
        List<String> userCourses = userCourseMap.get(userId);
        if (userCourses == null || userCourses.isEmpty()) {
            System.out.println("User " + userId + " has not registered for any courses.");
            return;
        }

        System.out.println("Courses registered by user " + userId + ":");
        for (String courseId : userCourses) {
            Course course = findByID(courseId);
            if (course != null) {
                System.out.printf("%-10s | %-30s | $%-15.2f | %-20s\n", course.getCourseId(), course.getCourseName(),
                        course.getPrice(), course.getWorkout().getDescription());
            } else {
                System.out.println("Course with ID " + courseId + " not found.");
            }
        }
    }

    public Map<String, List<String>> getUserCourseMap() {
        return userCourseMap;
    }

}
