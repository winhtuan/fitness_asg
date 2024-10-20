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

public class TrainingRepository implements Reponsitory<Users, Map<String, List<String>>> {

    final String PATH = new File("src\\data\\training.csv").getAbsolutePath();
    final String PATH_PROGRESS = new File("src\\data\\progress.csv").getAbsolutePath();

    @Override
    public Map<String, List<String>> readFile() {
        Map<String, List<String>> userCourse = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2) {
                    String userId = values[0].trim();
                    String courseId = values[1].trim();
                    userCourse.putIfAbsent(userId, new ArrayList<>());
                    userCourse.get(userId).add(courseId);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + PATH + ".");
        }

        return userCourse;
    }

    @Override
    public void writeFile(Map<String, List<String>> entities) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            for (Map.Entry<String, List<String>> entry : entities.entrySet()) {
                String userId = entry.getKey();
                List<String> courses = entry.getValue();
                for (String courseId : courses) {
                    Course course = new CourseService().findByID(courseId);
                    bw.write(userId + "," + courseId + "," + course.getCoachID());
                    bw.newLine();
                }
            }
        } catch (IOException ex) {
            System.out.println("Error writing file: " + PATH + ".");
        }
    }

    public Map<String, Boolean> readUserProgress(Course course, String userId) {
        Map<String, Boolean> progress = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_PROGRESS))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    progress.put(values[0], Boolean.valueOf(values[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading progress file for user: " + userId);
        }
        return progress;
    }

    public void writeUserProgress(Course course, String userId, Map<String, Boolean> exercises) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH_PROGRESS))) {
            for (Map.Entry<String, Boolean> entry : exercises.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing progress file for user: " + userId);
        }
    }
}
