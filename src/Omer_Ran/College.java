package Omer_Ran;

import Omer_Ran.Exceptions.*;

import java.util.ArrayList;

import static Omer_Ran.DegreeLevel.*;
import static Omer_Ran.Utils.findObject;


public class College {
    private String collegeName;
    private ArrayList<Lecturer> lecturers = new ArrayList<>();
    private ArrayList<Department> studyDepartments = new ArrayList<>();
    private ArrayList<Committee> committees = new ArrayList<>();

    public College(String collegeName) {
        this.collegeName = collegeName;
    }

    public ArrayList<Lecturer> getLecturers() {
        return lecturers;
    }

    public ArrayList<Committee> getCommittees() {
        return committees;
    }

    public void addLecturer(String name, String id, DegreeLevel degreeLevel, String major, float salary, Department department, String[] articles, String certifyingInst) throws CollegeExceptions {
        Lecturer lecturer;
        if (articles == null) {
            lecturer = new Lecturer(name, id, degreeLevel, major, salary, department);                              // checking which lecturer to build
        } else if (certifyingInst == null) {
            lecturer = new Doctor(name, id, degreeLevel, major, salary, department, articles);
        } else {
            lecturer = new Professor(name, id, degreeLevel, major, salary, department, articles, certifyingInst);
        }

        if (lecturers.contains(lecturer)) {                                      // finding if lecturer already exists
            throw new ExistException(lecturer);                                                             // exists - EXCEPTION
        }
        if (lecturer.getSalary() < 0) {                                                                     // checking if salary is valid
            throw new InvalidSalaryException(lecturer.getSalary());                                         // not valid - EXCEPTION
        }
        Department dep = (Department) findObject(studyDepartments, lecturer.getDepartment());    // finding department exists
        if (dep == null) {                                                                                  // if not and name is 'none' - department is null
            if (lecturer.getDepartment().getName().equals("none")) {
                lecturer.setDepartment(null);
            } else {
                throw new NotExistException(lecturer.getDepartment());                                      // else - EXCEPTION
            }
        } else {
            lecturer.setDepartment(dep);
        }                                                                                                   // if exists - set department to lecturer
        lecturers.add(lecturer);
    }

    public void addCommittee(String name, Lecturer potentialChairman, DegreeLevel degreeLevel) throws CollegeExceptions {
        Committee committee = new Committee(name, potentialChairman);
        if (committees.contains(committee)) {                        // finding if committee exists already
            throw new ExistException(committee);                                               // exists - EXCEPTION
        }
        Lecturer lec = (Lecturer) findObject(lecturers, potentialChairman);     // finding if the potential chairman exists as a lecturer
        if (lec == null) {
            throw new NotExistException(potentialChairman);                                     // if not - EXCEPTION
        } else if (lec.getDegreeLevel().ordinal() < 2) {                                        // if exists - check if the lecturer qualified to be set as chairman
            throw new QualificationException(potentialChairman, "'s degree level is too low.");                                // if not - EXCEPTION
        }
        committee.setChairman(lec);                                                             // else - set as a chairman
        committee.setDegreeLevel(degreeLevel);
        committees.add(committee);                                                    // adding a new committee for general committees array
    }

    public void addDepartment(String name, int studentCount) {
        Department department = new Department(name, studentCount);
        if (studyDepartments.contains(department)) {                  // finding if department already exists
            throw new ExistException(department);                                           // exists - EXCEPTION
        }
        studyDepartments.add(department);                                                 // else - add a new department to departments general array
    }

    public void assignLecToComm(Lecturer pendingLecturer, Committee pendingCommittee) {
        Committee com = (Committee) findObject(committees, pendingCommittee);            // finding if the committee exists
        if (com == null) {
            throw new NotExistException(pendingCommittee);                                               // if not - EXCEPTION
        }
        Lecturer lec = (Lecturer) findObject(com.getLecturers(), pendingLecturer);     // finding if the lecturer already exists in the committee
        if (lec != null) {
            throw new ExistException(pendingLecturer, pendingCommittee);                                  // if exists - EXCEPTION
        }
        lec = (Lecturer) findObject(lecturers, pendingLecturer);                         // finding if the lecturer exists at all
        if (lec == null) {
            throw new NotExistException(pendingLecturer);                                               // in not - EXCEPTION
        }
        if (lec.getDegreeLevel() != com.getDegreeLevel()) {                                             // if lec's degree level does not match com's degree level - EXCEPTION
            throw new QualificationException(lec, "'s degree level must be: " + com.getDegreeLevel());
        }
        if (com.getChairman() == lec) {                                                                  // checking if the lecturer is already the committee's chairman
            throw new QualificationException(pendingLecturer, pendingCommittee);                         // yes - EXCEPTION
        }
        com.assign(lec);                                                                                 // assigning the lecturer to the committee
    }

