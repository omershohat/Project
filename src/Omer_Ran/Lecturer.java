package Omer_Ran;

import static Omer_Ran.Utils.removeObject;
import static Omer_Ran.Utils.resizeArr;

public class Lecturer {
    private String name;
    private String id;
    private DegreeLevel degreeLevel;
    private String major;
    private float salary;
    private Department department;
    private Committee[] committees = new Committee[0];
    private int numOfCommittees;
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

    public ActionStatus setDepartment(Department department) {
        this.department = department;
        if (department != null) {
            department.assign(this);
        }
        return ActionStatus.SUCCESS;
    }

    public void addCommittee(Committee committee) {
        if (numOfCommittees == committees.length) {                   // making sure there is place for new committee
            committees = (Committee[]) resizeArr(committees);
        }
        committees[numOfCommittees++] = committee;                       // inserting committee to lecturer's committees array by index
    }

    private String committeesDisplay() {
        sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < numOfCommittees; i++) {
            if (committees[i] != null) {
                if (i == numOfCommittees - 1) {
                    sb.append(committees[i].getName());
                } else {
                    sb.append(committees[i].getName()).append(",");
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public ActionStatus removeCommittee(Committee committee) {
        if (removeObject(committee, committees, numOfCommittees)) {                                         // trying to remove committee from lecturer's committees array
            numOfCommittees--;
            return ActionStatus.SUCCESS;                                                                    // if done - SUCCESS
        }
        return ActionStatus.COMMITTEE_NOT_EXIST_IN_LEC;                                                     // if not done - ERROR
    }

    @Override
    public String toString() {
        return "{" +
                "name = '" + name + '\'' +
                ", id = '" + id + '\'' +
                ", degreeLevel = " + degreeLevel +
                ", major = '" + major + '\'' +
                ", salary = " + salary +
                ", department = " + (department == null ? "none" : department.getName()) +
                ", committees = " + committeesDisplay() +
                '}';
    }


}
