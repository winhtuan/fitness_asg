package model;

import Model.Person;
import java.time.LocalDate;

public class Coach extends Person {

    private String specialization;
    private double salary;

    public Coach(String id, String name, String cmnd, String phoneNumber, boolean sex,
             LocalDate birthday, String email, Address address, String specialization, double salary) {
        super(id, name, cmnd, phoneNumber, sex, birthday, email, address);
        this.specialization = specialization;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("|%-8.2f|%-10s|\n", salary, specialization);
    }

}
