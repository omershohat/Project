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

    public static String getCollegeName() {
        return collegeName;
    }

    public static void setCollegeName(String collegeName) {
        College.collegeName = collegeName;
    }

    public static Lecturer[] getLecturers() {
        return lecturers;
    }

    public static void setLecturers(Lecturer[] lecturers) {
        College.lecturers = lecturers;
    }

    public static Department[] getStudyDepartments() {
        return studyDepartments;
    }

    public static void setStudyDepartments(Department[] studyDepartments) {
        College.studyDepartments = studyDepartments;
    }

    public static Committee[] getCommittees() {
        return committees;
    }

    public static void setCommittees(Committee[] committees) {
        College.committees = committees;
    }

    public static int getNumOfLecturers() {
        return numOfLecturers;
    }

    public static void setNumOfLecturers(int numOfLecturers) {
        College.numOfLecturers = numOfLecturers;
    }

    public static int getNumOfCommittee() {
        return numOfCommittee;
    }

    public static void setNumOfCommittee(int numOfCommittee) {
        College.numOfCommittee = numOfCommittee;
    }

    public static int getNumOfDeps() {
        return numOfDeps;
    }

    public static void setNumOfDeps(int numOfDeps) {
        College.numOfDeps = numOfDeps;
    }

    public static Scanner getS() {
        return s;
    }

    public static void setS(Scanner s) {
        College.s = s;
    }

    public static DegreeLevel[] getDegrees() {
        return degrees;
    }

    public static void setDegrees(DegreeLevel[] degrees) {
        College.degrees = degrees;
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

    public boolean addLecturer(String name, String id, DegreeLevel degreeLevel, String major, float salary, Department department) {
        Lecturer lecturer = new Lecturer(name,id,degreeLevel,major,salary,department);      // adding a new lecturer for general list
        if (numOfLecturers == lecturers.length) {
            lecturers = copy(lecturers, numOfLecturers == 0 ? 2 : numOfLecturers * 2);
        }
        lecturers[numOfLecturers++] = lecturer;
        return true;
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
