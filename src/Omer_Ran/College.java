package Omer_Ran;

import java.util.Scanner;

enum DegreeLevel {
    BACHELORS("bachelors"),             // First degree
    MASTERS("masters"),                 // Second degree
    DOCTORATE("doctorate"),             // Dr.
    PROFESSOR("professor");             // Professor
    final String degreeDisplay;

    DegreeLevel(String display) {
        this.degreeDisplay = display;
    }

}

public class College {
    private static final String[] MENU = {
            "Exit Program",
            "Add a new lecturer",
            "Add a new committee",
            "Assign a lecturer to a committee",
            "Update committee's chairman",
            "Remove a member from a committee",
            "Add a new study department",
            "View average income of all lecturers in college",
            "View average income of all lecturers in a specific study department",
            "Lecturers details",
            "Committees details"
    };

    private static String collegeName;
    private static Lecturer[] lecturers = new Lecturer[0];
    private static Department[] studyDepartments = new Department[0];
    private static Committee[] committees = new Committee[0];
    private static int numOfLecturers;
    private static int numOfCommittee;
    private static int numOfDeps;
    private static Scanner s;
    private static DegreeLevel[] degrees = DegreeLevel.values();


    public College(String collegeName) {
        this.collegeName = collegeName;
    }

    public static void run() {
        s = new Scanner(System.in);
        String userChose;
        do {
            userChose = showMenu(s);
            s.nextLine();
            switch (userChose) {
                case "0" -> System.out.println("Done... Bye");
                case "1" -> addLecturer();                    // v
                case "2" -> addCommittee();                   // v
                case "3" -> assignLecturerToComm();           // v
                case "4" -> updateCommChairman();             // v
                case "5" -> removeLecturerFromComm();         // v
                case "6" -> addStudyDepartment();             // v
                case "7" -> getAllLecturersIncome();          // v perfect
                case "8" -> getDepLecturersIncome();
                case "9" -> showLecturers();                  // v
                case "10" -> showCommittees();                // v
                default -> System.out.println("Unexpected value.");
            }
        } while (!userChose.equals("0"));
    }

