package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Validation {

    private Scanner sc = new Scanner(System.in);

    public void getMsg(String s) {
        System.out.println(s);
    }

    public <T> T getValue(String msg, Class<T> type) {
        while (true) {
            try {
                System.out.print(msg);
                String input = sc.nextLine().trim();

                double numberValue = Double.parseDouble(input);
                if (numberValue <= 0) {
                    throw new IllegalArgumentException("Error: Please enter a positive number");
                }
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Error: Input can't be empty!");
                }
                if (input.matches(".*[a-zA-Z].*")) {
                    throw new IllegalArgumentException("Error: Input can't contain characters!");
                }
                if (!input.matches("^[0-9]+(\\.[0-9]+)?$")) {
                    throw new IllegalArgumentException("Error: Input must be a valid number!");
                }

                if (type == Integer.class) {
                    int intValue = (int) numberValue;
                    if (numberValue != intValue) {
                        throw new IllegalArgumentException("Error: Please enter an integer number!");
                    }
                    return type.cast(intValue);

                } else if (type == Double.class) {
                    return type.cast(numberValue);

                } else {
                    throw new IllegalArgumentException("Error: Unsupported type!");
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getID(String msg, String regex, String inputName) {

        while (true) {
            try {
                System.out.print(msg);
                String value = sc.nextLine().trim();

                if (value.isEmpty()) {
                    throw new IllegalArgumentException("Error: " + inputName + " can't be null or empty!");
                }

                if (value.matches(regex)) {
                    return value;
                } else {
                    throw new IllegalArgumentException("Error: " + inputName + " follow fommat " + regex);
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getStringValue(String msg, String regex, String inputName) {
        while (true) {
            try {
                System.out.print(msg);
                String value = sc.nextLine().trim();

                if (value.isEmpty()) {
                    throw new IllegalArgumentException("Error: " + inputName + " can't be null or empty!");
                }
                if (value.matches("^-?\\d+(\\.\\d+)?$")) {
                    throw new IllegalArgumentException("Error: " + inputName + " can't contain number!");
                }
                if (value.matches("^W+$")) {
                    throw new IllegalArgumentException("Error: " + inputName + " can't contain special characters!");
                }

                if (value.matches(regex)) {
                    return value;
                } else {
                    throw new IllegalArgumentException("Error: " + inputName + " contains invalid characters! Please Enter Correct " + regex);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            }
        }
    }

    public String getInputString(String msg, String regex, String inputName) {

        while (true) {
            try {
                System.out.print(msg);
                String value = sc.nextLine().trim();
                if (value.isEmpty()) {
                    throw new IllegalArgumentException("Error: " + inputName + " can't be null or empty!");
                }
                if (value.matches(regex)) {
                    return uppcaseLetter(value);
                } else {
                    throw new IllegalArgumentException("Error: " + inputName + " contains invalid characters!");
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean continueConfirm(String msg) {
        while (true) {
            System.out.print(msg + " (y/n): ");
            try {
                String choice = sc.nextLine().toLowerCase();

                if (choice.equals("y") || choice.equals("yes")) {
                    return true;
                } else if (choice.equals("n") || choice.equals("no")) {
                    return false;
                } else {
                    System.out.println("Please enter y or n!!");
                }
            } catch (Exception e) {
                System.out.println("Please enter y or n!!");
            }
        }
    }

    public boolean getBoolean(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                String input = sc.nextLine().trim().toLowerCase();

                if (input.equals("true") || input.equals("false")) {
                    return Boolean.parseBoolean(input);
                } else {
                    throw new IllegalArgumentException("Error: Please enter 'true' or 'false'!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public double getValue(String msg, int min, int max) {
        double value = 0;
        System.out.print(msg);
        while (true) {
            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Error: Input cannot be null or empty!");
                }
                value = Double.parseDouble(input);

                if (value >= min && value <= max) {
                    break;
                } else {
                    if (value < min) {
                        throw new IllegalArgumentException("Error: Please enter value bigger " + min);
                    } else if (value > max) {
                        throw new IllegalArgumentException("Error: Please enter value smaller " + max);
                    }

                    if (value <= 0) {
                        throw new IllegalArgumentException("Error: Please enter positive number!");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return value;
    }

    public LocalDate getDate(String msg) {
        try {
            System.out.print(msg);
            String day = sc.nextLine().trim();
            LocalDate date = convertStringToLocalDate(day);
            return date;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format!");
            return null;
        }
    }

    public LocalDate convertStringToLocalDate(String day) {
        String[] patterns = {"d/M/yyyy", "dd/M/yyyy", "d/MM/yyyy", "dd/MM/yyyy"};

        for (String pattern : patterns) {
            try {
                DateTimeFormatter formmat = DateTimeFormatter.ofPattern(pattern);
                return LocalDate.parse(day, formmat);
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
            }
        }
        throw new IllegalArgumentException("Invalid date format: " + day);
    }

    public String uppcaseLetter(String string) {
        String[] words = string.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }
        return result.toString().trim();
    }

    public String getTwoDecimalPlaces(double number) {
        number = Math.round(number * 100) / 100;
        return String.format("%.2f", number);
    }

}
