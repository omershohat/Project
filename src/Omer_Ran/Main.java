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
                case "3" -> assignLecturerToComm(college);           //
                case "4" -> assignLecturerToDep(college);            //
                case "5" -> updateCommChairman(college);             //
                case "6" -> removeLecturerFromComm(college);         //
                case "7" -> addStudyDepartment(college);             // v
                case "8" -> getAllLecturersIncome(college);          //
                case "9" -> getDepLecturersIncome(college);          //
                case "10" -> showLecturers(college);                 //
                case "11" -> showCommittees(college);                //
                default -> System.out.println("Unexpected value!");
            }
        } while (!userChose.equals("0"));
    }

    private static void addLecturer(College college) {
        System.out.println("Enter lecturer name: ");                                                        // reading name of new lecturer
        System.out.println("(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }
        if (isExist(name, college.getNumOfLecturers(), college.getLecturers())) {                           // checking for existing lecturer
            System.out.println(name + " already exist...");                                                 // if not - continue
            addLecturer(college);
            return;
        }

        String id = new String();                                                                           // reading an ID for lecturer
        boolean valid = false;
        while (!valid) {
            System.out.println("Enter lecturer's ID: ");
            System.out.println("(enter '0' to return to menu)");
            id = s.next();
            if (id.equals("0")) {
                return;
            }
            else if (id.length()==9) {                                                                      // checking if is valid
                valid = true;
            }
            else {
                System.out.println("Invalid ID. Make sure to enter exactly 9 digits.");
            }
        }

        DegreeLevel[] degreeLevels = DegreeLevel.values();                                                  // reading degree level of the lecturer
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
            else if (1 <= degreeChoice && degreeChoice <= 4) {                                              // checking if is valid
                degreeLevel = degreeLevels[degreeChoice-1];
                valid = true;
            }
            else {
                System.out.println("Invalid choice.");
            }
        }

        String major;                                                                                       // reading major of lecturer
        System.out.println("Enter lecturer's major: ");
        System.out.println("(enter '0' to return to menu)");
        major = s.next();
        if (major.equals("0")) {
            return;
        }

        float salary;                                                                                       // reading salary of lecturer
        System.out.println("Enter lecturer's salary: ");
        System.out.println("(enter '0' to return to menu)");
        salary = s.nextFloat();
        if (salary == 0) {
            return;
        }
        s.nextLine();

        String departmentName;                                                                              // reading the lecturer's department name
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
                for (int i = 0; i < college.getNumOfDeps(); i++) {                                          // checking for existing department
                    if (college.getStudyDepartments()[i].getName().equals(departmentName)) {                // if exist - continue
                        department = college.getStudyDepartments()[i];
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
        college.addLecturer(name,id,degreeLevel,major,salary,department);
        System.out.println("The new lecturer '" + name + "' has been added successfully!");
        return;
    }

    private static void addCommittee(College college) {
        System.out.println("Enter a committee name: ");                                     // reading name of new committee
        System.out.println("(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }
        if (isExist(name, college.getNumOfCommittee(), college.getCommittees())) {          // checking for existing committee with that name
            System.out.println(name + " already exist...");                                 // if not - continue
            addCommittee(college);
            return;
        }

        String lecturerName;                                                                // reading a potential chairman (lecturer)
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
            for (Lecturer selectedLecturer : college.getLecturers()) {                      // checking for existing lecturer
                if (selectedLecturer.getName().equals(lecturerName)) {                      // if exist - continue
                    notExist = false;
                    if (selectedLecturer.getDegreeLevel().ordinal() >= 2) {                 // checking if is valid for the role
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
        college.addCommittee(name, chairman);                                               // adding a new committee to the general list.
        System.out.println("The new committee '" + name + "' has been added successfully!");
        return;
    }

    private static void assignLecturerToComm(College college) {
        Lecturer selectedLecturer = null;
        boolean existLec = false;
        while (!existLec) {
            System.out.println("Choose a lecturer to assign: ");                // reading a lecturer name
            System.out.println("(enter '0' to return to menu)");
            String lecturerName = s.nextLine();
            if (lecturerName.equals("0")) {
                return;
            }
            for (Lecturer l : college.getLecturers()) {                                      // checking for existing lecturer
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
            for (Committee c : college.getCommittees()) {                                    // checking for existing committee
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

    private static void assignLecturerToDep(College college){
        Lecturer selectedLecturer = null;
        boolean existLec = false;
        while (!existLec) {
            System.out.println("Choose a lecturer to assign: ");                                // reading a lecturer name
            System.out.println("(enter '0' to return to menu)");
            String lecturerName = s.nextLine();
            if (lecturerName.equals("0")) {
                return;
            }
            for (Lecturer l : college.getLecturers()) {                                         // checking for existing lecturer
                if (l.getName().equals(lecturerName)) {                                         // if exist - continue
                    selectedLecturer = l;
                    existLec = true;
                    break;
                }
            }
            if (!existLec) {
                System.out.println("Lecturer does not exist...");
            }
        }
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
        if (existLec && existDep) {
            selectedLecturer.setDepartment(selectedDepartment);                                 // assigning the lecturer to the department
            System.out.println(selectedLecturer.getName() + " was assigned to " + selectedDepartment.getName() + " department successfully!");
            return;
        }
    }

    private static void updateCommChairman(College college) {
        Committee selectedCommittee = new Committee();
        boolean exist = false;
        while (!exist) {
            System.out.println("Enter a committee: ");                  // reading a committee name
            System.out.println("(enter '0' to return to menu)");
            String committeeName = s.nextLine();
            if (committeeName.equals("0")) {
                return;
            }
            for (Committee c : college.getCommittees()) {                            // checking for existing committee
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
            for (int i = 0; i < college.getNumOfLecturers(); i++) {
                currentLec = college.getLecturers()[i];
                if (currentLec.getName().equals(lecturerName)) {
                    selectedCommittee.setChairman(currentLec);
                    System.out.println(lecturerName + "was set to be the chairman of '" + selectedCommittee.getName() + "' committee.");
                    return;
                }
            }
            System.out.println("Lecturer does not exist...");
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

    private static void addStudyDepartment(College college) {
        System.out.println("Enter a study department name: ");                          // reading a study department name
        System.out.println("(enter '0' to return to menu)");
        String name = s.nextLine();
        if (name.equals("0")) {
            return;
        }
        if (isExist(name, college.getNumOfDeps(), college.getStudyDepartments())) {     // checking for existing department
            System.out.println(name + " already exist...");
            addStudyDepartment(college);
            return;
        }

        System.out.println("Enter the number of students in this department: ");        // reading the number of student in department
        int studentCount = s.nextInt();
        college.addDepartment(name, studentCount);                                      // adding a new study department to general list
        System.out.println("The new study department '" + name + "' has been added successfully!");
        return;
    }

    private static void getAllLecturersIncome(College college) {
        float sum = 0.0f;                                       // calculating average income of all lecturers in college
        for (int i = 0; i < college.getNumOfLecturers() ; i++) {
            sum += college.getLecturers()[i].getSalary();
        }
        System.out.println("The average income of all lecturers in the college is: " + (sum)/(college.getNumOfLecturers()));
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
        for (int i = 0; i < selectedDepartment.getNumOfLecturers() ; i++) {                     // calculating average
            sum += depLecturers[i].getSalary();
        }
        System.out.println("The average income of lecturers in '" + selectedDepartment.getName() +"' department is: " + (sum)/(selectedDepartment.getNumOfLecturers()));
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
