package first_step;

import java.util.Arrays;
import java.util.Scanner;

public class Starter {
    private static final String[] MENU = {
            "Exit Program",
            "Add Student",
            "Remove Student",
            "Show Students"
            // Add more options
    };
    private static Scanner s;
    private static String[] students;
    private static int numOfStudents;

    public static void main(String[] args) {
        s = new Scanner(System.in);
        run();
        s.close();
    }

    private static void run() {
        students = new String[0];
        int userChosen;
        do {
            userChosen = showMenu(s);
            switch (userChosen) {
                case 0 -> System.out.println("Done... Bye");
                case 1 -> addStudent();
                case 2 -> removeStudent();
                case 3 -> showStudent();
                // add more options
                default -> System.out.println("Unexpected value");
            }
        } while (userChosen != 0);
    }

    private static void showStudent() {
        System.out.print("[");
        for (int i = 0 ; i < numOfStudents ; i++) {
            if (i == numOfStudents -1) {
                System.out.print(students[i]);
            }
            else {
                System.out.print(students[i] + ", ");
            }
        }
        System.out.println("]");
    }

    private static void addStudent() {
        s.nextLine();
        System.out.println("Enter student name: " );
        String name = s.nextLine();
        if (isExist(name)) {
            System.out.println(name + " already exist...");
            return;
        }
        if (numOfStudents == students.length) {
            students = Arrays.copyOf(students, numOfStudents == 0 ? 2 : numOfStudents*2);
        }
        students[numOfStudents++] = name;
    }

    private static boolean isExist(String name) {
        for (int i = 0; i < numOfStudents ; i++) {
            if (students[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static void removeStudent() {
        System.out.println("Clicked Option1 2 " );
    }

    private static int showMenu(Scanner s) {
        System.out.println("\n====== Menu =======");
        for (int i = 0; i < MENU.length; i++) {
            System.out.println(i + ". " + MENU[i]);
        }
        System.out.println("Please enter your chose : ");
        return s.nextInt();
    }

}
