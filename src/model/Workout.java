package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Override
    public String toString() {
        StringBuilder details = new StringBuilder();
        details.append("Workout Details:\n");
        details.append("Description: ").append(description).append("\n");

        details.append("Workouts: ");
        for (int i = 0; i < exercise.size(); i++) {
            details.append(exercise.get(i));
            if (i < exercise.size() - 1) {
                details.append(" - ");
            }
        }
        details.append("\n");

        details.append("Nutrition: ");
        for (int i = 0; i < nutrition.size(); i++) {
            details.append(nutrition.get(i));
            if (i < nutrition.size() - 1) {
                details.append(" - ");
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        details.append("\n");
        if (schedule.isEmpty()) {
            details.append("No scheduled workouts available.\n");
        } else {
            details.append("Schedule:\n");
            for (Map.Entry<LocalDate, List<String>> entry : schedule.entrySet()) {
                details.append("Date: ").append(entry.getKey().format(formatter)).append(" - ");
                for (int i = 0; i < entry.getValue().size(); i++) {
                    details.append(entry.getValue().get(i));
                    if (i < entry.getValue().size() - 1) {
                        details.append(", ");
                    }
                }
                details.append("\n");
            }
        }

        return details.toString();
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