    public void assignLecToDep(Lecturer pendingLecturer, Department pendingDepartment) throws InvalidInputException {
        Department dep = (Department) findObject(studyDepartments, pendingDepartment);   // finding if the department exists
        if (dep == null) {
            throw new NotExistException(pendingDepartment);                                         // if not - EXCEPTION
        }
        if (pendingDepartment.getName().equals("none")) {                                           // if dep name is "none", dep = null
            dep = null;
        }
        Lecturer lec = (Lecturer) findObject(lecturers, pendingLecturer);           // finding if the lecturer exists at all
        if (lec == null) {
            throw new NotExistException(pendingLecturer);                                           // in not - EXCEPTION
        }
        if (lec.getDepartment() == dep) {                                                            // finding if the lecturer already exists in the department
            throw new ExistException(pendingLecturer, pendingDepartment);                           // if yes - EXCEPTION
        }
        lec.setDepartment(dep);                                                                     // assigning the lecturer to the department
    }

    public void updateCommChairman(Lecturer potentialChairman, Committee committee) {
        Committee com = (Committee) findObject(committees, committee);                            // finding if committee exists
        if (com == null) {
            throw new NotExistException(committee);                                                             // if not - EXCEPTION
        }
        Lecturer lec = (Lecturer) findObject(com.getLecturers(), potentialChairman);   // finding if the lecturer already exists in the committee
        if (lec != null) {
            throw new ExistException(potentialChairman, committee);                                             // if exists - EXCEPTION
        }
        lec = (Lecturer) findObject(lecturers, potentialChairman);                                // checking if lecturer exists at all
        if (lec == null) {
            throw new NotExistException(potentialChairman);                                                     // if not - EXCEPTION
        }
        if (com.getChairman() == lec) {                                                                         // checking if the lecturer is already the committee's chairman
            throw new QualificationException(potentialChairman, committee);                                     // yes - EXCEPTION
        }
        if (lec.getDegreeLevel().ordinal() < 2) {                                                               // checking if the lecturer qualified to be set as chairman
            throw new QualificationException(potentialChairman, "'s degree level is too low.");                                                // if not - EXCEPTION
        }
        com.setChairman(lec);                                                                            // assigning lecturer as chairman
    }

    public void removeLecturerFromComm(Lecturer lecturer, Committee committee) {
        Committee com = (Committee) findObject(committees, committee);    // finding if committee exists
        if (com == null) {
            throw new NotExistException(committee);                                     // if not - EXCEPTION
        }
        Lecturer lec = (Lecturer) findObject(lecturers, lecturer);        // finding if lecturer exists
        if (lec == null) {
            throw new NotExistException(lecturer);                                      // if not - EXCEPTION
        } else {
            com.removeLecturer(lec);                                                    // if exists - check if exists in committee and remove
        }
    }

    public float getDepLecturersIncome(Department department) {
        Department dep = (Department) findObject(studyDepartments, department);       // find if department exists
        if (dep != null) {
            return dep.getLecturersIncome();                                                    // if exists - calculate and return average income
        } else {
            throw new NotExistException(department);                                            // if not - EXCEPTION
        }
    }

    public String compareLecArticles(Lecturer fstLec, Lecturer scdLec) {
        Doctor doc1;
        Doctor doc2;
        Lecturer lec1 = (Lecturer) findObject(lecturers, fstLec);       // finding if the lecturer exists at all
        if (lec1 == null) {
            throw new NotExistException(fstLec);                                        // in not - EXCEPTION
        } else if (lec1.getDegreeLevel().ordinal() < 2) {                               // checking lecturer degree level
            throw new QualificationException(fstLec, "'s degree level is too low.");                                   // if low - EXCEPTION
        } else {
            doc1 = (Doctor) lec1;
        }
        Lecturer lec2 = (Lecturer) findObject(lecturers, scdLec);       // finding if the lecturer exists at all
        if (lec2 == null) {
            throw new NotExistException(scdLec);                                        // in not - EXCEPTION
        } else if (lec2.getDegreeLevel().ordinal() < 2) {                               // checking lecturer degree level
            throw new QualificationException(scdLec, "'s degree level is too low.");                                   // if low - EXCEPTION
        } else {
            doc2 = (Doctor) lec2;
        }
        if (doc1.equals(doc2)) {
            return "This is the same lecturer.";
        }
        int res = doc1.compareTo(doc2);
        if (res > 0) {                                                // results
            return doc1.getName() + " has more articles than " + doc2.getName() + ".";
        } else if (res < 0) {
            return doc2.getName() + " has more articles than " + doc1.getName() + ".";
        } else {
            return "Both lecturers have the same number of articles.";
        }
    }

