package reponsitory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Address;
import model.Coach;
import view.Validation;

public class CoachReponsitory implements ICoachReponsitory {

    @Override
    public List<Coach> readFile() {
        List<Coach> listCoachs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12) {
                    try {
                        Coach coach = createCoachFormFile(parts);
                        listCoachs.add(coach);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing line: " + line + " - " + e.getMessage());
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }
        return listCoachs;
    }

    private Coach createCoachFormFile(String[] parts) throws Exception {
        String coachID = parts[0];
        String name = parts[1];
        String ssn = parts[2];
        String phone = parts[3];
        Boolean gender = Boolean.valueOf(parts[4]);
        LocalDate birthDay = new Validation().convertStringToLocalDate(parts[5]);
        String email = parts[6];
        String country = parts[7];
        String city = parts[8];
        String street = parts[9];
        Address address = new Address(country, city, street);
        String specialization = parts[10];
        double salary = Double.parseDouble(parts[11]);
        Coach coach = new Coach(coachID, name, ssn, phone, gender, birthDay, email, address, specialization, salary);
        return coach;
    }

    @Override
    public void writeFile(List<Coach> entities) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            for (Coach coach : entities) {
                String line = String.join(",",
                        coach.getId(),
                        coach.getName(),
                        coach.getCmnd(),
                        coach.getPhoneNumber(),
                        String.valueOf(coach.isSex()),
                        coach.getBirthday().format(DATE_FORMAT),
                        coach.getEmail(),
                        coach.getAddress().getCountry(),
                        coach.getAddress().getCity(),
                        coach.getAddress().getStreet(),
                        coach.getSpecialization(),
                        String.valueOf(coach.getSalary())
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }
    }

}
