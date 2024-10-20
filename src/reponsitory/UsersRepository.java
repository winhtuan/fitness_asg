package reponsitory;

import Model.Users;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Address;
import view.Validation;

public class UsersRepository implements IUserReponsitory {

    private Validation check;

    public UsersRepository() {
        check = new Validation();
    }

    @Override
    public List<Users> readFile() {
        List<Users> listUserses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12) {
                    try {
                        Users user = createUserFormFile(parts);
                        listUserses.add(user);
                    } catch (NumberFormatException e) {
                        check.getMsg("Error parsing line: " + line + " - " + e.getMessage());
                    } catch (Exception ex) {
                        check.getMsg(ex.getMessage());
                    }
                } else {
                    check.getMsg("Invalid line format: " + line);
                }
            }
        } catch (IOException ex) {
            check.getMsg("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }
        return listUserses;
    }

    private Users createUserFormFile(String[] parts) throws Exception {
        String userID = parts[0];
        String name = parts[1];
        String ssn = parts[2];
        String phone = parts[3];
        Boolean gender = Boolean.valueOf(parts[4]);
        LocalDate birthDay = check.convertStringToLocalDate(parts[5]);
        String email = parts[6];
        String country = parts[7];
        String city = parts[8];
        String street = parts[9];
        Address address = new Address(country, city, street);
        double height = Double.parseDouble(parts[10]);
        double weight = Double.parseDouble(parts[11]);
        Users users = new Users(userID, name, ssn, phone, gender, birthDay, email, address, height, weight);
        return users;
    }

    @Override
    public void writeFile(List<Users> listUserses) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            for (Users users : listUserses) {
                String line = String.join(",",
                        users.getId(),
                        users.getName(),
                        users.getCmnd(),
                        users.getPhoneNumber(),
                        String.valueOf(users.isSex()),
                        users.getBirthday().format(DATE_FORMAT),
                        users.getEmail(),
                        users.getAddress().getCountry(),
                        users.getAddress().getCity(),
                        users.getAddress().getStreet(),
                        String.valueOf(users.getHeight()),
                        String.valueOf(users.getWeight())
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException ex) {
            check.getMsg("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }
    }

}
