package Omer_Ran;

import Omer_Ran.Exceptions.NotExistException;

import java.util.ArrayList;

public class Lecturer implements Nameable {
    private String name;
    private String id;
    private DegreeLevel degreeLevel;
    private String major;
    private float salary;
    private Department department;
    private ArrayList<Committee> committees = new ArrayList<>();
    StringBuilder sb;

    public Lecturer(String name, String id, DegreeLevel degreeLevel, String major, float salary, Department department) {
        this.name = name;
        this.id = id;
        this.degreeLevel = degreeLevel;
        this.major = major;
        this.salary = salary;
        this.department = department;
        if (department != null) {
            department.assign(this);
        }
    }

    public Lecturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public DegreeLevel getDegreeLevel() {
        return degreeLevel;
    }

    public float getSalary() {
        return salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
        if (department != null) {
            department.assign(this);
        }
    }

    public void addCommittee(Committee committee) {
        committees.add(committee);                       // inserting committee to lecturer's committees array by index
    }

    private String committeesDisplay() {
        sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < committees.size(); i++) {
            if (committees.get(i) != null) {
                if (i == committees.size() - 1) {
                    sb.append(committees.get(i).getName());
                } else {
                    sb.append(committees.get(i).getName()).append(",");
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public void removeCommittee(Committee committee) {
        if (committees.remove(committee)) {
            return;                                                                    // if done - SUCCESS
        }
        throw new NotExistException("Lecturer is not assigned to that committee.");                                                     // if not done - ERROR
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Lecturer lec) {
            return lec.name.equals(name);
        }
        return false;
    }

    @Override
    public String toString() {
        return
                "name = '" + name + '\'' +
                        ", id = '" + id + '\'' +
                        ", degreeLevel = " + degreeLevel +
                        ", major = '" + major + '\'' +
                        ", salary = " + salary +
                        ", department = " + (department == null ? "none" : department.getName()) +
                        ", committees = " + committeesDisplay();
    }
}
