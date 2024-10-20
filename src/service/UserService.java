package service;

import Model.Users;
import java.time.LocalDate;
import java.util.List;
import model.Address;
import reponsitory.UsersRepository;
import view.Validation;

public class UserService implements IUserService {

    private List<Users> listUserses;
    private Validation check;
    private UsersRepository usersRepository;

    public UserService() {
        check = new Validation();
        usersRepository = new UsersRepository();
        listUserses = usersRepository.readFile();

    }

    @Override
    public void addNewCustomer() {
        Users users = registNewUser();
        listUserses.add(users);
        System.out.println("Add New User Successfull.");
    }

    public Users registNewUser() {
        String id = check.getID("Enter ID: ", USER_REGEX, "User ID");
        String name = check.getInputString("Enter Your Name: ", TEXT_REGEX, "Name");
        String cmnd = check.getInputString("Enter Your SSN: ", SSN_REGEX, "SSN");
        String phoneNumber = check.getInputString("Enter Your Phone Number: ", PHONE_REGEX, "Phone Number");
        boolean sex = check.getBoolean("Enter sex (true for male, false for female): ");
        LocalDate birthday = check.getDate("Enter Your Birthday: ");
        String email = check.getInputString("Enter Your Email: ", EMAIL_REGEX, "Email");
        String country = check.getInputString("Enter Your Country: ", TEXT_REGEX, "Country");
        String city = check.getInputString("Enter Your City: ", TEXT_REGEX, "City");
        String street = check.getInputString("Enter Your Street: ", TEXT_REGEX, "Street");
        Address address = new Address(country, city, street);
        double height = check.getValue("Enter your height(m): ", Double.class);
        double weight = check.getValue("Enter your weight(kg): ", Double.class);
        Users newUser = new Users(id, name, cmnd, phoneNumber, sex, birthday, email, address, height, weight);
        System.out.println("User registered: " + newUser);
        return newUser;
    }

    @Override
    public void display() {
        System.out.printf("|%-10s|%-25s|%-15s|%-12s|%-8s|%-25s|%-15s|%-36s|%-8s|%-8s|\n",
                "User ID", "User Name", "SSN", "Phone", "Gender", "Email", "Birthday", "Address", "Height", "Weight");
        for (Users users : listUserses) {
            System.out.println(users);
        }
    }

    @Override
    public Users findByID(String id) {
        for (Users users : listUserses) {
            if (users.getId().trim().equalsIgnoreCase(id.trim())) {
                return users;
            }
        }
        return null;
    }

}
