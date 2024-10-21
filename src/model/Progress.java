package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Progress {

    private String userID;
    private String courseID;
    private Map<LocalDate, Map<String, Boolean>> progress;

    public Progress(String userID, String courseID, Map<LocalDate, Map<String, Boolean>> progress) {
        this.userID = userID;
        this.courseID = courseID;
        this.progress = progress;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Map<LocalDate, Map<String, Boolean>> getProgress() {
        return progress;
    }

    public void addProgress(LocalDate date, String exerciseId, boolean completed) {
        progress.computeIfAbsent(date, k -> new HashMap<>()).put(exerciseId, completed);
    }

    @Override
    public String toString() {
        StringBuilder progressDetails = new StringBuilder();

        for (Map.Entry<LocalDate, Map<String, Boolean>> entry : progress.entrySet()) {
            LocalDate date = entry.getKey();
            Map<String, Boolean> exercises = entry.getValue();
            progressDetails.append("  Date: ").append(date).append("\n");

            for (Map.Entry<String, Boolean> exerciseEntry : exercises.entrySet()) {
                String exerciseName = exerciseEntry.getKey();
                Boolean completed = exerciseEntry.getValue();
                progressDetails.append("    Exercise: ").append(exerciseName)
                        .append(", Completed: ").append(completed).append("\n");
            }
        }

        return String.format(
                "User ID: %s, Course ID: %s\nProgress Details:\n%s",
                userID, courseID, progressDetails.toString()
        );
    }

}
