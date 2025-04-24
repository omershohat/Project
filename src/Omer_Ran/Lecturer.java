package Omer_Ran;

public class Lecturer {
    private String name;
    private String id;
    private DegreeLevel degreeLevel;
    private String major;
    private float salary;
    private Department department;
    Department none = new Department("none", 0);

    public Lecturer() {
    }

    public Lecturer(String name, String id, DegreeLevel degreeLevel, String major, float salary, Department department) {
        this.name = name;
        this.id = id;
        this.degreeLevel = degreeLevel;
        this.major = major;
        this.salary = salary;
        this.department = department;
        if (department != null) {
            department.addLecturer(this);
        }
    }

    public Lecturer(String name) {
        this.name = name;
    }

    public DegreeLevel getDegreeLevel() {
        return degreeLevel;
    }

    public void setDegreeLevel(DegreeLevel degreeLevel) {
        this.degreeLevel = degreeLevel;
    }

    public float getSalary() {
        return salary;
    }

    public Department getDepartment() {
        return department;
    }

    public ActionStatus setDepartment(Department department) {
        this.department = department;
        if (department !=null) {
            department.assign(this);
        }
        return ActionStatus.SUCCESS;
    }

    public void setNoneDepartment() {
        this.department = none;
    }

    public String getName() {
        return name;
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
