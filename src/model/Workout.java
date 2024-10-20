package Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Workout {

    private String description;
    private List<String> exercise;
    private List<String> nutrition;
    private Map<LocalDate, List<String>> schedule;

    public Workout() {
    }

    public Workout(String description, List<String> exercise, List<String> nutrition, Map<LocalDate, List<String>> schedule) {
        this.description = description;
        this.exercise = exercise;
        this.nutrition = nutrition;
        this.schedule = schedule;
    }

    public Map<LocalDate, List<String>> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<LocalDate, List<String>> schedule) {
        this.schedule = schedule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getExercise() {
        return exercise;
    }

    public void setExercise(List<String> exercise) {
        this.exercise = exercise;
    }

    public List<String> getNutrition() {
        return nutrition;
    }

    public void setNutrition(List<String> nutrition) {
        this.nutrition = nutrition;
    }

}
