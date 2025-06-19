package Omer_Ran;

import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Nameable, Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int studentCount;
    private final ArrayList<Lecturer> lecturers = new ArrayList<>();

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
        lecturers.add(lecturer);                     // inserting lecturer to department array by index
    }

    public float getLecturersIncome() {
        float sum = 0f;
        for (Lecturer lec : lecturers) {                            // summing all department's lecturers income
            if (lec != null) {
                sum += lec.getSalary();
            } else {
                break;
            }
        }
        return (sum) / lecturers.size();                              // returning the average
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Department dep) {
            return dep.name.equals(name);
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
