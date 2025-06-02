package Omer_Ran;

import static Omer_Ran.Utils.resizeArr;

public class Department implements Nameable {
    private String name;
    private int studentCount;
    private Lecturer[] lecturers = new Lecturer[0];
    private  int numOfLecturers;

    public Department(String departmentName, int studentCount) {
        name = departmentName;
        this.studentCount = studentCount;
    }

    public Department(String departmentName) {
        name = departmentName;
    }

    public String getName() {
        return name;
    }

    public void assign(Lecturer lecturer) {
        if (numOfLecturers == lecturers.length) {                   // making sure there is place for new lecturer
            lecturers = (Lecturer[]) resizeArr(lecturers);
        }
        lecturers[numOfLecturers++] = lecturer;                     // inserting lecturer to department array by index
    }

    public float getLecturersIncome() {
        float sum = 0f;
        for (Lecturer lec : lecturers) {                            // summing all department's lecturers income
            if (lec != null ) {
                sum += lec.getSalary();
            }
            else {
                break;
            }
        }
        return (sum) / numOfLecturers;                              // returning the average
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
