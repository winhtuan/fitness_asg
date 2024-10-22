package service;

import Model.Course;
import Model.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Coach;
import reponsitory.CourseRepository;
import reponsitory.RegisteringRepository;
import static service.Service.COURSE_REGEX;
import static service.Service.USER_REGEX;
import view.Validation;

public class CourseService implements ICourseService {

    private List<Course> listCourse;
    private Map<String, List<Course>> listRegistering;
    private CourseRepository courseRepository;
    private RegisteringRepository registeringRepository;
    private UserService userService;
    private Validation check;

    public CourseService() {
        courseRepository = new CourseRepository();
        registeringRepository = new RegisteringRepository(this);
        listCourse = courseRepository.readFile();
        userService = new UserService();
        check = new Validation();
        listRegistering = registeringRepository.readFile();
    }

    public Map<String, List<Course>> getListRegistering() {
        return listRegistering;
    }

    public void registerUserToCourse() {
        String response = check.getInputString("Are you a new user (yes/no)? ", "^(yes|no)", "This Response");
        String userId = null;
        Users user;

        if (response.equalsIgnoreCase("yes")) {
            user = userService.registNewUser();
            userId = user.getId();
        } else {
            userId = check.getID("Enter ID: ", USER_REGEX, "User ID");
            user = userService.findByID(userId);
            if (user == null) {
                System.out.println("Error! System can't find user with ID: " + userId);
                return;
            }
        }

        displayMoreDetail();
        do {
            String courseId = check.getInputString("Enter course ID to register: ", COURSE_REGEX, "Course ID");
            Course course = findByID(courseId);
            if (course != null) {
                registerCourse(user, course);
            } else {
                System.out.println("Course with ID " + courseId + " not found.");
            }
        } while (check.continueConfirm("Do you want to continue registering for courses?"));
        displayUserCourses(userId);
    }

    @Override
    public void registerCourse(Users user, Course course) {
        listRegistering.putIfAbsent(user.getId(), new ArrayList<>());
        listRegistering.get(user.getId()).add(course);
        System.out.println("Course " + course.getCourseId() + " with coach " + course.getCoachID() + " registered for user " + user.getName());
        registeringRepository.writeFile(listRegistering);
    }

    public void displayUserCourses(String userId) {
        List<Course> userCourses = listRegistering.get(userId);
        if (userCourses == null || userCourses.isEmpty()) {
            System.out.println("User " + userId + " has not registered for any courses.");
            return;
        }
        System.out.println("Courses registered by user " + userId + ":");
        for (Course course : userCourses) {
            Coach coach = new CoachService().findByID(course.getCoachID());
            System.out.printf("%-10s | %-30s |  %-30s | $%-15.2f | %-20s\n",
                    course.getCourseId(),
                    course.getCourseName(),
                    coach.getName(),
                    course.getPrice(),
                    course.getWorkout().getDescription());
        }
    }

    // display for guest 
    @Override
    public void display() {
        System.out.println("List All Courses:");
        System.out.printf("%-10s | %-30s | %-15s | %-20s\n", "Course ID", "Course Name", "Price", "Description");
        System.out.println("-".repeat(60));
        for (Course course : listCourse) {
            System.out.printf("%-10s | %-30s | $%-15.2f | %-20s\n",
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getPrice(),
                    course.getWorkout().getDescription());
        }
        System.out.println("-".repeat(60));
    }
    // display for user
    @Override
    public void displayMoreDetail() {
        System.out.printf("%-12s | %-15s | %-8s | %-10s | %-8s |%n",
                "Course ID", "Course Name", "Price", "Time", "Coach ID");
        System.out.println("-".repeat(80));
        for (Course course : listCourse) {
            System.out.println(course);
            System.out.println("-".repeat(80));
        }
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

}
