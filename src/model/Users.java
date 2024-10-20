package Model;

import java.time.LocalDate;
import model.Address;

public class Users extends Person {

    private double height;
    private double weight;

    public Users(String id, String name, String cmnd, String phoneNumber, 
            boolean sex, LocalDate birthday, String email, Address address, double height, double weight) {
        super(id, name, cmnd, phoneNumber, sex, birthday, email, address);
        this.height = height;
        this.weight = weight;
    }

    public double calBMI() {
        return weight / Math.pow(height, 2);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("%-8.2f|%-8.2f|", height, weight);
    }
}
