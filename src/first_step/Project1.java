package Omer_Ran;

import java.util.Scanner;

// Omer 315854091
// Ran  212929269

public class Project1 {
    public static void main(String[] args) {
        s = new Scanner(System.in);
        getCollegeName(s);
        run();
        s.close();
    }

    private static final String[] MENU = {
            "Exit Program",
            "Add a new lecturer",
            "Add a new committee",
            "Add a new study department",
            "Assign a lecturer to a study department",
            "View average income of all lecturers in college",
            "View average income of all lecturers in a specific study department",
            "Lecturers details",
            "Committees details"
    };

    private static Scanner s;
    private static String[] lecturers;
    private static String[] committees;
    private static String[] studyDepartments;
    private static int numOfLecturers;
    private static int numOfCommittee;
    private static int numOfDeps;

    private static void run() {
        lecturers = new String[0];
        committees = new String[0];
        studyDepartments = new String[0];
        int userChose;
        do {
            userChose = showMenu(s);
            s.nextLine();
            switch (userChose) {
                case 0 -> System.out.println("Done... Bye");
                case 1 -> addLecturer();
                case 2 -> addCommittee();
                case 3 -> addStudyDepartment();
                case 4 -> assignLecturerToDep();
                case 5 -> getAllLecturersIncome();
                case 6 -> getDepLecturersIncome();
                case 7 -> showLecturers();
                case 8 -> showCommittees();
                default -> System.out.println("Unexpected value");
            }
        } while (userChose != 0);
    }

    private static void addLecturer() {
        System.out.println("Enter lecturer name: ");
        System.out.println("(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }
        if (isExist(name, numOfLecturers, lecturers)) {
            System.out.println(name + " already exist...");
            addLecturer();
            return;
        }
        if (numOfLecturers == lecturers.length) {
            lecturers = copy(lecturers, numOfLecturers == 0 ? 2 : numOfLecturers * 2);
        }
        lecturers[numOfLecturers++] = name;
        System.out.println("The new lecturer '" + name + "' has been added successfully!");
        return;
    }

    private static void addCommittee() {
        System.out.println("Enter a committee name: ");
        System.out.println("(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }
        if (isExist(name, numOfCommittee, committees)) {
            System.out.println(name + " already exist...");
            addCommittee();
            return;
        }
        if (numOfCommittee == committees.length) {
            committees = copy(committees, numOfCommittee == 0 ? 2 : numOfCommittee * 2);
        }
        committees[numOfCommittee++] = name;
        System.out.println("The new committee '" + name + "' has been added successfully!");
        return;
    }

    private static void addStudyDepartment() {
        System.out.println("Enter a study department name: ");
        System.out.println("(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }
        if (isExist(name, numOfDeps, studyDepartments)) {
            System.out.println(name + " already exist...");
            addStudyDepartment();
            return;
        }
        if (numOfDeps == studyDepartments.length) {
            studyDepartments = copy(studyDepartments, numOfDeps == 0 ? 2 : numOfDeps * 2);
        }
        studyDepartments[numOfDeps++] = name;
        System.out.println("The new study department '" + name + "' has been added successfully!");
        return;
    }

    private static void assignLecturerToDep() {
        System.out.println("Choose a lecturer to assign: ");
        System.out.println("(enter '0' to return to menu)");
        String selectedLecturer = s.nextLine();
        if (selectedLecturer.equals("0")) {
            return;
        }
        if (!isExist(selectedLecturer,numOfLecturers,lecturers)) {
            System.out.println("Lecturer does not exist.");
        }
        System.out.println("Choose a department: ");
        String selectedDep = s.nextLine();
        if (!isExist(selectedDep,numOfDeps,studyDepartments)) {
            System.out.println("Study department does not exist.");
        }
        if (isExist(selectedLecturer,numOfLecturers,lecturers) && isExist(selectedDep,numOfDeps,studyDepartments)) {
            System.out.println(selectedLecturer + " was assigned to " + selectedDep + " department successfully!");
            return;
        }
    }

    private static void getAllLecturersIncome() {}

    private static void getDepLecturersIncome() {
    }

    private static void showLecturers() {
        System.out.print("[");
        for (int i = 0; i < numOfLecturers; i++) {
            if (lecturers[i] != null) {
                if (i == numOfLecturers - 1) {
                    System.out.print(lecturers[i]);
                } else {
                    System.out.print(lecturers[i] + ", ");
                }
            }

        }
        System.out.println("]");
    }

    private static void showCommittees() {
        System.out.print("[");
        for (int i = 0; i < numOfCommittee; i++) {
            if (committees[i] != null) {
                if (i == numOfCommittee - 1) {
                    System.out.print(committees[i]);
                } else {
                    System.out.print(committees[i] + ", ");
                }
            }

        }
        System.out.println("]");
    }

    private static String[] copy(String[] arr, int size) {
        String[] temp = new String[size];
        for (int i = 0; i < arr.length ; i++) {
            temp[i] = arr[i];
        }
        return temp;
    }

    private static boolean isExist(String name, int numOfObjects, String[] array) {
        for (int i = 0; i < numOfObjects; i++) {
            if (array[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static int showMenu(Scanner s) {
        System.out.println("\n====== Menu =======");
        for (int i = 0; i < MENU.length; i++) {
            System.out.println(i + ". " + MENU[i]);
        }
        System.out.println("Please choose one of the following options : ");
        return s.nextInt();
    }

    private static void getCollegeName(Scanner s) {
        boolean continue1 = true;

        do {
            System.out.print("Please enter a college name: ");
            String collegeName = s.next();

            if (collegeName.equals("afeka")){
                continue1 = false;
            }
            else {
                continue1 = false;
            }
        } while (continue1);
    }

}
