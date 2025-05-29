package Omer_Ran;

import java.util.InputMismatchException;
import java.util.Scanner;

// Omer 315854091
// Ran  212929269

public class Main {
    private static final String[] MENU = {
            "Exit Program",
            "Add a new lecturer",
            "Add a new committee",
            "Assign a lecturer to a committee",
            "Assign a lecturer to a study department",
            "Update committee's chairman",
            "Remove a member from a committee",
            "Add a new study department",
            "View average income of all lecturers in college",
            "View average income of all lecturers in a specific study department",
            "Lecturers details",
            "Committees details",
            "Compare Doctor/Professor by number of articles",
            "Compare committees"
    };

    private static final String[] COMPARE_METHODS = {"return to main menu",
                                                    "By members amount",
                                                    "By sum of articles"};

    private static Scanner s;

    public static void main(String[] args) {
        s = new Scanner(System.in);
        College college = new College(enterCollegeName(s));
//        college.init();
        run(college, s);
        s.close();
    }

    public static void run(College college, Scanner s) {
        int userChose;
        while (true) {
            showMenu();
            try {
                userChose = s.nextInt();
                s.nextLine();
                switch (userChose) {
                    case 0 -> {                                         // v
                        System.out.println("Done... Bye");
                        return;
                    }
                    case 1 -> addLecturer(college);                     // v
                    case 2 -> addCommittee(college);                    // v
                    case 3 -> assignLecturerToComm(college);            // v
                    case 4 -> assignLecturerToDep(college);             // v
                    case 5 -> updateCommChairman(college);              // v
                    case 6 -> removeLecturerFromComm(college);          // v
                    case 7 -> addStudyDepartment(college);              // v
                    case 8 -> getAllLecturersIncome(college);           // v
                    case 9 -> getDepLecturersIncome(college);           // v
                    case 10 -> showLecturers(college);                  // v
                    case 11 -> showCommittees(college);                 // v
                    case 12 -> CompareArticlesHolders(college);         // v
                    case 13 -> compareCommittees(college);              // v
                    default -> {
                        System.out.println("Unexpected value!");        // v
                    }
                }
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input.");
                s.nextLine();
            }
        }
    }


