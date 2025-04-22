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
        if (this.department == null) {
            this.setNoneDepartment();
        }
        return this.department;
    }

    public void setDepartment(Department department) {
        if (department == null) {
            setNoneDepartment();
            department.addLecturer(this);
        }
        else {
            this.department = department;
            department.addLecturer(this);
        }
    }

    public void setNoneDepartment() {
        this.department = none;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  "{" +
                "name = '" + name + '\'' +
                ", id = '" + id + '\'' +
                ", degreeLevel = " + degreeLevel.degreeDisplay +
                ", major = '" + major + '\'' +
                ", salary = " + salary +
                ", department = " + (department == null ? "none" : department.getName()) +
                '}';
    }
}