    public String compareComms(Committee fstCom, Committee scdCom, int prefMethod) {
        Committee com1 = (Committee) findObject(committees, fstCom);    // finding if first committee exists
        if (com1 == null) {
            throw new NotExistException(fstCom);                                        // if not - EXCEPTION
        }
        Committee com2 = (Committee) findObject(committees, scdCom);    // finding if second committee exists
        if (com2 == null) {
            throw new NotExistException(scdCom);                                        // if not - EXCEPTION
        }
        if (com1.equals(com2)) {                                        // checking if the same committee
            return "This is the same committee";
        }
        int res;
        if (prefMethod == 1) {                                                  // comparing by members amount
            CompareComByMemAmount comparator = new CompareComByMemAmount();
            res = comparator.compare(com1, com2);
            if (res > 0) {
                return com1.getName() + " has more members in it than " + com2.getName() + ".";
            } else if (res < 0) {
                return com2.getName() + " has more members in it than " + com1.getName() + ".";
            } else {
                return "Both committees have the same amount of members.";
            }
        } else if (prefMethod == 2) {                                           // comparing by sum of articles
            CompareComBySumArticles comparator = new CompareComBySumArticles();
            res = comparator.compare(com1, com2);
            if (res > 0) {
                return com1.getName() + " has more articles overall than " + com2.getName() + ".";
            } else if (res < 0) {
                return com2.getName() + " has more articles overall than " + com1.getName() + ".";
            } else {
                return "Both committees have the same amount of articles.";
            }
        } else {                                                                   // if compare method invalid - EXCEPTION
            throw new InvalidInputException("Invalid method option.");
        }
    }

    public void cloneCom(Committee originalCom) throws CloneNotSupportedException {
        Committee com1 = (Committee) findObject(committees, originalCom);   // checking if committee exists
        if (com1 == null) {
            throw new NotExistException(originalCom);                                       // if not - EXCEPTION
        }
        Committee comCloned = com1.clone();         // cloning
        committees.add(comCloned);               // adding clone to committees
    }

    public ArrayList<Department> getDepartments() {
        return studyDepartments;
    }

    public void init() {
        Department kokod = new Department("kokod", 12);
        studyDepartments.add(kokod);

        Doctor omer = new Doctor("omer", "315854091", DOCTOR, "ART", 54.1f, studyDepartments.get(0), new String[]{"1", "2", "3", "4", "5"});
        omer.setDepartment(kokod);
        lecturers.add(omer);

        Professor elon = new Professor("elon", "322819123", PROFESSOR, "KAKI", 500.67f, studyDepartments.get(0), new String[]{"1", "2"}, "police");
        elon.setDepartment(kokod);
        lecturers.add(elon);

        Doctor ran = new Doctor("ran", "3151", MASTERS, "ART", 32.3f, studyDepartments.get(0), new String[]{"1", "2"});
        ran.setDepartment(kokod);
        lecturers.add(ran);

        Doctor koz = new Doctor("koz", "12345", DOCTOR, "ART", 36.31f, studyDepartments.get(0), new String[]{"1", "2"});
        koz.setDepartment(kokod);
        lecturers.add(koz);

        Committee koko1 = new Committee("koko1", lecturers.get(0), DOCTOR);
        koko1.setChairman(elon);
        committees.add(koko1);

        Committee koko2 = new Committee("koko2", lecturers.get(0), MASTERS);
        koko2.setChairman(koz);
        committees.add(koko2);

        assignLecToComm(omer, koko1);
        assignLecToComm(ran, koko2);

        Department lolod = new Department("lolod", 12);
        studyDepartments.add(lolod);


    }
}