    private static void addLecturer() {
        System.out.println("Enter lecturer name: ");                // reading name of new lecturer
        System.out.println("(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }
        if (isExist(name, numOfLecturers, lecturers)) {             // checking for existing lecturer
            System.out.println(name + " already exist...");         // if not - continue
            addLecturer();
            return;
        }

        String id = new String();                                   // reading an ID for lecturer
        boolean valid = false;
        while (!valid) {
            System.out.println("Enter lecturer's ID: ");
            System.out.println("(enter '0' to return to menu)");
            id = s.next();
            if (id.equals("0")) {
                return;
            }
            else if (id.length()==9) {                              // checking if is valid
                valid = true;
            }
            else {
                System.out.println("Invalid ID. Make sure to enter exactly 9 digits.");
            }
        }

        DegreeLevel[] degreeLevels = DegreeLevel.values();          // reading degree level of the lecturer
        DegreeLevel degreeLevel = null;
        int degreeChoice;
        valid = false;
        while (!valid) {
            System.out.println("Choose degree level: ");
            System.out.println("(enter '0' to return to menu)");
            degreeChoice = s.nextInt();
            if (degreeChoice == 0) {
                return;
            }
            else if (1 <= degreeChoice && degreeChoice <= 4) {      // checking if is valid
                degreeLevel = degreeLevels[degreeChoice-1];
                valid = true;
            }
            else {
                System.out.println("Invalid choice.");
            }
        }

        String major;                                               // reading major of lecturer
        System.out.println("Enter lecturer's major: ");
        System.out.println("(enter '0' to return to menu)");
        major = s.next();
        if (major.equals("0")) {
            return;
        }

        float salary;                                               // reading salary of lecturer
        System.out.println("Enter lecturer's salary: ");
        System.out.println("(enter '0' to return to menu)");
        salary = s.nextFloat();
        if (salary == 0) {
            return;
        }
        s.nextLine();

        String departmentName;                                      // reading the lecturer's department name
        Department department = null;
        boolean exist = false;
        while (!exist) {
            System.out.println("Enter lecturer's department (if not assigned to any - type 'none'): ");
            System.out.println("(enter '0' to return to menu)");
            departmentName = s.nextLine();
            if (departmentName.equals("0")) {
                return;
            }
            if (!departmentName.equals("none")) {
                for (int i = 0; i < numOfDeps; i++) {                               // checking for existing department
                    if (studyDepartments[i].getName().equals(departmentName)) {     // if exist - continue
                        department = studyDepartments[i];
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    System.out.println(departmentName + " does not exist...");
                }
            }
            else {
                exist = true;
            }
        }
        Lecturer lecturer = new Lecturer(name,id,degreeLevel,major,salary,department);      // adding a new lecturer for general list
        if (numOfLecturers == lecturers.length) {
            lecturers = copy(lecturers, numOfLecturers == 0 ? 2 : numOfLecturers * 2);
        }
        lecturers[numOfLecturers++] = lecturer;
        System.out.println("The new lecturer '" + name + "' has been added successfully!");
        return;
    }

    private static void addCommittee() {
        System.out.println("Enter a committee name: ");         // reading name of new committee
        System.out.println("(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }
        if (isExist(name, numOfCommittee, committees)) {        // checking for existing committee with that name
            System.out.println(name + " already exist...");     // if not - continue
            addCommittee();
            return;
        }

        String lecturerName;                                    // reading a potential chairman (lecturer)
        Lecturer chairman = null;
        boolean notExist = true;
        boolean done = false;
        while (!done) {
            System.out.println("Enter chairman's name: ");
            System.out.println("(enter '0' to return to menu)");
            lecturerName = s.nextLine();
            if (lecturerName.equals("0")) {
                return;
            }
            for (Lecturer selectedLecturer : lecturers) {                       // checking for existing lecturer
                if (selectedLecturer.getName().equals(lecturerName)) {          // if exist - continue
                    notExist = false;
                    if (selectedLecturer.getDegreeLevel().ordinal() >= 2) {     // checking if is valid for the role
                        chairman = selectedLecturer;
                        done = true;
                        break;
                    }
                    else {
                        System.out.println("Lecturer must have a doctorate at least.");
                        break;
                    }
                }
            }
            if (notExist) {
                System.out.println("Lecturer does not exist...");
            }
        }
        Committee committee = new Committee(name, chairman);                              // adding a new committee to the general list.
        if (numOfCommittee == committees.length) {
            committees = copy(committees, numOfCommittee == 0 ? 2 : numOfCommittee * 2);
        }
        committees[numOfCommittee++] = committee;
        System.out.println("The new committee '" + name + "' has been added successfully!");
        return;
    }

    private static void assignLecturerToComm() {
        Lecturer selectedLecturer = null;
        boolean existLec = false;
        while (!existLec) {
            System.out.println("Choose a lecturer to assign: ");                // reading a lecturer name
            System.out.println("(enter '0' to return to menu)");
            String lecturerName = s.nextLine();
            if (lecturerName.equals("0")) {
                return;
            }
            for (Lecturer l : lecturers) {                                      // checking for existing lecturer
                if (l.getName().equals(lecturerName)) {                         // if exist - continue
                    selectedLecturer = l;
                    existLec = true;
                    break;
                }
            }
            if (!existLec) {
                System.out.println("Lecturer does not exist...");
            }
        }
        Committee selectedCommittee = new Committee();
        boolean existComm = false;
        while (!existComm) {
            System.out.println("Enter a committee: ");                          // reading a committee name
            System.out.println("(enter '0' to return to menu)");
            String committeeName = s.nextLine();
            if (committeeName.equals("0")) {
                return;
            }
            for (Committee c : committees) {                                    // checking for existing committee
                if (c.getName().equals(committeeName)) {                        // if exist - continue
                    selectedCommittee = c;
                    existComm = true;
                    break;
                }
            }
            if (!existComm) {
                System.out.println("Committee does not exist...");
            }
        }
        if (existLec && existComm) {                                            // assigning the lecturer to the committee
            selectedCommittee.addLecturer(selectedLecturer);
            System.out.println(selectedLecturer.getName() + " was assigned to " + selectedCommittee.getName() + " committee successfully!");
            return;
        }
    }

    private static void updateCommChairman() {
        Committee selectedCommittee = new Committee();
        boolean exist = false;
        while (!exist) {
            System.out.println("Enter a committee: ");                  // reading a committee name
            System.out.println("(enter '0' to return to menu)");
            String committeeName = s.nextLine();
            if (committeeName.equals("0")) {
                return;
            }
            for (Committee c : committees) {                            // checking for existing committee
                if (c.getName().equals(committeeName)) {                // if exist - continue
                    selectedCommittee = c;
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                System.out.println("Committee does not exist...");
            }
        }

        Lecturer currentLec;
        String lecturerName;
        while (true) {
            System.out.println("Enter the lecturer for the role: ");
            System.out.println("(enter '0' to return to menu)");
            lecturerName = s.nextLine();
            if (lecturerName.equals("0")) {
                return;
            }
            for (int i = 0; i <  selectedCommittee.getNumOfLecturers() ; i++) {
                currentLec = selectedCommittee.getLecturers()[i];
                if (currentLec.getName().equals(lecturerName)) {
                    selectedCommittee.removeLecturer(lecturerName);
                    selectedCommittee.setChairman(currentLec);
                    System.out.println(currentLec.getName() + " was removed from the committee '" + selectedCommittee.getName() + "' and set to be chairman.");
                    return;
                }
            }
            for (int i = 0; i < numOfLecturers; i++) {
                currentLec = lecturers[i];
                if (currentLec.getName().equals(lecturerName)) {
                    selectedCommittee.setChairman(currentLec);
                    System.out.println(lecturerName + "was set to be the chairman of '" + selectedCommittee.getName() + "' committee.");
                    return;
                }
            }
            System.out.println("Lecturer does not exist...");
        }
    }

    private static void removeLecturerFromComm() {
        Committee selectedCommittee = new Committee();                      // reading a committee to access
        boolean exist = false;
        while (!exist) {
            System.out.println("Enter a committee: ");
            System.out.println("(enter '0' to return to menu)");
            String committeeName = s.nextLine();
            if (committeeName.equals("0")) {
                return;
            }
            for (Committee c : committees) {                                // checking for existing committee
                if (c.getName().equals(committeeName)) {                    // if exist - continue
                    selectedCommittee = c;
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                System.out.println("Committee does not exist...");
            }
        }
        Lecturer selectedLecturer = new Lecturer();                         // reading a lecturer to remove
        boolean done = false;
        while (!done) {
            System.out.println("Choose a lecturer to remove: ");
            System.out.println("(enter '0' to return to menu)");
            String lecturerName = s.nextLine();
            if (lecturerName.equals("0")) {
                return;
            }
            else if (selectedCommittee.removeLecturer(lecturerName)) {     // activating removeLecturer(lecturerName) from Committee.class
                done = true;                                               // if 'true' it means lecturer was removed
            }
            else {
                System.out.println("Lecturer does not exist...");
            }
        }
        System.out.println(selectedLecturer.getName() + " was removed from " + selectedCommittee.getName() + " committee successfully!");
    }

    private static void addStudyDepartment() {
        System.out.println("Enter a study department name: ");                      // reading a study department name
        System.out.println("(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }
        if (isExist(name, numOfDeps, studyDepartments)) {                           // checking for existing department
            System.out.println(name + " already exist...");
            addStudyDepartment();
            return;
        }

        System.out.println("Enter the number of students in this department: ");    // reading the number of student in department
        int studentCount = s.nextInt();
        Department department = new Department(name, studentCount);                 // adding a new study department to general list
        if (numOfDeps == studyDepartments.length) {
            studyDepartments = copy(studyDepartments, numOfDeps == 0 ? 2 : numOfDeps * 2);
        }
        studyDepartments[numOfDeps++] = department;
        System.out.println("The new study department '" + name + "' has been added successfully!");
        return;
    }

    private static void getAllLecturersIncome() {
        float sum = 0.0f;                                       // calculating average income of all lecturers in college
        for (int i = 0; i < numOfLecturers ; i++) {
            sum += lecturers[i].getSalary();
        }
        System.out.println("The average income of all lecturers in the college is: " + (sum)/(numOfLecturers));
    }

    private static void getDepLecturersIncome() {
    }

    private static void showLecturers() {
        for (int i = 0; i < numOfLecturers; i++) {
            if (lecturers[i] != null) {
                System.out.println(lecturers[i]);           // activating toString() method in Lecturer.class
            }
        }
    }

    private static void showCommittees() {
        for (int i = 0; i < numOfCommittee; i++) {
            if (committees[i] != null) {
                System.out.println(committees[i]);
            }
        }
    }

    private static Lecturer[] copy(Lecturer[] arr, int size) {
        Lecturer[] temp = new Lecturer[size];
        for (int i = 0; i < arr.length ; i++) {
            temp[i] = arr[i];
        }
        return temp;
    }

    private static Department[] copy(Department[] arr, int size) {
        Department[] temp = new Department[size];
        for (int i = 0; i < arr.length ; i++) {
            temp[i] = arr[i];
        }
        return temp;
    }

    private static Committee[] copy(Committee[] arr, int size) {
        Committee[] temp = new Committee[size];
        for (int i = 0; i < arr.length ; i++) {
            temp[i] = arr[i];
        }
        return temp;
    }

    private static boolean isExist(String name, int numOfObjects, Object[] array) {
        for (int i = 0; i < numOfObjects; i++) {
            Object obj = array[i];
            String objName = null;

            if (obj instanceof Lecturer) {
                objName = ((Lecturer) obj).getName();
            } else if (obj instanceof Department) {
                objName = ((Department) obj).getName();
            } else if (obj instanceof Committee) {
                objName = ((Committee) obj).getName();
            }

            if (objName != null && objName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static String showMenu(Scanner s) {
        System.out.println("\n====== Menu =======");
        for (int i = 0; i < MENU.length; i++) {
            System.out.println(i + ". " + MENU[i]);
        }
        System.out.println("Please choose one of the following options : ");
        return s.next();
    }
}
