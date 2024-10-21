package reponsitory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
            String currentUserID = null;
            String currentCourseID = null;
            Map<LocalDate, Map<String, Boolean>> progressMap = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.trim(); 
                if (line.contains(",")) {
                    if (currentUserID != null && currentCourseID != null) {
                        progresses.add(new Progress(currentUserID, currentCourseID, new HashMap<>(progressMap)));
                        progressMap.clear(); 
                    }

                    String[] ids = line.split(",");
                    currentUserID = ids[0].trim();
                    currentCourseID = ids[1].trim();

                    line = br.readLine();
                    if (line == null || !line.startsWith("Progress:")) {
                        break; 
                    }
                }

                while (line != null && line.startsWith("Progress:")) {
                    line = line.substring("Progress:".length()).trim();
                    String[] progressData = line.split(",");

                    if (progressData.length == 3) {
                        LocalDate date = check.convertStringToLocalDate(progressData[0]); 
                        String exerciseName = progressData[1].trim();
                        Boolean status = Boolean.valueOf(progressData[2].trim());

                        progressMap.putIfAbsent(date, new HashMap<>());
                        progressMap.get(date).put(exerciseName, status);
                    }
                    line = br.readLine();
                }
            }

            if (currentUserID != null && currentCourseID != null) {
                progresses.add(new Progress(currentUserID, currentCourseID, new HashMap<>(progressMap)));
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }

        return progresses;
    }

    @Override
    public void writeFile(List<Progress> progresses) {
        // Implementation for writing progress data to file can be added here
    }
}
