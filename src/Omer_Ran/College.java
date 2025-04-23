package Omer_Ran;

import java.awt.image.ImageProducer;

import static Omer_Ran.Utils.*;
import static Omer_Ran.ActionStatus.*;

enum DegreeLevel {
    BACHELORS("BA"),             // First degree
    MASTERS("MA"),                 // Second degree
    DOCTOR("Dr."),             // Dr.
    PROFESSOR("PhD.");             // Professor
    private final String description;

    DegreeLevel(String description) {this.description =description;}

    @Override
    public String toString() {return description;}
}

enum ActionStatus {
    SUCCESS("Success"),
    LECTURER_EXIST("Lecturer is already exist..."),
    LECTURER_NOT_EXIST("Lecturer does not exist..."),
    INVALID_CHOICE("Invalid choice."),
    DEPARTMENT_NOT_EXIST("Department does not exist..."),
    INVALID_SALARY("Invalid salary input. Salary must be 0 or above.");

    private final String description;

    ActionStatus(String description) {this.description =description;}

    @Override
    public String toString() {return description;}
}

public class College {
    private static String collegeName;
    private static Lecturer[] lecturers = new Lecturer[0];
    private static Department[] studyDepartments = new Department[0];
    private static Committee[] committees = new Committee[0];
    private static int numOfLecturers;
    private static int numOfCommittee;
    private static int numOfDeps;
    private static DegreeLevel[] degrees = DegreeLevel.values();



    public College(String collegeName) {
        this.collegeName = collegeName;
    }

    public static Lecturer[] getLecturers() {
        return lecturers;
    }

    public static Department[] getStudyDepartments() {
        return studyDepartments;
    }

    public static Committee[] getCommittees() {
        return committees;
    }

    public static int getNumOfLecturers() {
        return numOfLecturers;
    }

    public static int getNumOfCommittee() {
        return numOfCommittee;
    }

    public static int getNumOfDeps() {
        return numOfDeps;
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

    public ActionStatus addLecturer(String name, String id, DegreeLevel degreeLevel, String major, float salary, Department department) {
        Lecturer lecturer = new Lecturer(name,id,degreeLevel,major,salary,department);
        return addLecturerFinal(lecturer);                                                      // adding a new lecturer for general list
    }

    public ActionStatus addLecturerFinal(Lecturer lecturer) {
        if (isExist(lecturers,numOfLecturers,lecturer)) {return LECTURER_EXIST;}
        if (lecturer.getSalary() < 0) {return INVALID_SALARY;}
        if (!isExist(studyDepartments, numOfDeps, lecturer.getDepartment())) {return DEPARTMENT_NOT_EXIST;}

        if (numOfLecturers == lecturers.length) {
            lecturers = (Lecturer[]) resizeArr(lecturers);
        }
        lecturers[numOfLecturers++] = lecturer;
        return SUCCESS;
    }


    public boolean addCommittee(String name, Lecturer chairman) {
        Committee committee = new Committee(name, chairman);                              // adding a new committee to the general list.
        if (numOfCommittee == committees.length) {
            committees = copy(committees, numOfCommittee == 0 ? 2 : numOfCommittee * 2);
        }
        committees[numOfCommittee++] = committee;
        return true;
    }

    public boolean addDepartment(String name, int studentCount) {
        Department department = new Department(name, studentCount);
        if (numOfDeps == studyDepartments.length) {
            studyDepartments = copy(studyDepartments, numOfDeps == 0 ? 2 : numOfDeps * 2);
        }
        studyDepartments[numOfDeps++] = department;
        return true;
    }
}