    private static void addLecturer(College college) throws CollegeExceptions {
        System.out.println(
                "Enter lecturer name: \n" +                                         // reading a lecturer name
                        "(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }

        System.out.println(
                "Enter lecturer's ID: \n" +                                         // reading a lecturer's ID
                        "(enter '0' to return to menu)");
        String id = s.next();
        if (id.equals("0")) {
            return;
        }

        String major;                                                               // reading major of lecturer
        System.out.println(
                "Enter lecturer's major: \n" +
                        "(enter '0' to return to menu)");
        major = s.next();
        if (major.equals("0")) {
            return;
        }

        float salary;                                                               // reading salary of lecturer
        System.out.println(
                "Enter lecturer's salary: \n" +
                        "(enter '0' to return to menu)");
        salary = s.nextFloat();
        if (salary == 0) {
            return;
        }
        s.nextLine();

        String departmentName;                                                      // reading the lecturer's department name
        System.out.println(
                "Enter lecturer's department (if not assigned to any - type 'none'): \n" +
                        "(enter '0' to return to menu)");
        departmentName = s.nextLine();
        if (departmentName.equals("0")) {
            return;
        }
        Department department = new Department(departmentName);                     // creating a pending department

        DegreeLevel[] degreeLevels = DegreeLevel.values();                          // reading degree level of the lecturer
        DegreeLevel degreeLevel = null;
        int degreeChoice;
        boolean valid = false;
        while (!valid) {
            System.out.println("Choose degree level: ");
            for (int i = 0; i < DegreeLevel.values().length; i++) {
                System.out.println(i + 1 + ". " + DegreeLevel.values()[i]);
            }
            System.out.println("(enter '0' to return to menu)");
            degreeChoice = s.nextInt();
            if (degreeChoice == 0) {
                return;
            } else if (1 <= degreeChoice && degreeChoice <= degreeLevels.length) {      // checking if choice is valid
                degreeLevel = degreeLevels[degreeChoice - 1];
                valid = true;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        s.nextLine();

        String[] articles = null;
        if (degreeLevel == DegreeLevel.PROFESSOR || degreeLevel == DegreeLevel.DOCTOR) {
            System.out.println("Please enter your published articles (separated with ', '): ");
            String input = s.nextLine();
            articles = input.split(", ");
        }
        String certifyingInst = null;
        if (degreeLevel == DegreeLevel.PROFESSOR) {
            System.out.println("Please enter your certifying institution: ");
            certifyingInst = s.nextLine();
        }

        try {
            college.addLecturer(name, id, degreeLevel, major, salary, department, articles, certifyingInst);
        } catch (ExistException ise) {
            printAddLecturerFailure(name, ise);
            addLecturer(college);
        } catch (InvalidInputException iie) {
            printAddLecturerFailure(name, iie);
            return;
        }

        System.out.println("The new lecturer '" + name + "' has been added successfully!");
    }
    private static void printAddLecturerFailure(String name, InvalidInputException iie) {
        System.out.println("Failed to add the lecturer '" + name + "'.");
        System.out.println(iie.getMessage());
    }

    private static void addCommittee(College college) {
        System.out.println(
                "Enter a committee name: \n" +                                                          // reading a committee
                        "(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }
        System.out.println(
                "Enter chairman's name: \n" +                                                           // reading a committee
                        "(enter '0' to return to menu)");
        String lecturerName = s.nextLine();
        if (lecturerName.equals("0")) {
            return;
        }
        Lecturer chairman = new Lecturer(lecturerName);

        try {
            college.addCommittee(name, chairman);
        } catch (InvalidInputException e) {
            printAddCommitteeFailure(name, e);
            return;
        }
        System.out.println("The new committee '" + name + "' has been added successfully!");
    }
    private static void printAddCommitteeFailure(String name, CollegeExceptions ce) {
        System.out.println("Failed to add the committee '" + name + "'.");
        System.out.println(ce.getMessage());
    }

    private static void assignLecturerToComm(College college) {
        System.out.println(
                "Choose a lecturer to assign: \n" +                                         // reading a lecturer
                        "(enter '0' to return to menu)");
        String lecturerName = s.nextLine();
        if (lecturerName.equals("0")) {
            return;
        }
        Lecturer pendingLecturer = new Lecturer(lecturerName);

        System.out.println(
                "Enter a committee: \n" +                                                   // reading a committee
                        "(enter '0' to return to menu)");
        String committeeName = s.nextLine();
        if (committeeName.equals("0")) {
            return;
        }
        Committee pendingCommittee = new Committee(committeeName);
        try {
            college.assignLecToComm(pendingLecturer, pendingCommittee);       // trying to assign
        } catch (InvalidInputException iie) {
            printAssignmentFailure(pendingLecturer,pendingCommittee,iie);
            return;
        }
        System.out.println("'" + lecturerName + "' was assigned to '" + committeeName + "' committee successfully!");
    }

    private static void assignLecturerToDep(College college) {
        System.out.println(
                "Choose a lecturer to assign: \n" +                                         // reading a lecturer
                        "(enter '0' to return to menu)");
        String lecturerName = s.nextLine();
        if (lecturerName.equals("0")) {
            return;
        }
        Lecturer pendingLecturer = new Lecturer(lecturerName);

        System.out.println(
                "Enter a department: \n" +                                                  // reading a department
                        "(enter '0' to return to menu)");
        String departmentName = s.nextLine();
        if (departmentName.equals("0")) {
            return;
        }
        Department pendingDepartment = new Department(departmentName);

        try {
            college.assignLecToDep(pendingLecturer, pendingDepartment);       // trying to assign
        } catch (InvalidInputException iie) {
            printAssignmentFailure(pendingLecturer,pendingDepartment,iie);
            return;
        }
        System.out.println("'" + lecturerName + "' was assigned to '" + departmentName + "' department successfully!");
    }
    private static void printAssignmentFailure(Nameable na, Nameable toNa, InvalidInputException iie) {
        System.out.println("Failed to assign '" + na.getName() + "' to '" + toNa.getName() + "' " + toNa.getClass().getSimpleName() + ".");
        System.out.println(iie.getMessage());
    }

    private static void updateCommChairman(College college) {
        System.out.println(
                "Enter a committee: \n" +                                               // reading a committee
                        "(enter '0' to return to menu)");
        String committeeName = s.nextLine();
        if (committeeName.equals("0")) {
            return;
        }
        Committee committee = new Committee(committeeName);

        System.out.println(
                "Enter the lecturer for the role: \n" +                                 // reading a potential chairman
                        "(enter '0' to return to menu)");
        String lecturerName = s.nextLine();
        if (lecturerName.equals("0")) {
            return;
        }
        Lecturer lecturer = new Lecturer(lecturerName);

        try {
            college.updateCommChairman(lecturer, committee);                            // trying to assign a new chairman
        } catch (CollegeExceptions ce) {
            System.out.println("Failed to assign '" + lecturerName + "' to be chairman of '" + committeeName + " committee.");
            System.out.println(ce.getMessage());
            return;
        }
        System.out.println("'" + lecturerName + "' was set to be the chairman of '" + committeeName + "' committee.");
    }

    private static void removeLecturerFromComm(College college) {
        System.out.println(
                "Enter a committee: \n" +                                       // reading a committee
                        "(enter '0' to return to menu)");
        String committeeName = s.nextLine();
        if (committeeName.equals("0")) {
            return;
        }
        Committee committee = new Committee(committeeName);

        System.out.println(
                "Choose a lecturer to remove: \n" +                             // reading a lecturer
                        "(enter '0' to return to menu)");
        String lecturerName = s.nextLine();
        if (lecturerName.equals("0")) {
            return;
        }
        Lecturer lecturer = new Lecturer(lecturerName);

        try {
            college.removeLecturerFromComm(lecturer, committee);                  // trying to remove lecturer from committee
        } catch (NotExistException nee) {
            System.out.println("Failed to remove '" + lecturerName + "' from '" + committeeName + "' committee.");
            System.out.println(nee.getMessage());
            return;
        }
        System.out.println("'" + lecturerName + "' was removed from '" + committeeName + "' committee successfully!");
    }

    private static void addStudyDepartment(College college) {
        System.out.println(
                "Enter a study department name: \n" +
                        "(enter '0' or 'none' to return to menu)");                                     // reading a study department name
        String name = s.nextLine();
        if (name.equals("0") || name.equals("none")) {
            return;
        }
        System.out.println(
                "Enter the number of students in this department: \n" +
                        "(enter '0' to return to menu)");                                               // reading the number of student in department
        int studentCount = s.nextInt();
        s.nextLine();

        try {
            college.addDepartment(name, studentCount);                                   // trying to add studyDepartment
        } catch (ExistException ee) {
            System.out.println("Failed to add the department '" + name + "'.");
            System.out.println(ee.getMessage());
            addStudyDepartment(college);
        }
        System.out.println("The new study department '" + name + "' has been added successfully!");
    }

    private static void getAllLecturersIncome(College college) {
        float sum = 0.0f;                                                           // calculating average income of all lecturers in college
        for (int i = 0; i < college.getNumOfLecturers(); i++) {
            sum += college.getLecturers()[i].getSalary();
        }
        System.out.println("The average income of all lecturers in the college is: " + (sum) / (college.getNumOfLecturers()));
    }

    private static void getDepLecturersIncome(College college) {
        System.out.println(
                "Enter a department: \n" +                                                  // reading a department
                        "(enter '0' to return to menu)");
        String departmentName = s.nextLine();
        if (departmentName.equals("0")) {
            return;
        }
        Department department = new Department(departmentName);
        try {
            float averageIncome = college.getDepLecturersIncome(department);                    // trying to calculate average income in department
            System.out.println("The average income of the lecturers in '" + departmentName + "' department is: " + averageIncome);
        } catch (NotExistException nee) {
            System.out.println("Failed to display '" + departmentName + "' department's average salary");
            System.out.println(nee.getMessage());
        }

    }

    private static void showLecturers(College college) {
        for (int i = 0; i < college.getNumOfLecturers(); i++) {
            if (college.getLecturers()[i] != null) {
                System.out.println(college.getLecturers()[i]);           // activating toString() method in Lecturer.class
            }
        }
    }

    private static void showCommittees(College college) {
        for (int i = 0; i < college.getNumOfCommittee(); i++) {
            if (college.getCommittees()[i] != null) {
                System.out.println(college.getCommittees()[i]);         // activating toString() method in Committee.class
            }
        }
    }

    private static void CompareArticlesHolders(College college) {
        System.out.println("Please enter first lecturer (must be doctor or professor):");
        Lecturer fstLec = new Lecturer(s.nextLine());
        System.out.println("Please enter second lecturer (must be doctor or professor):");
        Lecturer scdLec = new Lecturer(s.nextLine());
        int a = college.compareLecArticles(fstLec,scdLec);
        if (a > 0) {
            printArticlesHoldersResult(fstLec,scdLec);
        } else if (a < 0) {
            printArticlesHoldersResult(scdLec,fstLec);
        } else {
            System.out.println("Both lecturers have the same number of articles.");
        }
    }
    private static void printArticlesHoldersResult(Lecturer lecGreater, Lecturer lecLesser) {
        System.out.println(lecGreater.getName() + " has more articles than " + lecLesser.getName() + ".");
    }

    private static void compareCommittees(College college) {
        System.out.println("Please enter first committee: ");
        String comName1 = s.nextLine();
        Committee fstCom = new Committee(comName1);
        System.out.println("Please enter second committee: ");
        String comName2 = s.nextLine();
        Committee scdCom = new Committee(comName2);
        System.out.println("Please choose preferred method to compare with: ");
        for (int i = 0; i < COMPARE_METHODS.length; i++) {
            System.out.println(i + ". " + COMPARE_METHODS[i]);
        }
        int prefMethod = s.nextInt();
        if (prefMethod == 0) {
            return;
        }
        try{
            System.out.println(college.compareComms(fstCom,scdCom,prefMethod));
        } catch (InvalidInputException iie) {
            System.out.println(iie.getMessage());
        }
    }

    private static void showMenu() {
        System.out.println("\n====== Menu =======");
        for (int i = 0; i < MENU.length; i++) {
            System.out.println(i + ". " + MENU[i]);
        }
        System.out.println("Please choose one of the following options : ");
    }

    private static String enterCollegeName(Scanner s) {
        System.out.print("Please enter a college name: ");
        return s.next();
    }
}
