package Omer_Ran;

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
            "Committees details"
    };

    private static Scanner s;

    public static void main(String[] args) {
        s = new Scanner(System.in);
        College college = enterCollegeName(s);
        run(college);
        s.close();
    }

    private static College enterCollegeName(Scanner s) {
        System.out.print("Please enter a college name: ");
        College college = new College(s.next());
        return college;
    }

    public static void run(College college) {
        s = new Scanner(System.in);
        String userChose;
        do {
            userChose = showMenu(s);
            s.nextLine();
            switch (userChose) {
                case "0" -> System.out.println("Done... Bye");
                case "1" -> addLecturer(college);                    // v
                case "2" -> addCommittee(college);                   // v
                case "3" -> assignLecturerToComm(college);           // v
                case "4" -> assignLecturerToDep(college);            // v
                case "5" -> updateCommChairman(college);             // v
                case "6" -> removeLecturerFromComm(college);         //
                case "7" -> addStudyDepartment(college);             // v
                case "8" -> getAllLecturersIncome(college);          //
                case "9" -> getDepLecturersIncome(college);          //
                case "10" -> showLecturers(college);                 // v
                case "11" -> showCommittees(college);                // v
                default -> System.out.println("Unexpected value!");
            }
        } while (!userChose.equals("0"));
    }

    private static void addLecturer(College college) {
        System.out.println("Enter lecturer name: ");                                // reading name of new lecturer
        System.out.println("(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }

        System.out.println("Enter lecturer's ID: ");
        System.out.println("(enter '0' to return to menu)");
        String id = s.next();

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
            } else if (1 <= degreeChoice && degreeChoice <= 4) {                    // checking if choice is valid
                degreeLevel = degreeLevels[degreeChoice - 1];
                valid = true;
            } else {
                System.out.println(ActionStatus.INVALID_CHOICE);
            }
        }

        String major;                                                               // reading major of lecturer
        System.out.println("Enter lecturer's major: ");
        System.out.println("(enter '0' to return to menu)");
        major = s.next();
        if (major.equals("0")) {
            return;
        }

        float salary;                                                               // reading salary of lecturer
        System.out.println("Enter lecturer's salary: ");
        System.out.println("(enter '0' to return to menu)");
        salary = s.nextFloat();
        if (salary == 0) {
            return;
        }
        s.nextLine();

        String departmentName;                                                      // reading the lecturer's department name
        System.out.println("Enter lecturer's department (if not assigned to any - type 'none'): ");
        System.out.println("(enter '0' to return to menu)");
        departmentName = s.nextLine();
        if (departmentName.equals("0")) {
            return;
        }
        Department department = new Department(departmentName);                                         // creating a pending department

        ActionStatus res = college.addLecturer(name, id, degreeLevel, major, salary, department);       // trying to add lecturer
        if (!res.equals(ActionStatus.SUCCESS)) {                                                        // printing result
            System.out.println("Failed to add the lecturer '" + name + "', Error: " + res);
            if (res.equals(ActionStatus.LECTURER_EXIST)) {
                addLecturer(college);
            }
            return;
        }
        System.out.println("The new lecturer '" + name + "' has been added successfully!");
    }

    private static void addCommittee(College college) {
        System.out.println("Enter a committee name: ");                                     // reading name of new committee
        System.out.println("(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }
        System.out.println("Enter chairman's name: ");                                      // reading a potential chairman's name
        System.out.println("(enter '0' to return to menu)");
        String lecturerName = s.nextLine();
        if (lecturerName.equals("0")) {
            return;
        }
        Lecturer chairman = new Lecturer(lecturerName);

        ActionStatus res = college.addCommittee(name, chairman);                                        // trying to add committee
        if (!res.equals(ActionStatus.SUCCESS)) {                                                        // printing result
            System.out.println("Failed to add the committee '" + name + "', Error: " + res);
            return;
        }
        System.out.println("The new committee '" + name + "' has been added successfully!");
    }

    private static void assignLecturerToComm(College college) {
        System.out.println("Choose a lecturer to assign: ");                // reading a lecturer name
        System.out.println("(enter '0' to return to menu)");
        String lecturerName = s.nextLine();
        if (lecturerName.equals("0")) {
            return;
        }
        Lecturer pendingLecturer = new Lecturer(lecturerName);

        System.out.println("Enter a committee: ");                          // reading a committee name
        System.out.println("(enter '0' to return to menu)");
        String committeeName = s.nextLine();
        if (committeeName.equals("0")) {
            return;
        }
        Committee pendingCommittee = new Committee(committeeName);

        ActionStatus res = college.assignLecToComm(pendingLecturer, pendingCommittee);       // trying to assign
        if (!res.equals(ActionStatus.SUCCESS)) {                                            // printing result
            System.out.println("Failed to assign '" + lecturerName + "' to '" + committeeName + "' committee, Error: " + res);
        } else {
            System.out.println("'" + lecturerName + "' was assigned to '" + committeeName + "' committee successfully!");
        }
    }

    private static void assignLecturerToDep(College college) {
        System.out.println("Choose a lecturer to assign: ");                // reading a lecturer name
        System.out.println("(enter '0' to return to menu)");
        String lecturerName = s.nextLine();
        if (lecturerName.equals("0")) {
            return;
        }
        Lecturer pendingLecturer = new Lecturer(lecturerName);

        System.out.println("Enter a department: ");                          // reading a department name
        System.out.println("(enter '0' to return to menu)");
        String departmentName = s.nextLine();
        if (departmentName.equals("0")) {
            return;
        }
        Department pendingDepartment = new Department(departmentName);

        ActionStatus res = college.assignLecToDep(pendingLecturer, pendingDepartment);       // trying to assign
        if (!res.equals(ActionStatus.SUCCESS)) {                                            // printing result
            System.out.println("Failed to assign '" + lecturerName + "' to '" + departmentName + "' department, Error: " + res);
        } else {
            System.out.println("'" + lecturerName + "' was assigned to '" + departmentName + "' department successfully!");
        }
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

        ActionStatus res = college.updateCommChairman(lecturer, committee);             // trying to assign a new chairman
        if (!res.equals(ActionStatus.SUCCESS)) {                                        // printing result
            System.out.println("Failed to update '" + lecturerName + "' as the new chairman of '" + committeeName + "' committee, Error: " + res);
        } else {
            System.out.println("'" + lecturerName + "' was set to be the chairman of '" + committeeName + "' committee.");
        }
    }

    private static void removeLecturerFromComm(College college) {
        Committee selectedCommittee = new Committee();                      // reading a committee to access
        boolean exist = false;
        while (!exist) {
            System.out.println("Enter a committee: ");
            System.out.println("(enter '0' to return to menu)");
            String committeeName = s.nextLine();
            if (committeeName.equals("0")) {
                return;
            }
            for (Committee c : college.getCommittees()) {                                // checking for existing committee
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
            } else if (selectedCommittee.removeLecturer(lecturerName)) {     // activating removeLecturer(lecturerName) from Committee.class
                done = true;                                               // if 'true' it means lecturer was removed
            } else {
                System.out.println("Lecturer does not exist...");
            }
        }
        System.out.println(selectedLecturer.getName() + " was removed from " + selectedCommittee.getName() + " committee successfully!");
    }

    private static void addStudyDepartment(College college) {
        System.out.println(
                "Enter a study department name: \n" +
                        "(enter '0' or 'none' to return to menu)");                                          // reading a study department name
        String name = s.nextLine();
        if (name.equals("0") || name.equals("none")) {
            return;
        }
        System.out.println(
                "Enter the number of students in this department: \n" +
                        "(enter '0' to return to menu)");                                                   // reading the number of student in department
        int studentCount = s.nextInt();
        s.nextLine();

        ActionStatus res = college.addDepartment(name, studentCount);                                   // trying to add studyDepartment
        if (!res.equals(ActionStatus.SUCCESS)) {                                                        // printing result
            System.out.println("Failed to add the department '" + name + "', Error: " + res);
            if (res.equals(ActionStatus.DEPARTMENT_EXIST)) {
                addStudyDepartment(college);
            }
            return;
        }
        System.out.println("The new study department '" + name + "' has been added successfully!");
    }

    private static void getAllLecturersIncome(College college) {
        float sum = 0.0f;                                       // calculating average income of all lecturers in college
        for (int i = 0; i < college.getNumOfLecturers(); i++) {
            sum += college.getLecturers()[i].getSalary();
        }
        System.out.println("The average income of all lecturers in the college is: " + (sum) / (college.getNumOfLecturers()));
    }

    private static void getDepLecturersIncome(College college) {
        Department selectedDepartment = new Department();
        boolean existDep = false;
        while (!existDep) {
            System.out.println("Enter a department: ");                                         // reading a department name
            System.out.println("(enter '0' to return to menu)");
            String departmentName = s.nextLine();
            if (departmentName.equals("0")) {
                return;
            }
            for (Department d : college.getStudyDepartments()) {                                // checking for existing department
                if (d.getName().equals(departmentName)) {                                       // if exist - continue
                    selectedDepartment = d;
                    existDep = true;
                    break;
                }
            }
            if (!existDep) {
                System.out.println("Department does not exist...");
            }
        }
        float sum = 0;
        Lecturer[] depLecturers = selectedDepartment.getLecturers();
        for (int i = 0; i < selectedDepartment.getNumOfLecturers(); i++) {                     // calculating average
            sum += depLecturers[i].getSalary();
        }
        System.out.println("The average income of lecturers in '" + selectedDepartment.getName() + "' department is: " + (sum) / (selectedDepartment.getNumOfLecturers()));
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
                System.out.println(college.getCommittees()[i]);
            }
        }
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
