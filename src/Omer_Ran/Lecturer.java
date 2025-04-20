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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DegreeLevel getDegreeLevel() {
        return degreeLevel;
    }

    public void setDegreeLevel(DegreeLevel degreeLevel) {
        this.degreeLevel = degreeLevel;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
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

    public void setName(String name) {
        this.name = name;
    }

    public static String[] getNameList(Lecturer[] array) {
        String[] nameList = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            nameList[i] = array[i].getName();
        }
        return nameList;
    }

    @Override
    public String toString() {
        return  "{" +
                "name = '" + name + '\'' +
                ", id = '" + id + '\'' +
                ", degreeLevel = " + degreeLevel.degreeDisplay +
                ", major = '" + major + '\'' +
                ", salary = " + salary +
                ", department = " + (this.department == null ? "none" : name) +
                '}';
    }
}
