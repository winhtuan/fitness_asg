package reponsitory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Progress;
import view.Validation;

public class ProgressRepository implements IProgressReponsitory {

    final String PATH = new File("src\\data\\progress.csv").getAbsolutePath();
    private Validation check;

    public ProgressRepository() {
        check = new Validation();
    }

    @Override
    public List<Progress> readFile() {
        List<Progress> progresses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2) {
                    String userID = values[0].trim();
                    String courseID = values[1].trim();
                    Map<LocalDate, Map<String, Boolean>> progressMap = new HashMap<>();

                    line = br.readLine();
                    if (line != null && line.startsWith("Progess:")) {
                        while ((line = br.readLine()) != null && !line.isEmpty()) {
                            if (line.startsWith("Date:")) {
                                String progressEntry = line.substring("Date:".length()).trim();
                                String[] progressData = progressEntry.split(",");

                                if (progressData.length == 3) {
                                    LocalDate date = check.convertStringToLocalDate(progressData[0].trim());
                                    String exerciseName = progressData[1].trim();
                                    Boolean status = Boolean.valueOf(progressData[2].trim());

                                    progressMap.putIfAbsent(date, new HashMap<>());
                                    progressMap.get(date).put(exerciseName, status);
                                }
                            }
                        }
                    }
                    Progress progress = new Progress(userID, courseID, progressMap);
                    progresses.add(progress);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }
        return progresses;
    }

    @Override
    public void writeFile(List<Progress> progresses) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            for (Progress progress : progresses) {
                bw.write(progress.getUserID() + "," + progress.getCourseID());
                bw.newLine();
                bw.write("Progess:");
                bw.newLine();

                for (Map.Entry<LocalDate, Map<String, Boolean>> entry : progress.getProgress().entrySet()) {
                    LocalDate date = entry.getKey();
                    Map<String, Boolean> exercises = entry.getValue();

                    for (Map.Entry<String, Boolean> exerciseEntry : exercises.entrySet()) {
                        String exerciseName = exerciseEntry.getKey();
                        Boolean status = exerciseEntry.getValue();

                        bw.write("Date:" + date + "," + exerciseName + "," + status);
                        bw.newLine();
                    }
                }
                bw.newLine(); 
            }
            System.out.println("Progress data written successfully to: " + PATH);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + PATH + ". Please check if the file is accessible.");
        }
    }

}
