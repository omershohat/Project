package Omer_Ran;

public class Department {
    private String name;
    private int studentCount;
    private Lecturer[] lecturers = new Lecturer[0];
    private static int numOfLecturers;

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
            lecturers = copy(lecturers, numOfLecturers == 0 ? 2 : numOfLecturers * 2);
        }
        lecturers[numOfLecturers++] = lecturer;
        return;
    }

    private static Lecturer[] copy(Lecturer[] arr, int size) {
        Lecturer[] temp = new Lecturer[size];
        for (int i = 0; i < arr.length ; i++) {
            temp[i] = arr[i];
        }
        return temp;
    }

    public Lecturer[] getLecturers() {
        return lecturers;
    }

    public static int getNumOfLecturers() {
        return numOfLecturers;
    }
}
