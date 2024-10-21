package reponsitory;

import Model.Course;
import Model.Users;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import service.CourseService;

public class RegisteringRepository implements Reponsitory<Users, Map<String, List<Course>>> {

    final String PATH = new File("src\\data\\registering.csv").getAbsolutePath();
    private CourseService courseService;

    public RegisteringRepository() {
    }

    public RegisteringRepository(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public Map<String, List<Course>> readFile() {
        Map<String, List<Course>> userCourse = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2) {
                    String userId = values[0].trim();
                    String courseId = values[1].trim();
                    Course course = courseService.findByID(courseId);
                    userCourse.putIfAbsent(userId, new ArrayList<>());
                    userCourse.get(userId).add(course);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + PATH + ".");
        }
        return userCourse;
    }

    @Override
    public void writeFile(Map<String, List<Course>> entities) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            for (Map.Entry<String, List<Course>> entry : entities.entrySet()) {
                String userId = entry.getKey();
                List<Course> courses = entry.getValue();

                for (Course course : courses) {
                    bw.write(userId + "," + course.getCourseId());
                    bw.newLine();
                }
            }
        } catch (IOException ex) {
            System.out.println("Error writing file: " + PATH + ".");
        }
    }

}
