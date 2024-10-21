package service;

import java.time.format.DateTimeFormatter;

public interface Service<T> {

    void display();

    T findByID(String id);
    
    final String USER_REGEX = "CUS-\\d{4}";
    final String COURSE_REGEX = "COR-\\d{4}";
    final String COACH_REGEX = "COA-\\d{4}";

    final String TEXT_REGEX = "^([A-Za-z]+(?:\\s[A-Za-z]+)*)$";
    final String SSN_REGEX = "^\\d{12}$";
    final String PHONE_REGEX = "^0\\d{9}$";
    final String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9.-]+$";
    final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
}
