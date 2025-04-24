package Omer_Ran;

import static Omer_Ran.Utils.resizeArr;

public class Department {
    private String name;
    private int studentCount;
    private Lecturer[] lecturers = new Lecturer[0];
    private  int numOfLecturers;

    public Department(String departmentName, int studentCount) {
        name = departmentName;
        this.studentCount = studentCount;
    }

    public Department() {
    }

    public Department(String departmentName) {
        name = departmentName;
    }

    public String getName() {

        return name;
    }

    public void addLecturer(Lecturer lecturer) {
        if (numOfLecturers == lecturers.length) {
            lecturers = (Lecturer[]) resizeArr(lecturers);
        }
        lecturers[numOfLecturers++] = lecturer;
        return;
    }

    private static Lecturer[] copy(Lecturer[] arr, int size) {
        Lecturer[] temp = new Lecturer[size];
        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        return temp;
    }

    public Lecturer[] getLecturers() {
        return lecturers;
    }

    public  int getNumOfLecturers() {
        return numOfLecturers;
    }

    public ActionStatus assign(Lecturer lecturer) {
        if (numOfLecturers == lecturers.length) {                   // making sure there is place for new lecturer
            lecturers = (Lecturer[]) resizeArr(lecturers);
        }
        lecturers[numOfLecturers++] = lecturer;                     // inserting lecturer to department array by index
        return ActionStatus.SUCCESS;
    }
}
