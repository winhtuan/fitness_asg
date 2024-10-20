package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.Address;

public abstract class Person {

    private String id;
    private String name;
    private String cmnd;
    private String phoneNumber;
    private boolean sex;
    private LocalDate birthday;
    private String email;
    private Address address;

    public Person(String id, String name, String cmnd, String phoneNumber, boolean sex, LocalDate birthday, String email, Address address) {
        this.id = id;
        this.name = name;
        this.cmnd = cmnd;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dob = birthday.format(fomatter);
        return String.format("|%-10s|%-25s|%-15s|%-12s|%-8s|%-25s|%-15s|%-36s|",
                id, name, cmnd, phoneNumber, isSex() ? "Male" : "Female", email, dob, address);
    }

}
