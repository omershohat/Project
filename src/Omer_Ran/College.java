package Omer_Ran;

import static Omer_Ran.ActionStatus. *;
import static Omer_Ran.Utils.findObject;
import static Omer_Ran.Utils.resizeArr;

public class College {
    private String collegeName;
    private Lecturer[] lecturers = new Lecturer[0];
    private Department[] studyDepartments = new Department[0];
    private Committee[] committees = new Committee[0];
    private int numOfLecturers;
    private int numOfCommittee;
    private int numOfDeps;

    public College(String collegeName) {
        this.collegeName = collegeName;
    }

    public Lecturer[] getLecturers() {
        return lecturers;
    }

    public Committee[] getCommittees() {
        return committees;
    }

    public int getNumOfLecturers() {
        return numOfLecturers;
    }

    public int getNumOfCommittee() {
        return numOfCommittee;
    }

    public ActionStatus addLecturer(String name, String id, DegreeLevel degreeLevel, String major, float salary, Department department) {
        Lecturer lecturer = new Lecturer(name, id, degreeLevel, major, salary, department);
        if (findObject(lecturers, numOfLecturers, lecturer) != null) {                                      // finding if lecturer already exists
            return LECTURER_EXIST;                                                                          // exists - ERROR
        }
        if (lecturer.getSalary() < 0) {                                                                     // checking if salary is valid
            return INVALID_SALARY;                                                                          // not valid - ERROR
        }
        Department dep = (Department) findObject(studyDepartments, numOfDeps, lecturer.getDepartment());    // finding department exists
        if (dep == null) {                                                                                  // if not and name is 'none' - department is null
            if (lecturer.getDepartment().getName().equals("none")) {
                lecturer.setDepartment(null);
            } else {
                return DEPARTMENT_NOT_EXIST;
            }                                                                                               // else - ERROR
        } else {
            lecturer.setDepartment(dep);
        }                                                                                                   // if exists - set department to lecturer
        return addLecturerFinal(lecturer);                                                                  // adding a new lecturer for general lecturers array
    }

    public ActionStatus addLecturerFinal(Lecturer pendingLecturer) {
        if (numOfLecturers == lecturers.length) {                                                           // making sure there's place for new lecturer
            lecturers = (Lecturer[]) resizeArr(lecturers);
        }
        lecturers[numOfLecturers++] = pendingLecturer;                                                      // inserting lecturer by index to lecturers array
        return SUCCESS;
    }

    public ActionStatus addCommittee(String name, Lecturer potentialChairman) {
        Committee committee = new Committee(name, potentialChairman);
        if (findObject(committees, numOfCommittee, committee) != null) {                        // finding if committee exists already
            return COMMITTEE_EXIST;                                                             // exists - ERROR
        }
        Lecturer lec = (Lecturer) findObject(lecturers, numOfLecturers, potentialChairman);     // finding if the potential chairman exists as a lecturer
        if (lec == null) {
            return LECTURER_NOT_EXIST;                                                          // if not - ERROR
        } else if (lec.getDegreeLevel().ordinal() < 2) {                                        // if exists - check if the lecturer qualified to be set as chairman
            return DEGREE_LEVEL_TOO_LOW;                                                        // if not - ERROR
        }
        committee.setChairman(lec);                                                             // else - set at a chairman
        return addCommitteeFinal(committee);                                                    // adding a new committee for general committees array
    }

    public ActionStatus addCommitteeFinal(Committee pendingCommittee) {
        if (numOfCommittee == committees.length) {                                              // making sure there's place for new committee
            committees = (Committee[]) resizeArr(committees);
        }
        committees[numOfCommittee++] = pendingCommittee;                                        // inserting committee by index to committee array
        return SUCCESS;
    }

    public ActionStatus addDepartment(String name, int studentCount) {
        Department department = new Department(name, studentCount);
        if (findObject(studyDepartments, numOfDeps, department) != null) {                  // finding if department already exists
            return DEPARTMENT_EXIST;                                                        // exists - ERROR
        }
        return addDepartmentFinal(department);                                              // else - add a new department to departments general array
    }

    public ActionStatus addDepartmentFinal(Department pendingDepartment) {
        if (numOfDeps == studyDepartments.length) {                                         // making sure there is place for new department
            studyDepartments = (Department[]) resizeArr(studyDepartments);
        }
        studyDepartments[numOfDeps++] = pendingDepartment;                                  // inserting department to departments array by index
        return SUCCESS;
    }

