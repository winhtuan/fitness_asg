package controler;

import view.Menu;

public class Gym extends Menu {

    private static String[] choices = {"", "", "", "", "", "", "Exit"};

    public Gym() {
        super("Fitness System", choices);

    }

    public static void main(String[] args) {
        Gym gym = new Gym();
        gym.run();
    }

    @Override
    public void execute(int ch) {
        switch (ch) {
            case 1 -> {
            }
            case 2 -> {
            }
            case 3 -> {
            }
            case 4 -> {
            }
            case 5 -> {
            }
            case 6 -> {
            }
            case 7 ->
                System.exit(0);
            default ->
                System.out.println("Invalid choice! Please try again.");
        }
    }
}
