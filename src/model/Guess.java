package Model;

import java.time.LocalDate;
import model.Address;

public class Guess extends Person {

    public Guess(String id, String name, String cmnd, String phoneNumber, boolean sex, LocalDate birthday, String email, Address address) {
        super(id, name, cmnd, phoneNumber, sex, birthday, email, address);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