    public ActionStatus assignLecToComm(Lecturer pendingLecturer, Committee pendingCommittee) {
        Committee com = (Committee) findObject(committees, numOfCommittee, pendingCommittee);                   // finding if the committee exists
        if (com == null) {
            return COMMITTEE_NOT_EXIST;                                                                         // if not - ERROR
        }
        Lecturer lec = (Lecturer) findObject(com.getLecturers(), com.getNumOfLecturers(), pendingLecturer);     // finding if the lecturer already exists in the committee
        if (lec != null) {
            return LECTURER_EXIST_IN_COMM;                                                                      // if exists - ERROR
        }
        lec = (Lecturer) findObject(lecturers, numOfLecturers, pendingLecturer);                                // finding if the lecturer exists at all
        if (lec == null) {
            return LECTURER_NOT_EXIST;                                                                          // in not - ERROR
        }
        if (com.getChairman() == lec) {                                                                         // checking if the lecturer is already the committee's chairman
            return LECTURER_IS_CHAIRMAN;                                                                        // yes - ERROR
        }
        return com.assign(lec);                                                                                 // assigning the lecturer to the committee
    }

    public ActionStatus assignLecToDep(Lecturer pendingLecturer, Department pendingDepartment) {
        Department dep = (Department) findObject(studyDepartments, numOfDeps, pendingDepartment);              // finding if the department exists
        if (dep == null) {
            return DEPARTMENT_NOT_EXIST;                                                                        // if not - ERROR
        }
        if (pendingDepartment.getName().equals("none")) {
            dep = null;
        }
        Lecturer lec = (Lecturer) findObject(lecturers, numOfLecturers, pendingLecturer);                       // finding if the lecturer exists at all
        if (lec == null) {
            return LECTURER_NOT_EXIST;                                                                          // in not - ERROR
        }
        if(lec.getDepartment() == dep) {                                                                   // finding if the lecturer already exists in the department
            return LECTURER_IN_DEP;
        }
        return lec.setDepartment(dep);                                                                                 // assigning the lecturer to the department
    }

    public ActionStatus updateCommChairman(Lecturer potentialChairman, Committee committee) {
        Committee com = (Committee) findObject(committees,numOfCommittee,committee);                            // finding if committee exists
        if (com == null) {
            return COMMITTEE_NOT_EXIST;                                                                         // if not - ERROR
        }
        Lecturer lec = (Lecturer) findObject(com.getLecturers(), com.getNumOfLecturers(), potentialChairman);   // finding if the lecturer already exists in the committee
        if (lec != null) {
            return LECTURER_EXIST_IN_COMM;                                                                      // if exists - ERROR
        }
        lec = (Lecturer) findObject(lecturers,numOfLecturers,potentialChairman);                                // checking if lecturer exists at all
        if (lec == null) {
            return LECTURER_NOT_EXIST;                                                                          // if not - ERROR
        }
        if (com.getChairman() == lec) {                                                                         // checking if the lecturer is already the committee's chairman
            return LECTURER_IS_CHAIRMAN;                                                                        // yes - ERROR
        }
        if (lec.getDegreeLevel().ordinal() < 2) {                                                               // checking if the lecturer qualified to be set as chairman
            return DEGREE_LEVEL_TOO_LOW;                                                                        // if not - ERROR
        }
        return com.setChairman(lec);                                                                            // assigning lecturer as chairman
    }

    public ActionStatus removeLecturerFromComm(Lecturer lecturer, Committee committee) {
        Committee com = (Committee) findObject(committees,numOfCommittee,committee);            // finding if committee exists
        if (com == null) {
            return COMMITTEE_NOT_EXIST;                                                         // if not - ERROR
        }
        Lecturer lec = (Lecturer) findObject(lecturers,numOfLecturers,lecturer);
        return com.removeLecturer(lec);                                                    // if exists - check if exists in committee and remove
    }

    public float getDepLecturersIncome(Department department) {
        Department dep = (Department) findObject(studyDepartments,numOfDeps, department);       // find if department exists
        if (dep != null) {
            return dep.getLecturersIncome();                                                    // if exists - calculate and return average income
        }
        else {
            return 0;                                                                           // if not - return 0
        }
    }
}
