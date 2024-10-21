package reponsitory;

import Model.Course;
import Model.Workout;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseRepository implements Reponsitory<Course, List<Course>> {

    private List<Course> listCourse;
    final String PATH = new File("src\\data\\course.csv").getAbsolutePath();

    public CourseRepository() {
        listCourse = new ArrayList<>();
    }

    @Override
    public List<Course> readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    String courseId = values[0];
                    String courseName = values[1];
                    double price = Double.parseDouble(values[2]);
                    String time = values[3];
                    String coachID = values[4];
                    String workoutDescription = values[5].trim();

                    line = br.readLine();
                    if (line == null || !line.startsWith("Exercises:")) {
                        break;
                    }
                    String[] exerciseLine = line.split(":")[1].split(",");
                    List<String> exercises = new ArrayList<>(Arrays.asList(exerciseLine));

                    line = br.readLine();
                    if (line == null || !line.startsWith("Nutrition Plan:")) {
                        break;
                    }
                    String[] nutritionLine = line.split(":")[1].split(",");
                    List<String> nutrition = new ArrayList<>(Arrays.asList(nutritionLine));

                    Map<LocalDate, List<String>> schedule = new HashMap<>();

                    while ((line = br.readLine()) != null && line.startsWith("Date:")) {
                        String[] scheduleData = line.split(" - Activities: ");
                        LocalDate date = LocalDate.parse(scheduleData[0].substring(6), DATE_FORMAT);
                        List<String> activities = Arrays.asList(scheduleData[1].split(", "));
                        schedule.put(date, activities);
                    }

                    Workout workout = new Workout(workoutDescription, exercises, nutrition, schedule);
                    Course course = new Course(courseId, courseName, price, time, coachID, workout);

                    listCourse.add(course);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }
        return listCourse;
    }

    @Override
    public void writeFile(List<Course> entities) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            for (Course course : entities) {
                String line = String.join(",",
                        course.getCourseId(),
                        course.getCourseName(),
                        String.valueOf(course.getPrice()),
                        course.getTime(),
                        course.getCoachID(),
                        course.getWorkout().getDescription()
                );
                bw.write(line);
                bw.newLine();
                bw.write("Exercises: " + String.join(",", course.getWorkout().getExercise()));
                bw.newLine();
                bw.write("Nutrition Plan: " + String.join(",", course.getWorkout().getNutrition()));
                bw.newLine();
                bw.write("Schedule:");
                bw.newLine();
                for (Map.Entry<LocalDate, List<String>> entry : course.getWorkout().getSchedule().entrySet()) {
                    bw.write("Date: " + entry.getKey().format(DATE_FORMAT) + " - Activities: " + String.join(", ", entry.getValue()));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + PATH + ". Please check if the file is accessible.");
        }

    }
}
