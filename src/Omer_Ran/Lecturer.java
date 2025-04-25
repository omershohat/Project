package Omer_Ran;

public class Lecturer {
    private String name;
    private String id;
    private DegreeLevel degreeLevel;
    private String major;
    private float salary;
    private Department department;

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

    @Override
    public String toString() {
        return "{" +
                "name = '" + name + '\'' +
                ", id = '" + id + '\'' +
                ", degreeLevel = " + degreeLevel +
                ", major = '" + major + '\'' +
                ", salary = " + salary +
                ", department = " + (department == null ? "none" : department.getName()) +
                '}';
    }
}
